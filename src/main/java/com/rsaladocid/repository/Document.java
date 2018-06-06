package com.rsaladocid.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * This class encapsulates the key-value pairs stored in a document-based
 * database
 */
public class Document {

	/**
	 * The unique id
	 */
	private String id;

	/**
	 * The content
	 */
	private Map<String, Object> data;

	/**
	 * The metadata
	 */
	private Map<String, String> metadata;

	Document(String id, Map<String, Object> data) {
		setData(data);
		setId(id);
	}

	Document(String id, Map<String, Object> data, Map<String, String> metadata) {
		setData(data);
		setId(id);
		setMetadata(metadata);
	}

	/**
	 * Returns the content of the document as key-value paris.
	 * 
	 * @return the content
	 */
	public Map<String, Object> getData() {
		return data;
	}

	/**
	 * Returns the unique id. This id is automatically assigned by the underlying
	 * document-based database engine
	 * 
	 * @return the unique id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the metadata associated to this document
	 * 
	 * @return the metada
	 */
	public Map<String, String> getMetadata() {
		return new HashMap<String, String>(metadata);
	}

	/**
	 * Sets the content of the document
	 * 
	 * @param data
	 *            the content
	 */
	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	void setId(String id) {
		this.id = id;
	}

}
