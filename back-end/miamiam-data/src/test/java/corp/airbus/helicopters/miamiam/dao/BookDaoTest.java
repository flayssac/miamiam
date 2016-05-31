/**
 * 	
 */
package corp.airbus.helicopters.miamiam.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.sqli.commons.core.data.GenericDao;
import com.sqli.commons.test.data.DaoTestSupport;

import corp.airbus.helicopters.miamiam.model.Book;

/**
 * The Class BookDaoTest.
 */
public class BookDaoTest extends DaoTestSupport<Book, Long> {

	/** The dao. */
	@Autowired
	private BookDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sqli.commons.test.data.DaoTestSupport#
	 * getNewInstance (int)
	 */
	@Override
	protected Book getNewInstance(int index) {
		Book instance = new Book();
		setAllParameters(instance, index);
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sqli.commons.test.data.DaoTestSupport#getDao
	 * ()
	 */
	@Override
	protected GenericDao<Book, Long> getDao() {
		return dao;
	}
}
