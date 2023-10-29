package docker.domain.ipr;

import docker.domain.ipr.model.Some;

import java.util.List;

public interface SomeJpaRepository {

    Some save(Long iprId, String field1, String field2);
    List<Some> findAllByIpr(Long iprId);
}
