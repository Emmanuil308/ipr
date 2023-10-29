package docker.application.services.some;


import docker.domain.ipr.model.Some;
import ipr.api.ipr.api.model.ByIprResponse;
import ipr.api.ipr.api.model.DualClass;
import ipr.api.ipr.api.model.IprInner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SomeMapper {

    public ByIprResponse toResponseByIpr(List<Some> items) {
        if (items == null || items.isEmpty())
            return null;

        var response = new ByIprResponse();

        var ipr = items.get(0).getIpr();
        var iprInner = new IprInner()
                .id(ipr.getId())
                .message(ipr.getMessage())
                .isOK(ipr.getIsOK())
                .number(ipr.getNumber())
                .dual(new DualClass().stringField(ipr.getDual().getStringField()).longField(ipr.getDual().getLongField()))
                .someDate(ipr.getSomeDate());
        response.setIpr(iprInner);
        response.setItems(items.stream().map(item -> new ipr.api.ipr.api.model.Some().id(item.getId()).field1(item.getField1()).field2(item.getField2())).toList());

        return response;
    }
}
