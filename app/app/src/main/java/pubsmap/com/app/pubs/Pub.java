package pubsmap.com.app.pubs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pubsmap.com.app.shared.Identifiable;

@Entity(name= "Pub")
@Table(name = "pub")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class Pub implements Identifiable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pub_id")
	private Long pubId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "description")
	private String description;

	@Column(name = "deleted", nullable = false)
	private Boolean deleted;

	@Override
	public Long getId() {
		return pubId;
	}

	// Getter and setter methods
}