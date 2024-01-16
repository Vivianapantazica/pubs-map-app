package pubsmap.com.app.pubs;

import org.springframework.stereotype.Component;
import pubsmap.com.app.clients.Client;
import pubsmap.com.app.clients.ClientDTO;

import java.util.function.Function;

@Component
public class DTOToPubMapper implements Function<PubDTO, Pub> {

    @Override
    public Pub apply(PubDTO pubDTO) {
        return Pub.builder()
                .name(pubDTO.name())
                .address(pubDTO.address())
                .description(pubDTO.description())
                .deleted(false)
                .build();
    }
}
