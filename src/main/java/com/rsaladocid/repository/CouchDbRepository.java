package com.rsaladocid.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.Response;

import com.google.gson.JsonObject;

public class CouchDbRepository implements Repository {
	
	private static final String DOC_ID = "_id";
	private static final String DOC_REV = "_rev";
	private static final String ID = "id";
	private static final String REV = "rev";
	
	private CouchDbClient client;

	@Override
	public void connect(String... configuration) {
		CouchDbProperties properties = new CouchDbProperties();
		properties.setDbName(configuration[0]);
		properties.setProtocol("http");
		properties.setHost("127.0.0.1");
		properties.setPort(5984);
		properties.setCreateDbIfNotExist(true);
		setClient(new CouchDbClient(properties));
	}

	@Override
	public void disconnect() {
		getClient().shutdown();
	}

	@Override
	public String create(Document source) {
		Response response = getClient().save(getClient().getGson().fromJson(source.getDataAsString(), JsonObject.class));
		source.setId(response.getId());
		source.getMetadata().put(REV, response.getRev());
		
		return response.getId();
	}

	@Override
	public Document retrieve(String id) throws NotDocumentFoundException {
		Document document = new Document("{}".getBytes(), "");

		if (getClient().contains(id)) {
			InputStream input = getClient().find(id);

			byte[] data = new byte[0];
			Map<String, Object> metadata = new HashMap<String, Object>();

			try {
				data = IOUtils.toByteArray(input);

				JsonObject json = getClient().getGson().fromJson(new String(data), JsonObject.class);
				metadata.put(REV, json.get(DOC_REV).getAsString());
				json.remove(DOC_ID);
				json.remove(DOC_REV);

				data = getClient().getGson().toJson(json).getBytes();

				document.setData(data);
				document.setId(id);
				document.setMetadata(metadata);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new NotDocumentFoundException(id);
		}

		return document;
	}

	@Override
	public Document[] retrieveAll() {
		List<JsonObject> allDocs = getClient().view("_all_docs").query(JsonObject.class);
		Document[] documents = new Document[allDocs.size()];
		
		for (int i = 0; i < allDocs.size(); i++) {
			try {
				documents[i] = retrieve(allDocs.get(i).get(ID).getAsString());
			} catch (NotDocumentFoundException e) {
				e.printStackTrace();
			}
		}
		
		return documents;
	}

	@Override
	public String update(Document source) throws NotDocumentFoundException {
		retrieve(source.getId());
		
		JsonObject json = getClient().getGson().fromJson(source.getDataAsString(), JsonObject.class);
		json.addProperty(DOC_ID, source.getId());
		json.addProperty(DOC_REV, source.getMetadata().get(REV).toString());
		Response response = getClient().update(json);
		
		return response.getId();
	}

	@Override
	public Document delete(String id) throws NotDocumentFoundException {
		Document document = delete(retrieve(id));
		
		return document;
	}

	@Override
	public Document delete(Document source) throws NotDocumentFoundException {
		retrieve(source.getId());
		
		getClient().remove(source.getId(), source.getMetadata().get(REV).toString());
		
		return source;
	}

	@Override
	public Document[] deleteAll() {
		List<JsonObject> allDocs = getClient().view("_all_docs").query(JsonObject.class);
		Document[] documents = new Document[allDocs.size()];
		
		for (int i = 0; i < allDocs.size(); i++) {
			try {
				documents[i] = delete(allDocs.get(i).get(ID).getAsString());
			} catch (NotDocumentFoundException e) {
				e.printStackTrace();
			}
		}
		
		return documents;
	}

	protected CouchDbClient getClient() {
		return client;
	}

	protected void setClient(CouchDbClient client) {
		this.client = client;
	}

}
