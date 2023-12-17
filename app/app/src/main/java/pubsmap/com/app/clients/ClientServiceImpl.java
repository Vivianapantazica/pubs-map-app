package pubsmap.com.app.clients;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {
	private final ClientRepository clientRepository;
	private final ClientToDTOMapper clientToDTOMapper;
	private final DTOToClientMapper dtoToClientMapper;

	@Override
	public List<ClientDTO> findAll() {
		return clientRepository.findAll().stream().map(clientToDTOMapper).collect(Collectors.toList());
	}

	@Override
	public Optional<ClientDTO> getClientById(Long clientId) {
		return clientRepository.findById(clientId).map(clientToDTOMapper);
	}

	@Override
	public void deleteClientById(Long clientId) {
		clientRepository.deleteById(clientId);
	}

	@Override
	public Optional<ClientDTO> addClient(ClientDTO clientDTO) {
		Client client = dtoToClientMapper.apply(clientDTO);
		clientRepository.save(client);
		return Optional.of(clientDTO);
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
