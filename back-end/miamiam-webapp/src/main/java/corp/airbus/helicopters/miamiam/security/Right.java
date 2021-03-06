package corp.airbus.helicopters.miamiam.security;

/**
 * The Enum Right.
 */
public enum Right {

	/** The get me. */
	GET_ME,
	/** The get all book. */
	GET_ALL_BOOK,
	/** The get book. */
	GET_BOOK,
	/** The get pdf book. */
	GET_PDF_BOOK,
	/** The delete book. */
	DELETE_BOOK,
	/** The create book. */
	CREATE_BOOK,
	/** The update book. */
	UPDATE_BOOK,
	/** The access keycopter. */
	ACCESS_KEYCOPTER,
	/** The access teamcopter. */
	ACCESS_TEAMCOPTER;

	/**
	 * Gets the by name.
	 *
	 * @param name
	 *            the name
	 * @return the by name
	 */
	public static Right getByName(String name) {
		for (Right right : Right.values()) {
			if (right.name().equals(name)) {
				return right;
			}
		}
		return null;
	}
}
