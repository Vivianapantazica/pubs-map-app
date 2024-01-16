package pubsmap.com.app.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
}
