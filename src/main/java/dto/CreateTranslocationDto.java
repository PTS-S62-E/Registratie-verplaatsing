package dto;

import entities.Translocation;
import util.LocalDateTimeParser;

public class CreateTranslocationDto {

	private long vehicleId;
	private String licensePlate;
	private double latitude;
	private double longitude;
	private String timestamp;
	private String countryCode;

	public CreateTranslocationDto(){}

	public CreateTranslocationDto(Translocation translocation){
		this.vehicleId = translocation.getVehicle().getId();
		this.licensePlate = translocation.getVehicle().getLicensePlate();
		this.latitude = translocation.getLatitude();
		this.longitude = translocation.getLongitude();
		this.timestamp = LocalDateTimeParser.localDateTimeToString(translocation.getTimestamp());
		this.countryCode = translocation.getCountryCode();
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
