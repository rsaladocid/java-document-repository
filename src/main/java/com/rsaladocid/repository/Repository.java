package com.rsaladocid.repository;

import java.util.Map;

import com.rsaladocid.repository.operations.CreateIndexOperation;
import com.rsaladocid.repository.operations.IndexOperation;
import com.rsaladocid.repository.operations.UpdateIndexOperation;

/**
 * Storage of key-value pairs that provides basic CRUD operations.
 */
public interface Repository<T extends Index> {

	/**
	 * Performs an operation to create a new index to store data.
	 * 
	 * @param operation
	 *            the operation
	 * @return the new index, or {@link NullIndex} if any index can be created
	 */
	public T create(CreateIndexOperation operation);

	/**
	 * Performs an operation to get data related to a particular index.
	 * 
	 * @param operation
	 *            the operation
	 * @return the key-value pairs
	 * @throws MissingIndexException
	 *             if the index does not exist, or is an instance of
	 *             {@link NullIndex}
	 */
	public Map<String, Object> retrieve(IndexOperation operation);

	/**
	 * Performs an operation to update data related to a particular index.
	 * 
	 * @param operation
	 *            the operation
	 * @throws MissingIndexException
	 *             if the index does not exist, or is an instance of
	 *             {@link NullIndex}
	 */
	public void update(UpdateIndexOperation operation);

	/**
	 * Performs an operation to remove an index and the corresponding data.
	 * 
	 * @param operation
	 *            the operation
	 * @throws MissingIndexException
	 *             if the index does not exist, or is an instance of
	 *             {@link NullIndex}
	 */
	public void delete(IndexOperation operation);

}
