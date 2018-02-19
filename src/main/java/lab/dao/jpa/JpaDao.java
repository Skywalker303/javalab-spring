package lab.dao.jpa;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class JpaDao {

	@Setter(onMethod = @__(@PersistenceUnit))
	protected EntityManagerFactory emf;

    protected <T> T mapEntityManager(Function<EntityManager, T> entityManagerMapper) {
		        try (CloseableEntityManager entityManager = new CloseableEntityManager(
				                emf.createEntityManager())) {
			            return entityManagerMapper.apply(entityManager);
			        }
		    }

    protected <T> T mapEntityManagerInTransaction(Function<EntityManager, T> entityManagerMapper) {
		        return mapEntityManager(entityManager -> {
			            EntityTransaction transaction = entityManager.getTransaction();
			            transaction.begin();
			            T result = entityManagerMapper.apply(entityManager);
			            transaction.commit();
			            return result;
			        });
		    }

		    protected void withEntityManager(Consumer<EntityManager> entityManagerConsumer) {
		        try (CloseableEntityManager entityManager = new CloseableEntityManager(
				                emf.createEntityManager())) {
			            entityManagerConsumer.accept(entityManager);
			        }
		    }

		    protected void withEntityManagerInTransaction(Consumer<EntityManager> entityManagerConsumer) {
		        withEntityManager(entityManager -> {
			            EntityTransaction transaction = entityManager.getTransaction();
			            transaction.begin();
			            entityManagerConsumer.accept(entityManager);
			            transaction.commit();
			        });
		    }

	@RequiredArgsConstructor
    private class CloseableEntityManager implements EntityManager, AutoCloseable {
       @Delegate
       private final EntityManager entityManager;
    }
}
