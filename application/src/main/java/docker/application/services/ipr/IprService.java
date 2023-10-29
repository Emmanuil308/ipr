package docker.application.services.ipr;

import ipr.api.ipr.api.model.CreateDeleteResponse;
import ipr.api.ipr.api.model.CreateRequest;
import ipr.api.ipr.api.model.IprInner;

import java.util.List;

public interface IprService {

    CreateDeleteResponse apiCreate(CreateRequest createRequest);

    List<IprInner> apiRead();

}
