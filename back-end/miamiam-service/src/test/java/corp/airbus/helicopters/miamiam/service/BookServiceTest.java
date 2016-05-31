package corp.airbus.helicopters.miamiam.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sqli.commons.test.service.ServiceTestSupport;

/**
 * The Class BookServiceTest.
 */
public class BookServiceTest extends ServiceTestSupport {

	/** The book service. */
	@Autowired
	BookService bookService;

	/**
	 * Test all.
	 */
	@Test
	public void testAll() {
		Assert.assertEquals(3, bookService.all().size());
	}
}
