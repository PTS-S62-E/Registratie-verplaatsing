package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NamedQueries({
		@NamedQuery(name="Translocation.getTranslocationsByVehicleId",
				query="SELECT t FROM Translocation t WHERE vehicle_id = :vehicleId"),
		@NamedQuery(name="Translocation.getTranslocationsByLicensePlateAndTimePeriod",
				query="SELECT t FROM Translocation t WHERE vehicle_id IS " +
						"(SELECT id FROM Vehicle v WHERE licensePlate = :licensePlate)" +
						"AND timestamp BETWEEN :startDate AND :endDate"),
})
public class Translocation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	@JsonBackReference
	private Vehicle vehicle;
	private double latitude;

	public Translocation(Vehicle vehicle, double latitude, double longitude, LocalDateTime timestamp) {
		this.vehicle = vehicle;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timestamp = timestamp;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	private double longitude;
	private LocalDateTime timestamp;

	public Translocation() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longitude;
	}

	public void setLongtitude(double longtitude) {
		this.longitude = longtitude;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime localDateTime) {
		this.timestamp = timestamp;
	}
}
