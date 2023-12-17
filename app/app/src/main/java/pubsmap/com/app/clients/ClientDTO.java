package pubsmap.com.app.clients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClientDTO(Long clientId,
						@NotNull
						String email,
						@NotNull
						@NotBlank
						@Size(min=8, max=255, message = "Min 1, max 255 characters")
						String password,
						@NotNull
						Boolean isAdmin) {
}
