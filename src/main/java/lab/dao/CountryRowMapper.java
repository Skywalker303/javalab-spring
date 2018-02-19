package lab.dao;

import lab.model.Country;
import lab.model.SimpleCountry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRowMapper implements RowMapper<Country> {
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String CODE_NAME = "code_name";

        	public Country mapRow(ResultSet resultSet, int i) throws SQLException {
        		return new SimpleCountry(
                				resultSet.getInt(ID),
                				resultSet.getString(NAME),
                                resultSet.getString(CODE_NAME)
                        				);
        }
}