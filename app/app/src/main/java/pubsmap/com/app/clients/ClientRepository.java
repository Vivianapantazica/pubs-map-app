package pubsmap.com.app.clients;

import org.springframework.stereotype.Repository;
import pubsmap.com.app.shared.PersistableRepository;

@Repository
public interface ClientRepository extends PersistableRepository<Client, Long> {

}
