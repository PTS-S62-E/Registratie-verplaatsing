package entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dao.OwnerDao;
import dao.VehicleDao;
import dto.OwnershipDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Ownership implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;
	private LocalDateTime fromDate;
	private LocalDateTime toDate;

	public Ownership(){}

	public Ownership(Owner owner, Vehicle vehicle, LocalDateTime fromDate, LocalDateTime toDate) {
		this.owner = owner;
		this.vehicle = vehicle;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public LocalDateTime getFromDate() {
		return fromDate;
	}

	public void setFrom(LocalDateTime fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDateTime getToDate() {
		return toDate;
	}

	public void setToDate (LocalDateTime toDate) {
		this.toDate = toDate;
	}
}