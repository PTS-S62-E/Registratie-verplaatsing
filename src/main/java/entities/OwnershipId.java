package entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OwnershipId implements Serializable {
	private long vehicleId;
	private long ownerId;

	public OwnershipId(){}

	public long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
}