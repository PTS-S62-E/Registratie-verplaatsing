package dto;

import entities.Translocation;
import util.LocalDateTimeParser;

import java.io.Serializable;

public class TranslocationDto implements Serializable {

	private long id;
	private double latitude;
	private double longitude;
	private String timestamp;
	private String countryCode;

	public TranslocationDto(){}

	public TranslocationDto(Translocation translocation){
		this.id = translocation.getId();
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

	public long getTranslocationId() {
		return id;
	}

	public void setTranslocationId(long id) {
		this.id = id;
	}
}
