package docker.domain.ipr;

import docker.domain.ipr.model.Ipr;
import jakarta.persistence.EntityManagerFactory;



import java.util.List;

public class IprJpaRepositoryImpl implements IprJpaRepository {

    EntityManagerFactory entityManagerFactory;

    public IprJpaRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public List<Ipr> findAll() {

        try(var em = entityManagerFactory.createEntityManager()) {
            try {
                var query = em.createQuery("SELECT i FROM Ipr i", Ipr.class);
                em.getTransaction().begin();
                var result = query.getResultList();
                em.getTransaction().commit();
                return result;

            } catch (Exception e) {
                em.getTransaction().rollback();
                throw e;
            }
        }
    }

    @Override
    public Long save(Ipr item) {

        try(var em = entityManagerFactory.createEntityManager()) {
            try {
                em.getTransaction().begin();
                em.persist(item);
                em.flush();
                em.getTransaction().commit();
                return item.getId();

            } catch (Exception e) {
                em.getTransaction().rollback();
                throw e;
            }
        }
    }
}
