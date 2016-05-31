package corp.airbus.helicopters.miamiam.facade.rest;

import fr.opensagres.xdocreport.converter.XDocConverterException;
import fr.opensagres.xdocreport.core.XDocReportException;
import io.swagger.annotations.Api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sqli.commons.core.config.core.properties.ReloadableProperty;
import com.sqli.commons.core.controller.AbstractController;
import com.sqli.commons.core.service.model.AbstractDTO;
import com.sqli.commons.core.service.model.IdentifierDTO;

import corp.airbus.helicopters.miamiam.model.BookDTO;
import corp.airbus.helicopters.miamiam.security.Right;
import corp.airbus.helicopters.miamiam.security.UserHasRight;
import corp.airbus.helicopters.miamiam.service.BookService;

/**
 * The Class BookController.
 */
@RequestMapping("/books")
@Controller
@Api(value = "Book")
public class BookController extends AbstractController<BookDTO> {

	/** The book service. */
	@Autowired
	private BookService bookService;

	/** Hot update of myConfig var. */
	@ReloadableProperty("myConfig")
	private Integer myConfig;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sqli.commons.core.controller.AbstractController
	 * #all()
	 */
	@Override
	@UserHasRight(Right.GET_ALL_BOOK)
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public List<BookDTO> all() {
		return bookService.all();
	}

	/**
	 * Gets the pdf.
	 *
	 * @param id
	 *            the id
	 * @param pdf
	 *            the pdf
	 * @return the pdf
	 * @throws XDocConverterException
	 *             the x doc converter exception
	 * @throws XDocReportException
	 *             the x doc report exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@UserHasRight(Right.GET_PDF_BOOK)
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public AbstractDTO<Long> get(@PathVariable Long id, @RequestParam(value = "pdf", required = false) boolean pdf) throws XDocConverterException, XDocReportException, IOException {
		if (pdf) {
			return bookService.getPdf(id);
		} else {
			return bookService.get(id);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sqli.commons.core.controller.AbstractController
	 * #create(com.sqli.commons.core.service.model.
	 * AbstractDTO)
	 */
	@Override
	@UserHasRight(Right.CREATE_BOOK)
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/{id}")
	public IdentifierDTO<Long> create(@RequestBody BookDTO book) {
		return new IdentifierDTO<Long>(bookService.save(book));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sqli.commons.core.controller.AbstractController
	 * #update(com.sqli.commons.core.service.model.
	 * AbstractDTO)
	 */
	@Override
	@UserHasRight(Right.UPDATE_BOOK)
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public void update(@RequestBody BookDTO book) {
		bookService.save(book);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sqli.commons.core.controller.AbstractController
	 * #delete(java.lang.Long)
	 */
	@Override
	@UserHasRight(Right.DELETE_BOOK)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public void delete(@PathVariable Long id) {
		bookService.delete(id);
	}
}
