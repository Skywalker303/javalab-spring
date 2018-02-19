package demo.dao.jdbc;

import demo.model.Author;
import demo.model.Book;

import java.util.Optional;
import java.util.stream.Stream;

public interface BookDao {
    Book add(String title, Author author, String dateRelease, String comment);

//    default Book add(Book book) {
//        return add(book.getTitle(), book.getAuthor(), book.getDateRelease(), book.getComment());
//    }

    Stream<Book> get();

    default Optional<Book> get(long id) {
        return get().filter(book -> book.getId() == id).findAny();
    }

    default Stream<Book> get(String title) {
        return get().filter(book -> book.getTitle().equals(title));
    }


}
