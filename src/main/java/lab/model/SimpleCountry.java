package lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "country")
@NoArgsConstructor
@AllArgsConstructor
public class SimpleCountry implements Country {
    @Id
    @Wither
    @GeneratedValue
    Long id;

    String name;

    String codeName;
}
