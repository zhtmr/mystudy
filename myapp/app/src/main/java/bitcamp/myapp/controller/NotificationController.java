package bitcamp.myapp.controller;

import bitcamp.myapp.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class NotificationController {

  private final NotificationService notificationService;

  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
  public SseEmitter subscribe(
      @PathVariable String id,
      @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {

    return notificationService.subscribe(id, lastEventId);
  }

}

