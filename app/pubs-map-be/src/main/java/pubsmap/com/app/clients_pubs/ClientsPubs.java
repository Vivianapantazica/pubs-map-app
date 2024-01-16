package pubsmap.com.app.clients_pubs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name= "ClientsPubs")
@Table(name = "clients_pubs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientsPubs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clients_pubs_id")
    private Long clientsPubsId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "pub_id", nullable = false)
    private Long pubId;
}
