package pubsmap.com.app.shared;

import java.io.Serializable;

public interface Identifiable<ID extends Serializable> {
	ID getId();
}
