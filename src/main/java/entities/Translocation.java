package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rekeningrijden.europe.interfaces.ITransLocation;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NamedQueries({
		@NamedQuery(name="Translocation.getTranslocationsByVehicleIdAndTimePeriod",
				query="SELECT t FROM Translocation t WHERE vehicle_id = :vehicleId AND timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp ASC"),
		@NamedQuery(name="Translocation.getTranslocationsByVehicleId",
				query="SELECT t FROM Translocation t WHERE vehicle_id = :vehicleId ORDER BY t.id DESC")
})
public class Translocation implements ITransLocation, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	@JsonBackReference
	private Vehicle vehicle;
	private double latitude;
	private double longitude;
	private LocalDateTime timestamp;
	private String countryCode;
	private boolean flagged;

	public Translocation(Vehicle vehicle, double latitude, double longitude, LocalDateTime timestamp, String countryCode, boolean flagged) {
		this.vehicle = vehicle;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timestamp = timestamp;
		this.countryCode = countryCode;
		this.flagged = flagged;
	}

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

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public Double getLat() {
		return latitude;
	}

	@Override
	public Double getLon() {
		return longitude;
	}

	@Override
	public String getDateTime() {
		return null;
	}

	@Override
	public String getSerialNumber() {
		return null;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}
}
