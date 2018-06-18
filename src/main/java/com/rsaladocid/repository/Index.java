package com.rsaladocid.repository;

/**
 * An index contains the information to uniquely operate on key-value pairs
 * stored in a {@link Repository}.
 */
public class Index {

	private String id;

	public Index(String id) {
		id(id);
	}

	public final String id() {
		return id;
	}

	public final Index id(String id) {
		this.id = id;
		return this;
	}
	
	@Override
	public String toString() {
		return id();
	}

}
