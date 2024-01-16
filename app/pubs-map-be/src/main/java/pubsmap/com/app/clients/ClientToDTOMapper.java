package pubsmap.com.app.clients;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ClientToDTOMapper implements Function<Client, ClientDTO> {
	@Override
	public ClientDTO apply(Client client) {
		return new ClientDTO(client.getClientId(),
							 client.getEmail(),
							 client.getPassword(),
							 client.getIsAdmin());
	}
}
