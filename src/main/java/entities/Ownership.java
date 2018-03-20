package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@NamedQueries({
		@NamedQuery(name="Ownership.getOwnershipByVehicleId",
				query="SELECT o FROM Ownership o WHERE o.id.vehicleId = :vehicleId"),
		@NamedQuery(name="Ownership.getOwnershipByOwnerId",
				query="SELECT o FROM Ownership o WHERE o.id.ownerId = :ownerId"),
})
public class Ownership implements Serializable {
	@EmbeddedId
	private OwnershipId id;
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp from;
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp to;

	public Ownership(){}

	public OwnershipId getId() {
		return id;
	}

	public void setId(OwnershipId id) {
		this.id = id;
	}

	public Timestamp getFrom() {
		return from;
	}

	public void setFrom(Timestamp from) {
		this.from = from;
	}

	public Timestamp getTo() {
		return to;
	}

	public void setTo(Timestamp to) {
		this.to = to;
	}
}