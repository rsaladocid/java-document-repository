package com.rsaladocid.repository;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * Repository that uses ElasticSearch as document-based database
 */
public class ElasticSearchRepository implements Repository {

	private RestClientBuilder builder;
	private RestHighLevelClient client;
	private String name;
	private String type;

	/**
	 * Creates a ElasticSearch repository
	 * 
	 * @param name
	 *            the index
	 * @param type
	 *            the type
	 * @param builder
	 *            the data to build the ElasticSearch client
	 */
	public ElasticSearchRepository(String name, String type, RestClientBuilder builder) {
		this.name = name;
		this.type = type;
		this.builder = builder;
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
			client = new RestHighLevelClient(builder);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void disconnect() throws IOException {
		if (client != null) {
			client.close();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Document create(Map<String, Object> content) {
		IndexRequest request = new IndexRequest(getName(), type);
		request.source(content);

		try {
			connect();
			IndexResponse response = client.index(request);

			if (response.getResult() == DocWriteResponse.Result.CREATED) {
				return buildDocument(response.getId(), content);
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
	public Document retrieve(String id) {
		GetRequest request = new GetRequest(getName(), type, id);

		try {
			connect();
			GetResponse response = client.get(request);

			if (response.isExists()) {
				return buildDocument(response.getId(), response.getSourceAsMap());
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
		IndexRequest request = new IndexRequest(getName(), type, document.getId());
		request.source(document.getData());

		try {
			connect();
			IndexResponse response = client.index(request);

			if (response.getResult() == DocWriteResponse.Result.UPDATED) {
				return response.getId();
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
		DeleteRequest request = new DeleteRequest(getName(), type, id);

		try {
			connect();
			DeleteResponse response = client.delete(request);

			ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
			if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Connection cannot be established");
		}

		return false;
	}

	private Document buildDocument(String id, Map<String, Object> data) {
		return new Document(id, data);
	}

}
