package com.rsaladocid.repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.Response;

public class CouchDbRepository implements Repository {

	private String name;
	private CouchDbProperties properties;
	private CouchDbClient client;

	/**
	 * Creates a CouchDB repository
	 * 
	 * @param properties
	 *            the data to build the CouchDB client
	 */
	public CouchDbRepository(CouchDbProperties properties) {
		this.properties = properties;
		this.name = properties.getDbName();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	public void connect() throws IOException {
		if (client == null) {
			client = new CouchDbClient(properties);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void disconnect() throws IOException {
		if (client != null) {
			client.shutdown();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Document create(Map<String, Object> content) {
		try {
			connect();

			Response response = client.save(content);

			if (response.getError() == null) {
				Map<String, String> metadata = new HashMap<String, String>();
				metadata.put("rev", response.getRev());

				return new Document.Builder(response.getId(), content).metadata(metadata).build();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Connection cannot be established");
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Document retrieve(String id) {
		try {
			connect();

			if (client.contains(id)) {
				Map<String, Object> content = client.find(Map.class, id);
				String rev = content.get("_rev").toString();

				if (content != null) {
					content.remove("_id");
					content.remove("_rev");

					Map<String, String> metadata = new HashMap<String, String>();
					metadata.put("rev", rev);

					return new Document.Builder(id, content).metadata(metadata).build();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Connection cannot be established");
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String update(Document document) {
		try {
			connect();

			if (client.contains(document.getId())) {
				Map<String, Object> object = new HashMap<String, Object>(document.getData());
				object.put("_id", document.getId());
				object.put("_rev", document.getMetadata().get("rev"));

				Response response = client.update(object);

				if (response.getError() == null) {
					return response.getId();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Connection cannot be established");
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean delete(String id) {
		try {
			connect();

			if (client.contains(id)) {
				Document document = retrieve(id);
				return delete(document.getId(), document.getMetadata().get("rev"));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Connection cannot be established");
		}

		return false;
	}

	/**
	 * Removes the given document
	 * 
	 * @param document
	 *            the document to remove
	 * @return <code>true</code> if the document is deleted; <code>false</code>
	 *         otherwise
	 */
	public boolean delete(Document document) {
		try {
			connect();

			if (client.contains(document.getId())) {
				return delete(document.getId(), document.getMetadata().get("rev"));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Connection cannot be established");
		}

		return false;
	}

	private boolean delete(String id, String rev) {
		Response response = client.remove(id, rev);
		return response.getError() == null;
	}

}
