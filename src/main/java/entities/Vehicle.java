package entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
		@NamedQuery(name="Vehicle.getVehicleByLicensePlate",
				query="SELECT v FROM Vehicle v WHERE licensePlate = :licensePlate"),
		@NamedQuery(name="Vehicle.checkIfLicensePlateAlreadyExists",
				query="SELECT v FROM Vehicle v WHERE licensePlate = :licensePlate AND id != :id"),
})
public class Vehicle implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(unique = true)
	private String licensePlate;
	@Column(nullable = false)
	@Size(min=1)
	private String brand;
	@Column(nullable = false)
	@Size(min=1)
	private String type;
	@Column(nullable = false)
	@Size(min=1)
	private String hardwareSn;
	@OneToOne
	private Category category;
	@OneToMany(mappedBy = "vehicle")
	@JsonManagedReference
	private List<Translocation> translocations;

	public Vehicle(){}

	public Vehicle(long id, String licensePlate, String brand, String type, Category category, List<Translocation> translocations, String hardwareSn ){
		this.id = id;
		this.licensePlate = licensePlate;
		this.brand = brand;
		this.type = type;
		this.category = category;
		this.translocations = translocations;
		this.hardwareSn = hardwareSn;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String merk) {
		this.brand = merk;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Translocation> getTranslocations() {
		return translocations;
	}

	public void setTranslocations(List<Translocation> translocations) {
		this.translocations = translocations;
	}

	public String getHardwareSn() {
		return hardwareSn;
	}

	public void setHardwareSn(String hardwareSn) {
		this.hardwareSn = hardwareSn;
	}
}
