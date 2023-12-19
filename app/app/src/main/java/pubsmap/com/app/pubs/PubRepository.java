package pubsmap.com.app.pubs;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pubsmap.com.app.shared.PersistableRepository;

import java.util.Optional;

@Repository
public interface PubRepository extends PersistableRepository<Pub, Long> {
    @Query( """ 
   			SELECT pub FROM Pub pub WHERE pub.address = :address
   			""")
    Optional<Pub> getPubByAddress(@Param("address") String address);
}
