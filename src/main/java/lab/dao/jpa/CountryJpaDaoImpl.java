package lab.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.stereotype.Repository;

import lab.dao.CountryDao;
import lab.model.Country;

@Repository("countryDao")
public class CountryJpaDaoImpl extends JpaDao implements CountryDao {

	@Override
	public Country save(Country country) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction etransaction = em.getTransaction();
		etransaction.begin();
		em.merge(country);
		etransaction.commit();
		if (em != null) {
			em.close();
		}
		return country;
	}

	@Override
	public List<Country> getCountries() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction etransaction = em.getTransaction();
		etransaction.begin();
		List<Country> listOfCountries = em.createQuery("select s from SimpleCountry s", Country.class).getResultList();
		etransaction.commit();
		if (em != null)
			em.close();
		return listOfCountries;
			}

	@Override
	public List<Country> getCountriesStartWith(String name) {
		return null;
	}

	@Override
	public void updateCountryName(String codeName, String newCountryName) {

	}

	@Override
	public void loadCountries() {

	}

	@Override
	public Country getCountryByCodeName(String codeName) {
		return null;
	}

	@Override
	public Country getCountryByName(String name) {
		return mapEntityManager(entityManager -> entityManager.createQuery
				("select s from SimpleCountry s where s.name like :name", Country.class).setParameter("name", name)
				.getSingleResult());
	}

}
