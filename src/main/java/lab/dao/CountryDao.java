package lab.dao;

import lab.model.Country;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import java.util.List;
import java.util.Map;

public class CountryDao extends NamedParameterJdbcDaoSupport {

    public static final String[][] COUNTRY_INIT_DATA = {
            {"Australia", "AU"},
            {"Canada", "CA"},
            {"France", "FR"},
            {"Hong Kong", "HK"},
            {"Iceland", "IC"},
            {"Japan", "JP"},
            {"Nepal", "NP"},
            {"Russian Federation", "RU"},
            {"Sweden", "SE"},
            {"Switzerland", "CH"},
            {"United Kingdom", "GB"},
            {"United States", "US"}};

    private static final String LOAD_COUNTRIES_SQL = "INSERT INTO country (name, code_name) VALUES ('%s', '%s')";
    private static final String GET_ALL_COUNTRIES_SQL = "SELECT id, name, code_name FROM country";
    private static final String GET_COUNTRIES_BY_NAME_SQL = "SELECT id, name, code_name FROM country WHERE name LIKE :name";
    private static final String GET_COUNTRY_BY_NAME_SQL = "SELECT id, name, code_name FROM country WHERE name = '%s'";
    private static final String GET_COUNTRY_BY_CODE_NAME_SQL = "SELECT id, name, code_name FROM country WHERE code_name = '%s'";
    private static final String UPDATE_COUNTRY_NAME_SQL_1 = "update country SET name='";
	private static final String UPDATE_COUNTRY_NAME_SQL_2 = " where code_name='";

    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

    public List<Country> getCountryList() {
        return getJdbcTemplate().query(GET_ALL_COUNTRIES_SQL,COUNTRY_ROW_MAPPER);
    }

    public List<Country> getCountryListStartWith(String name) {
        return getNamedParameterJdbcTemplate()
                .query(GET_COUNTRIES_BY_NAME_SQL,
                        Map.of("name", name + "%"),
                        COUNTRY_ROW_MAPPER);
    }

    public void updateCountryName(String codeName, String newCountryName) {
        getJdbcTemplate().execute(
                UPDATE_COUNTRY_NAME_SQL_1 + newCountryName + "'" +
                        UPDATE_COUNTRY_NAME_SQL_2 + codeName + "'");
    }

    public void loadCountries() {
        for (String[] countryData : COUNTRY_INIT_DATA) {
            getJdbcTemplate().execute(
                    String.format(LOAD_COUNTRIES_SQL, countryData[0], countryData[1]));
        }
    }

    public Country getCountryByCodeName(String codeName) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        String sql = String.format(GET_COUNTRY_BY_CODE_NAME_SQL, codeName);

        return jdbcTemplate.query(sql, COUNTRY_ROW_MAPPER).get(0);
    }

    public Country getCountryByName(String name)
            throws CountryNotFoundException {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        List<Country> countryList = jdbcTemplate.query(
                String.format(GET_COUNTRY_BY_NAME_SQL, name), COUNTRY_ROW_MAPPER);
        if (countryList.isEmpty()) {
            throw new CountryNotFoundException();
        }
        return countryList.get(0);
    }
}
