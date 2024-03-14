package bitcamp.myapp.service;

import bitcamp.myapp.repository.EmitterRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
public class NotificationService {
  private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

  private final EmitterRepository emitterRepository;

  public NotificationService(EmitterRepository emitterRepository) {
    this.emitterRepository = emitterRepository;
  }

  public SseEmitter subscribe(String userId, String lastEventId) {

    String id = userId + "_" + System.currentTimeMillis();

    SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

    emitter.onCompletion(() -> emitterRepository.deleteById(id));
    emitter.onTimeout(() -> emitterRepository.deleteById(id));

    sendToClient(emitter, id, "EventStream Created. [userId=" + userId + "]");

    if (!lastEventId.isEmpty()) {
      Map<String, Object> events = emitterRepository.findAllEventCacheStartWithId(String.valueOf(userId));
      events.entrySet().stream()
          .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
          .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
    }

    return emitter;
  }

  private void sendToClient(SseEmitter emitter, String id, Object data) {
    try {
      emitter.send(SseEmitter.event()
          .id(id)
          .name("sse")
          .data(data));
    } catch (IOException exception) {
      emitterRepository.deleteById(id);
      throw new RuntimeException("연결 오류!");
    }
  }
}
