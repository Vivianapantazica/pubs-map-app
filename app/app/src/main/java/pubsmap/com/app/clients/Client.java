package pubsmap.com.app.clients;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pubsmap.com.app.shared.Identifiable;

@Entity(name= "Client")
@Table(name = "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE client SET deleted = true WHERE client_id = ?")
@Where(clause = "deleted = false")
public class Client implements Identifiable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "client_id")
	private Long clientId;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "is_admin", nullable = false)
	private Boolean isAdmin;

	@Column(name = "deleted", nullable = false)
	private Boolean deleted;

	@Override
	public Long getId() {
		return clientId;
	}

	// Getter and setter methods
}
