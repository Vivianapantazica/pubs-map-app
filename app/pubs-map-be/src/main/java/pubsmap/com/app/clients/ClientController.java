package pubsmap.com.app.clients;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pubsmap.com.app.pubs.PubDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clients")
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

	@GetMapping("/{clientId}/pubs")
	public ResponseEntity<List<PubDTO>> getPubsByClientId(@PathVariable("clientId") Long clientId) {
		return ResponseEntity.ok(clientService.getPubsById(clientId));
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

	@DeleteMapping("/{clientId}/pubs/{pubId}")
	public ResponseEntity<Void> deletePubById(@PathVariable("clientId") Long clientId, @PathVariable("pubId") Long pubId) {
		clientService.deletePubById(clientId, pubId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<ClientDTO> addClient(@RequestBody @Valid ClientDTO clientDTO) {
		ClientDTO newClientDTO = clientService.addClient(clientDTO).orElseThrow(
				() -> new ResponseStatusException(
						HttpStatus.CONFLICT , "Resource Already Found"));
		return new ResponseEntity<>(newClientDTO, HttpStatus.CREATED);
	}

	@PostMapping("/{clientId}/pubs/{pubId}")
	public ResponseEntity<PubDTO> addPubToClient(@PathVariable("clientId") Long clientId, @PathVariable("pubId") Long pubId) {
		PubDTO pubAddedToClientDTO = clientService.addPubToClient(clientId, pubId).orElseThrow(
				() -> new ResponseStatusException(
						HttpStatus.CONFLICT , "Resource Already Found"));
		return new ResponseEntity<>(pubAddedToClientDTO, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<ClientDTO> updateClient(@RequestBody @Valid ClientDTO clientDTO) {
		return Optional.ofNullable(clientDTO).flatMap(dto -> clientService.updateClient(clientDTO))
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND , "Resource Not Found"));
	}
}
