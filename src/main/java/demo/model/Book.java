package demo.model;

import java.time.LocalDate;

public interface Book {
    long getId();
    String getTitle();
    Author getAuthor();
    LocalDate getDateRelease();
    String getComment();
}
