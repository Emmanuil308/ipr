package docker.application.api.ipr;

import docker.application.services.ipr.IprService;
import ipr.api.ipr.api.IprApiDelegate;
import ipr.api.ipr.api.model.CreateDeleteResponse;
import ipr.api.ipr.api.model.CreateRequest;
import ipr.api.ipr.api.model.IprInner;
import ipr.api.ipr.api.model.LogResponseInner;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class IprImpl implements IprApiDelegate {

    private IprService iprService;
    private KafkaTemplate<String, String> kafkaTemplate;

    public IprImpl(IprService iprService, KafkaTemplate<String, String> kafkaTemplate) {
        this.iprService = iprService;
        this.kafkaTemplate = kafkaTemplate;
    }

    private List<LogResponseInner> logKeeper = new ArrayList<>();


    public void sendMessage(String message) {
        kafkaTemplate.send("ipr", message);
    }

    @KafkaListener(topics = "ipr", groupId = "groupId")
    public void listenGroupId(String message) {
        this.logKeeper.add(new LogResponseInner().key(LocalDateTime.now().withNano(0)).value(message));
    }

    @Override
    public ResponseEntity<CreateDeleteResponse> apiCreate(CreateRequest createRequest) {
        try{
            var response = iprService.apiCreate(createRequest);
            sendMessage(response.getMessage());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            sendMessage(String.format("Не удалось сохранить нового пользователя, произошла ошибка: %s", e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<List<IprInner>> apiRead() {
        var response = iprService.apiRead();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<LogResponseInner>> apiLogs() {
        return ResponseEntity.ok(this.logKeeper);
    }
}
