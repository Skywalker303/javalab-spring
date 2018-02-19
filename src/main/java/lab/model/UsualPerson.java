package lab.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.Wither;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component("person")
public class UsualPerson implements Person {

    @Id
    @GeneratedValue
    private int id;

    private String firstName;
    private String lastName;

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