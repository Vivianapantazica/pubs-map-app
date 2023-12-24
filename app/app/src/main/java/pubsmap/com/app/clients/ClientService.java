package pubsmap.com.app.clients;

import pubsmap.com.app.pubs.PubDTO;

import java.util.List;
import java.util.Optional;

public interface ClientService {
	List<ClientDTO> findAll();
	Optional<ClientDTO> getClientById(final Long clientId);
	Optional<Client> findClientByEmail(final String email);
	List<PubDTO> getPubsById(final Long clientId);
	void deleteClientById(final Long clientId);
	void deletePubById(final Long clientId, final Long pubId);
	Optional<ClientDTO> addClient(final ClientDTO clientDTO);
	Optional<ClientDTO> updateClient(final ClientDTO clientDTO);
	Optional<PubDTO> addPubToClient(Long clientId, Long pubId);
}
