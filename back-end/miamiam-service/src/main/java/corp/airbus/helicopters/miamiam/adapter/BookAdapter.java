package corp.airbus.helicopters.miamiam.adapter;

import org.springframework.stereotype.Component;

import com.sqli.commons.core.adapter.AbstractAdapter;

import corp.airbus.helicopters.miamiam.model.Book;
import corp.airbus.helicopters.miamiam.model.BookDTO;

/**
 * The Class BookAdapter.
 */
@Component
public class BookAdapter extends AbstractAdapter<Book, BookDTO> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * corp.airbus.helicopters.miamiam.adapter.AbstractAdapter#adaptToDTO(java.
	 * lang.Object)
	 */
	@Override
	public BookDTO adaptToDTO(Book model) {
		BookDTO book = new BookDTO();

		book.setId(model.getPK());
		book.setTitle(model.getTitle());
		book.setDescription(model.getDescription());

		return book;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * corp.airbus.helicopters.miamiam.adapter.AbstractAdapter#adaptToModel(corp
	 * .airbus.helicopters.miamiam.model.AbstractDTO)
	 */
	@Override
	public Book adaptToModel(BookDTO dto) {
		Book book = new Book();

		book.setPK(dto.getId());
		book.setTitle(dto.getTitle());
		book.setDescription(dto.getDescription());

		return book;
	}

}
