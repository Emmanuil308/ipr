package docker.application.services.some;

import ipr.api.ipr.api.model.ByIprResponse;
import ipr.api.ipr.api.model.CreateSomeResponse;

public interface SomeService {

    CreateSomeResponse apiSomeCreate(Long iprId, String field1, String field2);
    ByIprResponse apiSomeGetByIpr(Long ipr);
}
