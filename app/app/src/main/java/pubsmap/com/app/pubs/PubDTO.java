package pubsmap.com.app.pubs;

import jakarta.validation.constraints.NotNull;

public record PubDTO(
        Long pubId,
        @NotNull
        String name,
        @NotNull
        String address,
        @NotNull
        String description
) {
}
