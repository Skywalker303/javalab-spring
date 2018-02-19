package lab.model;

import lombok.Value;

@Value
public class SimpleContact implements Contact {
    String type;
    String value;
}