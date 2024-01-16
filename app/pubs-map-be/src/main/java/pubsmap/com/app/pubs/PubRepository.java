package pubsmap.com.app.pubs;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pubsmap.com.app.shared.PersistableRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PubRepository extends PersistableRepository<Pub, Long> {
    @Query( """ 
   			SELECT pub FROM Pub pub WHERE pub.address = :address
   			""")
    Optional<Pub> getPubByAddress(@Param("address") String address);

    @Query(value = """
SELECT pub.pub_id, pub.name, pub.address, pub.description, pub.deleted FROM pub INNER JOIN clients_pubs ON pub.pub_id = clients_pubs.pub_id WHERE clients_pubs.client_id = :clientId
			""", nativeQuery = true)
    List<Pub> findPubsById(@Param("clientId") Long clientId);
}
