package entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Vehicle implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(unique = true,
			nullable = false)
	private String licensePlate;
	private String brand;
	private String type;
	private String category;

	@OneToMany(mappedBy = "vehicle")
	private List<Ownership> owners;

	@OneToMany(mappedBy = "vehicle")
	@JsonManagedReference
	private List<Translocation> translocations;
	private String hardwareSN;

	public Vehicle(){}

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String categorie) {
		this.category = categorie;
	}

	public List<Ownership> getOwners() {
		return owners;
	}

	public void setOwners(List<Ownership> owners) {
		this.owners = owners;
	}

	public List<Translocation> getTranslocations() {
		return translocations;
	}

	public void setTranslocations(List<Translocation> translocations) {
		this.translocations = translocations;
	}

	public String getHardwareSN() {
		return hardwareSN;
	}

	public void setHardwareSN(String hardwareSN) {
		this.hardwareSN = hardwareSN;
	}
}
