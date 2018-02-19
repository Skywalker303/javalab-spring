package lab.model;

import com.epam.jl.demo.ioc.Contact;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Entity
@Table(name = "person")
@Builder
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Component("person")
public class UsualPerson implements Person {

    @Id
    @GeneratedValue
    int id;

    String firstName;
    String lastName;

    @ManyToOne
    @JoinColumn(name = "country_id")
    SimpleCountry country;
    int age;
    float height;
    boolean isProgrammer;

    @Wither
    boolean isBroke;

    @OneToMany
    @Singular
    List<Contact> contacts;
}