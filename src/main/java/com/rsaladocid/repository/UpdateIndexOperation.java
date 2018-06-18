package com.rsaladocid.repository;

import java.util.Map;

/**
 * Operation to replace the data related to a particular index.
 */
public class UpdateIndexOperation extends IndexOperation {

	private Map<String, Object> data;

	/**
	 * Constructs an operation to replace the data related to the given index.
	 * 
	 * @param index
	 *            the index
	 * @param data
	 *            the key-value pairs to replace the existing ones
	 * 
	 */
	public UpdateIndexOperation(Index index, Map<String, Object> data) {
		super(index);
		data(data);
	}

	/**
	 * Sets the new data.
	 * 
	 * @param data
	 *            the key-value pairs
	 * @return the operation
	 */
	public final UpdateIndexOperation data(Map<String, Object> data) {
		this.data = data;
		return this;
	}

	/**
	 * Returns the new data.
	 * 
	 * @return the key-value pairs
	 */
	public final Map<String, Object> data() {
		return data;
	}

}
