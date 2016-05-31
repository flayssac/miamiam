/**
 * 	
 */
package corp.airbus.helicopters.miamiam.dao;

import org.springframework.stereotype.Repository;

import com.sqli.commons.core.data.AbstractDao;

import corp.airbus.helicopters.miamiam.model.Book;

/**
 * The Class BookDao.
 */
@Repository
public class BookDao extends AbstractDao<Book, Long> {

	/**
	 * Instantiates a new book dao.
	 */
	public BookDao() {
		super(Book.class);
	}
}
