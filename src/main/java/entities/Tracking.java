package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
		@NamedQuery(name="Tracking.getTracking",
				query="SELECT DISTINCT(t) FROM Tracking t WHERE licensePlate = :licensePlate")
})
public class Tracking implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable = false)
	private String licensePlate;

	public long getId() {
		return id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public Tracking(){}

	public Tracking(long id, String licensePlate){
		this.id = id;
		this.licensePlate = licensePlate;
	}

	public Tracking(String licensePlate){
		this.licensePlate = licensePlate;
	}
}
