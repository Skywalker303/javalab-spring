package jdbc;

import lab.dao.CountryDao;
import lab.model.Country;
import lab.model.SimpleCountry;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@FieldDefaults(makeFinal = true, level = PRIVATE)
@ContextConfiguration("classpath:jdbc.xml")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JdbcTest{


	private CountryDao jdbcCountryDao;
	
    private List<Country> expectedCountryList = new ArrayList<>();
    private List<Country> expectedCountryListStartsWithA = new ArrayList<>();
    private Country countryWithChangedName = new SimpleCountry(8L, "Russia", "RU");

    @BeforeEach
    void setUp() {
        initExpectedCountryLists();
        jdbcCountryDao.loadCountries();
    }

    
    @Test
    @DirtiesContext
    void testCountryList() {
        List<Country> countryList = jdbcCountryDao.getCountries();
        assertNotNull(countryList);
        assertEquals(expectedCountryList.size(), countryList.size());
        for (int i = 0; i < expectedCountryList.size(); i++) {
            assertEquals(expectedCountryList.get(i), countryList.get(i));
        }
    }

    @Test
    @DirtiesContext
    void testCountryListStartsWithA() {
        List<Country> countryList = jdbcCountryDao.getCountriesStartWith("A");
        assertNotNull(countryList);
        assertEquals(expectedCountryListStartsWithA.size(), countryList.size());
        for (int i = 0; i < expectedCountryListStartsWithA.size(); i++) {
            assertEquals(expectedCountryListStartsWithA.get(i), countryList.get(i));
        }
    }

    @Test
    @DirtiesContext
    void testCountryChange() {
        jdbcCountryDao.updateCountryName("RU", "Russia");
        assertEquals(countryWithChangedName, jdbcCountryDao.getCountryByCodeName("RU"));
    }

    private void initExpectedCountryLists() {
         for (int i = 0; i < CountryDao.COUNTRY_INIT_DATA.length;) {
             String[] countryInitData = CountryDao.COUNTRY_INIT_DATA[i++];
             Country country = new SimpleCountry((long)i, countryInitData[0], countryInitData[1]);
             expectedCountryList.add(country);
             if (country.getName().startsWith("A")) {
                 expectedCountryListStartsWithA.add(country);
             }
         }
     }
}