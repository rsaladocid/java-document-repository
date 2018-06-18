package com.rsaladocid.repository;

/**
 * Exception that can be thrown when a particular {@link Index} is not found.
 */
public class MissingIndexException extends RuntimeException {

	private static final long serialVersionUID = 2469105085804072770L;

	/**
	 * Constructs a new runtime exception with null as its detail message.
	 */
	public MissingIndexException() {
		super();
	}

	/**
	 * Constructs a new runtime exception with the specified detail message.
	 *
	 * @param message
	 *            the message
	 */
	public MissingIndexException(String message) {
		super(message);
	}

	/**
	 * Constructs a new runtime exception with the specified detail message and
	 * cause.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public MissingIndexException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new runtime exception with the specified cause and a detail
	 * message of (cause==null ? null : cause.toString()) (which typically contains
	 * the class and detail message of cause).
	 *
	 * @param cause
	 *            the cause
	 */
	public MissingIndexException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new runtime exception with the specified detail message, cause,
	 * suppression enabled or disabled, and writable stack trace enabled or
	 * disabled.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 * @param enableSuppression
	 *            the enableSuppression
	 * @param writableStackTrace
	 *            the writableStackTrace
	 */
	protected MissingIndexException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
