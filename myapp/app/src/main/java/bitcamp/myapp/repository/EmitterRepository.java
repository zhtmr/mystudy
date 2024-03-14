package bitcamp.myapp.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;

@Repository
public class EmitterRepository {
  private Map<String, SseEmitter> emitterMap = new HashMap<>();


  public SseEmitter save(String id, SseEmitter sseEmitter) {
    return emitterMap.put(id, sseEmitter);
  }


  public void deleteById(String id) {
    emitterMap.remove(id);
  }


}
