package pubsmap.com.app.clients;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pubsmap.com.app.shared.PersistableRepository;

import java.util.Optional;

@Repository
public interface ClientRepository extends PersistableRepository<Client, Long> {
	@Query( """ 
   			SELECT client FROM Client client WHERE client.email = :email
   			""")
	Optional<Client> getClientByEmail(@Param("email") String email);
}
