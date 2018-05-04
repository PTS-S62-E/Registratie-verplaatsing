package entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dto.CreateVehicleDto;
import services.TranslocationService;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
		@NamedQuery(name="Vehicle.getVehicleByLicensePlate",
				query="SELECT v FROM Vehicle v WHERE licensePlate = :licensePlate"),
})
public class Vehicle implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(unique = true,
			nullable = false)
	private String licensePlate;
	private String brand;
	private String type;
	@OneToOne
	private Category category;
	@OneToMany(mappedBy = "vehicle")
	@JsonManagedReference
	private List<Translocation> translocations;
	private String hardwareSn;

	public Vehicle(){}

	public Vehicle(String licensePlate, String brand, String type, Category category, List<Translocation> translocations, String hardwareSn ){
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
