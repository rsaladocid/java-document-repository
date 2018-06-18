package com.rsaladocid.repository.operations;

import java.util.Map;

/**
 * Operation to store a {@link Map}, making it accessible by a particular index.
 *
 */
public class CreateIndexOperation {

	private Map<String, Object> data;

	/**
	 * Constructs a new operation to index the given data.
	 * 
	 * @param data
	 */
	public CreateIndexOperation(Map<String, Object> data) {
		data(data);
	}

	/**
	 * Sets the data to index.
	 * 
	 * @param data
	 *            the key-value pairs to store and index
	 * @return the operation
	 */
	public final CreateIndexOperation data(Map<String, Object> data) {
		this.data = data;
		return this;
	}

	/**
	 * Returns the data to index.
	 * 
	 * @return the key-value pairs
	 */
	public final Map<String, Object> data() {
		return data;
	}

}
