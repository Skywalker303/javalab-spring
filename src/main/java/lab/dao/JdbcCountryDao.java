package lab.dao;

import lab.model.Country;
import lab.model.SimpleCountry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class JdbcCountryDao extends NamedParameterJdbcDaoSupport implements CountryDao {

    //language=h2
    private static final String SAVE_COUNTRY_SQL = "INSERT INTO country (name, code_name) VALUES (?, ?)";
    //language=h2
    private static final String GET_ALL_COUNTRIES_SQL = "SELECT id, name, code_name FROM country";
    //language=h2
    private static final String GET_COUNTRIES_BY_NAME_SQL = "SELECT id, name, code_name FROM country WHERE name LIKE :name";
    //language=h2
    private static final String GET_COUNTRY_BY_NAME_SQL = "SELECT id, name, code_name FROM country WHERE name = '%s'";
    //language=h2
    private static final String GET_COUNTRY_BY_CODE_NAME_SQL = "SELECT id, name, code_name FROM country WHERE code_name = '%s'";
    //language=h2
    private static final String UPDATE_COUNTRY_NAME_SQL = "UPDATE country SET name='%s' WHERE code_name='%s'";

    private static final RowMapper<Country> COUNTRY_ROW_MAPPER = (resultSet, i) ->
            new SimpleCountry(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("code_name"));

    @Override
    public List<Country> getCountries() {
        return getJdbcTemplate().query(GET_ALL_COUNTRIES_SQL, COUNTRY_ROW_MAPPER);
    }

    @Override
    public List<Country> getCountriesStartWith(String name) {
        return getNamedParameterJdbcTemplate()
                .query(GET_COUNTRIES_BY_NAME_SQL,
                        Map.of("name", name + "%"),
                        COUNTRY_ROW_MAPPER);
    }

    @Override
    public void updateCountryName(String codeName, String newCountryName) {
        getJdbcTemplate().execute(
                String.format(UPDATE_COUNTRY_NAME_SQL, newCountryName, codeName));
    }

    @Override
    public void loadCountries() {
        for (String[] countryData : COUNTRY_INIT_DATA) {
            save(countryData[0], countryData[1]);
        }
    }

    @Override
    public Country getCountryByCodeName(String codeName) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        String sql = String.format(GET_COUNTRY_BY_CODE_NAME_SQL, codeName);

        return jdbcTemplate.query(sql, COUNTRY_ROW_MAPPER).get(0);
    }

    @Override
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

    @Override
    public Country save(Country country) {
        assert country.getId() == null;
                return country.withId(save(country.getName(), country.getCodeName()));
            }

            public long save(String name, String codeName) {
                KeyHolder keyHolder = new GeneratedKeyHolder();
                getJdbcTemplate().update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(SAVE_COUNTRY_SQL, Statement.RETURN_GENERATED_KEYS);
                                        ps.setString(1, name);
                                        ps.setString(2, codeName);
                                        return ps; }, keyHolder);
                return keyHolder.getKey().intValue();
    }
}
