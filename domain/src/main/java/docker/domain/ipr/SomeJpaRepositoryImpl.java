package docker.domain.ipr;

import docker.domain.ipr.model.Ipr;
import docker.domain.ipr.model.Some;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class SomeJpaRepositoryImpl implements SomeJpaRepository {

    EntityManagerFactory entityManagerFactory;

    public SomeJpaRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public Some save(Long iprId, String field1, String field2) {
        var some = new Some();
        some.setField1(field1);
        some.setField2(field2);
        try (var em = entityManagerFactory.createEntityManager()) {
            try {
                var iprQ = em.createQuery("SELECT i FROM Ipr i WHERE i.id = :iprId", Ipr.class)
                        .setParameter("iprId", iprId);
                em.getTransaction().begin();
                some.setIpr(iprQ.getSingleResult());
                em.persist(some);
                em.getTransaction().commit();
                return some;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw e;
            }
        }
    }

    @Override
    public List<Some> findAllByIpr(Long iprId) {
        try (var em = entityManagerFactory.createEntityManager()) {
            try {
                var query = em.createQuery("SELECT s FROM Some s LEFT JOIN Ipr i ON s.ipr = i WHERE i.id = :iprId", Some.class)
                        .setParameter("iprId", iprId);
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
}
