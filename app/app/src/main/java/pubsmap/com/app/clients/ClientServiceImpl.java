package pubsmap.com.app.clients;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pubsmap.com.app.clients_pubs.ClientsPubs;
import pubsmap.com.app.clients_pubs.ClientsPubsRepository;
import pubsmap.com.app.pubs.Pub;
import pubsmap.com.app.pubs.PubDTO;
import pubsmap.com.app.pubs.PubRepository;
import pubsmap.com.app.pubs.PubToDTOMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {
	private final ClientRepository clientRepository;
	private final PubRepository pubRepository;
	private final ClientsPubsRepository clientsPubsRepository;
	private final ClientToDTOMapper clientToDTOMapper;
	private final DTOToClientMapper dtoToClientMapper;
	private final PubToDTOMapper pubToDTOMapper;

	@Override
	public List<ClientDTO> findAll() {
		return clientRepository.findAll().stream().map(clientToDTOMapper).collect(Collectors.toList());
	}

	@Override
	public Optional<ClientDTO> getClientById(Long clientId) {
		return clientRepository.findById(clientId).map(clientToDTOMapper);
	}

	@Override
	public List<PubDTO> getPubsById(Long clientId) {
		List<Pub> pubs = pubRepository.findPubsById(clientId);
		return pubs.stream().map(pubToDTOMapper).collect(Collectors.toList());
	}

	@Override
	public void deleteClientById(Long clientId) {
		clientRepository.deleteById(clientId);
	}

	@Override
	public void deletePubById(final Long clientId, final Long pubId) {
		clientsPubsRepository.findByClientIdAndPubId(clientId, pubId).ifPresent(clientsPubs ->
				clientsPubsRepository.deleteById(clientsPubs.getClientsPubsId()));
	}

	@Override
	public Optional<ClientDTO> addClient(ClientDTO clientDTO) {
		if (clientRepository.getClientByEmail(clientDTO.email()).isPresent()) {
			throw new ResponseStatusException(
					HttpStatus.CONFLICT , "Resource Already Found");
		}
		Client client = dtoToClientMapper.apply(clientDTO);
		clientRepository.save(client);
		return Optional.of(clientDTO);
	}

	@Override
	public Optional<PubDTO> addPubToClient(Long clientId, Long pubId) {
		Optional<Pub> pub = pubRepository.findById(pubId);
		Optional<Client> client = clientRepository.findById(clientId);
		if (pub.isEmpty()) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND , "Pub does not exist");
		}
		if (client.isEmpty()) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND , "Client does not exist");
		}
		clientsPubsRepository.save(ClientsPubs.builder().clientId(clientId).pubId(pubId).build());
		return Optional.of(pubToDTOMapper.apply(pub.get()));
	}

	@Override
	public Optional<ClientDTO> updateClient(ClientDTO clientDTO) {
		Client client = clientRepository.findById(clientDTO.clientId()).orElseThrow();
		client.setEmail(clientDTO.email());
		client.setPassword(clientDTO.password());
		client.setIsAdmin(clientDTO.isAdmin());
		clientRepository.save(client);
		return Optional.of(clientDTO);
	}
}
