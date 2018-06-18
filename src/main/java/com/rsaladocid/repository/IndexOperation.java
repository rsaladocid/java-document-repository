package com.rsaladocid.repository;

/**
 * Operation to perform using an index.
 */
public class IndexOperation {

	private Index index;

	/**
	 * Constructs a new index operation.
	 * 
	 * @param index
	 *            the index
	 */
	public IndexOperation(Index index) {
		index(index);
	}

	/**
	 * Sets the index to manage.
	 * 
	 * @param index
	 *            the index
	 * @return the operation
	 */
	public final IndexOperation index(Index index) {
		this.index = index;
		return this;
	}

	/**
	 * Returns the index to manage.
	 * 
	 * @return the index
	 */
	public final Index index() {
		return index;
	}

}
