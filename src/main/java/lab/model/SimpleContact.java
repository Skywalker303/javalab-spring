package lab.model;

import com.epam.jl.demo.ioc.Contact;
import lombok.Value;

@Value
public class SimpleContact implements Contact {
    String type;
    String value;
}