package pubsmap.com.app.clients;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pubsmap.com.app.shared.PersistableRepository;

import java.util.Optional;

@Repository
public interface ClientRepository extends PersistableRepository<Client, Long> {
	Optional<Client> findByEmail(String email);
}
