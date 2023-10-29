package docker.application.api.some;

import docker.application.services.some.SomeService;
import ipr.api.ipr.api.SomeApiDelegate;
import ipr.api.ipr.api.model.ByIprResponse;
import ipr.api.ipr.api.model.CreateSomeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SomeImpl implements SomeApiDelegate {

    private SomeService someService;

    public SomeImpl(SomeService someService) {
        this.someService = someService;
    }

    @Override
    public ResponseEntity<CreateSomeResponse> apiSomeCreate(Long iprId, String field1, String field2, String meta) {
        try {
            return ResponseEntity.ok(someService.apiSomeCreate(iprId, field1, field2));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CreateSomeResponse().error(e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ByIprResponse> apiSomeGetByIpr(Long ipr) {
        try{
            return ResponseEntity.ok(someService.apiSomeGetByIpr(ipr));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ByIprResponse().error(e.getMessage()));
        }
    }
}
