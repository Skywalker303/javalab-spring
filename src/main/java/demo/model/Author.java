package demo.model;

import lab.model.Person;

import java.util.Collection;

public interface Author extends Person {
    Collection<Book> getBooks();
}
