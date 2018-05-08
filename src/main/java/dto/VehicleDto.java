package dto;

import entities.Vehicle;
import java.io.Serializable;

public class VehicleDto implements Serializable {

	private long id;
	private String licensePlate;
	private String brand;
	private String type;
	private String category;
	private String hardwareSn;

	public VehicleDto(){}

	public VehicleDto(Vehicle vehicle){
		this.id = vehicle.getId();
		this.licensePlate = vehicle.getLicensePlate();
		this.brand = vehicle.getBrand();
		this.type = vehicle.getType();
		this.category = vehicle.getCategory().getName();
		this.hardwareSn = vehicle.getHardwareSn();
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

	public void setBrand(String brand) {
		this.brand = brand;
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

	public void setCategory(String category) {
		this.category = category;
	}

	public String getHardwareSn() {
		return hardwareSn;
	}

	public void setHardwareSn(String hardwareSn) {
		this.hardwareSn = hardwareSn;
	}
}
