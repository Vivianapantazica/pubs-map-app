package pubsmap.com.app.pubs;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pubs")
@RequiredArgsConstructor
public class PubController {

    private final PubService pubService;

    @GetMapping
    public ResponseEntity<List<PubDTO>> fetchPubs() {
        return ResponseEntity.ok(pubService.findAll());
    }

    @GetMapping("/{pubId}")
    public ResponseEntity<PubDTO> getPubById(@PathVariable("pubId") Long pubId) {
        return pubService.getPubById(pubId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Resource Not Found"));
    }

    @DeleteMapping("/admin/{pubId}")
    public ResponseEntity<Void> deletePubById(@PathVariable("pubId") Long pubId) {
        pubService.getPubById(pubId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Resource Not Found"));
        pubService.deletePubById(pubId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin")
    public ResponseEntity<PubDTO> addPub(@RequestBody @Valid PubDTO pubDTO) {
        PubDTO newPubDTO = pubService.addPub(pubDTO).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.CONFLICT , "Resource Already Found"));
        return new ResponseEntity<>(newPubDTO, HttpStatus.CREATED);
    }

    @PutMapping("/admin")
    public ResponseEntity<PubDTO> updatePub(@RequestBody @Valid PubDTO pubDTO) {
        return Optional.ofNullable(pubDTO).flatMap(dto -> pubService.updatePub(pubDTO))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND , "Resource Not Found"));
    }
}
