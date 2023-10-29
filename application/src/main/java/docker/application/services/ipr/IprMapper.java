package docker.application.services.ipr;

import docker.domain.ipr.model.DualClass;
import docker.domain.ipr.model.Ipr;
import ipr.api.ipr.api.model.CreateRequest;
import ipr.api.ipr.api.model.IprInner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IprMapper {

    public Ipr toEntity(CreateRequest createRequest) {
        var dual = createRequest.getDual() == null ? null : new DualClass(createRequest.getDual().getStringField(), createRequest.getDual().getLongField());
        return new Ipr(createRequest.getId(), createRequest.getMessage(), createRequest.getNumber(), createRequest.getIsOK(), createRequest.getSomeDate(), dual);
    }

    public List<IprInner> toIprResponse(List<Ipr> items) {
        if(items == null)
            return null;
        return items.stream()
                .map(item -> {
                    var dual = item.getDual() == null ? null :
                            new ipr.api.ipr.api.model.DualClass().stringField(item.getDual().getStringField()).longField(item.getDual().getLongField());

                    return new IprInner().id(item.getId()).message(item.getMessage())
                            .number(item.getNumber()).isOK(item.getIsOK()).someDate(item.getSomeDate()).dual(dual);
                })
                .toList();
    }
}
