package com.rsaladocid.repository;

import java.io.IOException;
import java.util.Map;

/**
 * Interface to manage the storage of key-value pairs into document-based
 * databases. This interface provides the basic CRUD operations
 */
public interface Repository {

	/**
	 * Returns the name of the repository
	 * 
	 * @return the name
	 */
	public String getName();

	/**
	 * Establishes the connection to the repository
	 * 
	 * @throws IOException
	 */
	public void connect() throws IOException;

	/**
	 * Closes the connection to the repository
	 * 
	 * @throws IOException
	 */
	public void disconnect() throws IOException;

	/**
	 * Stores a new document with the given content
	 * 
	 * @param content
	 *            the content to store
	 * @return the document
	 */
	public Document create(Map<String, Object> content);

	/**
	 * Returns the document related to the given id
	 * 
	 * @param id
	 *            the document id
	 * @return the document, or <code>null</code> if there is no document with the
	 *         given id
	 */
	public Document retrieve(String id);

	/**
	 * Updates the content of the given document
	 * 
	 * @param document
	 *            the document to update
	 * @return the id of the updated document, or <code>null</code> if the given
	 *         document is not stored
	 */
	public String update(Document document);

	/**
	 * Removes the document related to the given id
	 * 
	 * @param id
	 *            the document id
	 * @return <code>true</code> if the document with the given id is deleted;
	 *         <code>false</code> otherwise
	 */
	public boolean delete(String id);

}
