package com.rsaladocid.repository;

/**
 * Special case of {@link Index} without any related key-value pairs.
 */
public class NullIndex extends Index {

	NullIndex() {
		super(null);
	}

}
