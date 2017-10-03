package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class GameRunner {

    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedRate = 2000)
    public void loop() {
        template.convertAndSend("/topic/subscription", "hello");

    }

}
