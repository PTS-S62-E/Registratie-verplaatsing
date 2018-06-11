package dto;

import entities.Translocation;
import util.LocalDateTimeParser;

public class TranslocationDto {

	private String serialNumber;
	private double latitude;
	private double longitude;
	private String timestamp;
	private String countryCode;

	public TranslocationDto(){}

	public TranslocationDto(Translocation translocation){
		this.serialNumber = translocation.getSerialNumber();
		this.latitude = translocation.getLatitude();
		this.longitude = translocation.getLongitude();
		this.timestamp = LocalDateTimeParser.localDateTimeToString(translocation.getTimestamp());
		this.countryCode = translocation.getCountryCode();
		this.serialNumber = translocation.getVehicle().getSerialNumber();
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
