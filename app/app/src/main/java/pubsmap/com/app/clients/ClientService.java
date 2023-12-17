package pubsmap.com.app.clients;

import java.util.List;
import java.util.Optional;

public interface ClientService {
	List<ClientDTO> findAll();
	Optional<ClientDTO> getClientById(final Long clientId);
	void deleteClientById(final Long clientId);
	Optional<ClientDTO> addClient(final ClientDTO clientDTO);
	Optional<ClientDTO> updateClient(final ClientDTO clientDTO);
}
