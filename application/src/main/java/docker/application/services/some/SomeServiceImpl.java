package docker.application.services.some;

import docker.domain.ipr.SomeJpaRepository;
import ipr.api.ipr.api.model.ByIprResponse;
import ipr.api.ipr.api.model.CreateSomeResponse;
import org.springframework.stereotype.Service;

@Service
public class SomeServiceImpl implements SomeService {

    private SomeJpaRepository someJpaRepository;
    private SomeMapper someMapper;

    public SomeServiceImpl(SomeJpaRepository someJpaRepository, SomeMapper someMapper) {
        this.someJpaRepository = someJpaRepository;
        this.someMapper = someMapper;
    }

    @Override
    public CreateSomeResponse apiSomeCreate(Long iprId, String field1, String field2) {
        var result = someJpaRepository.save(iprId, field1, field2);
        return new CreateSomeResponse().id(result.getId()).message("Добавлено к Ipr с id = " + result.getIpr().getId());
    }

    @Override
    public ByIprResponse apiSomeGetByIpr(Long ipr) {
        return someMapper.toResponseByIpr(someJpaRepository.findAllByIpr(ipr));
    }
}
