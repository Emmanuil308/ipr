package docker.domain.ipr;

import docker.domain.ipr.model.Ipr;

import java.util.List;

public interface IprRepository {

    List<Ipr> findAll();

    Long save(Ipr item);
}
