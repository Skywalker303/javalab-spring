package demo.model;

import lombok.Value;

import java.time.LocalDate;

@Value
public class SimpleBook implements Book {
    private long id;
    private String title;
    private Author author;
    private LocalDate dateRelease;
    private String comment;
}
