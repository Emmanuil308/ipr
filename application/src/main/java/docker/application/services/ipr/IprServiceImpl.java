package docker.application.services.ipr;


import docker.domain.ipr.IprRepository;
import docker.domain.ipr.IprJpaRepository;
import ipr.api.ipr.api.model.CreateDeleteResponse;
import ipr.api.ipr.api.model.CreateRequest;
import ipr.api.ipr.api.model.IprInner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class IprServiceImpl implements IprService{

    private IprMapper iprMapper;
    private IprRepository iprRepository;
    private IprJpaRepository iprJPARepository;

    @Override
    public CreateDeleteResponse apiCreate(CreateRequest createRequest) {
        var id = iprJPARepository.save(iprMapper.toEntity(createRequest));
        return new CreateDeleteResponse().message("Сохранен с id: " + id).time(LocalDateTime.now().withNano(0));
    }

    @Override
    public List<IprInner> apiRead() {
        return iprMapper.toIprResponse(iprJPARepository.findAll());
    }
}
