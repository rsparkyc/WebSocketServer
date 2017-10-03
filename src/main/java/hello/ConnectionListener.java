package hello;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class ConnectionListener implements ApplicationListener {

    Map<String, String> clientsWithAuth = new ConcurrentHashMap<>();

    @EventListener
    private void onSessionConnectedEvent(SessionConnectedEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        List<String> auth = sha.getNativeHeader("Authorization"); // not found here

        String actualAuthHeader =
                ((List) ((Map) ((GenericMessage) sha.getHeader("simpConnectMessage")).getHeaders()
                        .get("nativeHeaders")).get("Authorization")).get(0).toString();

        clientsWithAuth.put(sha.getSessionId(), actualAuthHeader);
    }

    @EventListener
    private void onSessionDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        clientsWithAuth.remove(sha.getSessionId());
    }

    @Override
    public void onApplicationEvent(final ApplicationEvent event) {

    }
}
