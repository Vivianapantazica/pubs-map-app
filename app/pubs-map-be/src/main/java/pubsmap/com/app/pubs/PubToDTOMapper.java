package pubsmap.com.app.pubs;

import org.springframework.stereotype.Component;
import pubsmap.com.app.clients.Client;
import pubsmap.com.app.clients.ClientDTO;

import java.util.function.Function;

@Component
public class PubToDTOMapper implements Function<Pub, PubDTO> {

    @Override
    public PubDTO apply(Pub pub) {
        return new PubDTO(pub.getPubId(),
                pub.getName(),
                pub.getAddress(),
                pub.getDescription());
    }
}
