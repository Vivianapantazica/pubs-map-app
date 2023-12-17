package pubsmap.com.app.shared;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface PersistableRepository<T extends Identifiable<ID>, ID extends Serializable> extends JpaRepository<T, ID> {
	@Override
	@Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND (e.deleted = false)")
	Optional<T> findById(@Param("id") ID id);

	@Query("UPDATE #{#entityName} e SET e.deleted = true WHERE e.id = :id")
	@Modifying
	@Transactional
	void deleteById(@Param("id") ID id);

	@Override
	@Transactional
	default void delete(final T entity) {
		deleteById(entity.getId());
	}
}
