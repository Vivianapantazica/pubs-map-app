package pubsmap.com.app.clients;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DTOToClientMapper implements Function<ClientDTO, Client> {

	@Override
	public Client apply(ClientDTO clientDTO) {
		return Client.builder()
				.email(clientDTO.email())
				.password(clientDTO.email())
				.isAdmin(clientDTO.isAdmin())
				.deleted(false)
				.build();
	}
}
