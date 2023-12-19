package pubsmap.com.app.pubs;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pubsmap.com.app.clients.Client;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PubServiceImpl implements PubService {

    private final PubRepository pubRepository;
    private final PubToDTOMapper pubToDTOMapper;
    private final DTOToPubMapper dtoToPubMapper;

    @Override
    public List<PubDTO> findAll() {
        return pubRepository.findAll().stream().map(pubToDTOMapper).collect(Collectors.toList());
    }

    @Override
    public Optional<PubDTO> getPubById(Long pubId) {
        return pubRepository.findById(pubId).map(pubToDTOMapper);
    }

    @Override
    public void deletePubById(Long pubId) {
        pubRepository.deleteById(pubId);
    }

    @Override
    public Optional<PubDTO> addPub(PubDTO pubDTO) {
        if (pubRepository.getPubByAddress(pubDTO.address()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT , "Resource Already Found");
        }
        Pub pub = dtoToPubMapper.apply(pubDTO);
        pubRepository.save(pub);
        return Optional.of(pubDTO);
    }

    @Override
    public Optional<PubDTO> updatePub(PubDTO pubDTO) {
        Pub pub = pubRepository.findById(pubDTO.pubId()).orElseThrow();
        pub.setName(pubDTO.name());
        pub.setAddress(pubDTO.address());
        pub.setDescription(pubDTO.description());
        pubRepository.save(pub);
        return Optional.of(pubDTO);
    }
}
