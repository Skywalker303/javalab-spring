package lab.model;

public interface Country {
    Country withId(Long id);
    Long getId();
    String getName();
    String getCodeName();
}
