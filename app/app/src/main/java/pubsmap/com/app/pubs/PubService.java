package pubsmap.com.app.pubs;

import java.util.List;
import java.util.Optional;

public interface PubService {
    List<PubDTO> findAll();
    Optional<PubDTO> getPubById(final Long pubId);
    void deletePubById(final Long pubId);
    Optional<PubDTO> addPub(final PubDTO pubDTO);
    Optional<PubDTO> updatePub(final PubDTO pubDTO);
}
