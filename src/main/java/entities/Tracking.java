package entities;

import dto.TrackingDto;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@NamedQueries({
		@NamedQuery(name="Tracking.getTracking",
				query="SELECT t FROM Tracking t WHERE sessionId = :sessionId AND licensePlate = :licensePlate"),
		@NamedQuery(name="Tracking.getTracking",
				query="SELECT t FROM Tracking t WHERE licensePlate = :licensePlate"),
})
public class Tracking implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable = false)
	@Size(min=1)
	private String licensePlate;
	@Column(unique = true, nullable = false)
	@Size(min=1)
	private String sessionId;

	public long getId() {
		return id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Tracking(){}

	public Tracking(long id, String licensePlate, String sessionId){
		this.id = id;
		this.licensePlate = licensePlate;
		this.sessionId = sessionId;
	}

	public Tracking(TrackingDto trackingDto){
		this.sessionId = trackingDto.getSessionId();
		this.licensePlate = trackingDto.getLicensePlate();
	}
}
