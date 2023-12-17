package pubsmap.com.app.clients;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/clients")
@RequiredArgsConstructor
public class ClientController {
	private final ClientService clientService;

	@GetMapping
	public ResponseEntity<List<ClientDTO>> fetchClients() {
		return ResponseEntity.ok(clientService.findAll());
	}

	@GetMapping("/{clientId}")
	public ResponseEntity<ClientDTO> getClientById(@PathVariable("clientId") Long clientId) {
		return clientService.getClientById(clientId)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Resource Not Found"));
	}

	@DeleteMapping("/{clientId}")
	public ResponseEntity<Void> deleteClientById(@PathVariable("clientId") Long clientId) {
		clientService.getClientById(clientId)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Resource Not Found"));
		clientService.deleteClientById(clientId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<ClientDTO> addClient(@RequestBody @Valid ClientDTO clientDTO) {
		ClientDTO newClientDTO = clientService.addClient(clientDTO).orElseThrow(
				() -> new ResponseStatusException(
						HttpStatus.CONFLICT , "Resource Already Found"));
		return new ResponseEntity<>(newClientDTO, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<ClientDTO> updateClient(@RequestBody @Valid ClientDTO clientDTO) {
		return Optional.ofNullable(clientDTO).flatMap(dto -> clientService.updateClient(clientDTO))
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND , "Resource Not Found"));
	}
}
