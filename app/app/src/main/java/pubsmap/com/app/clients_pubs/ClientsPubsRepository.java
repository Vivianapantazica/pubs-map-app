package pubsmap.com.app.clients_pubs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientsPubsRepository extends JpaRepository<ClientsPubs, Long> {
    Optional<ClientsPubs> findByClientIdAndPubId(Long clientId, Long pubId);
}
