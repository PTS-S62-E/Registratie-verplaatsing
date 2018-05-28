package dto;

import entities.Tracking;

import java.io.Serializable;

public class TrackingDto implements Serializable {

	private String licensePlate;
	private String sessionId;

	public TrackingDto() {}

	public TrackingDto(String licensePlate, String sessionId) {
		this.licensePlate = licensePlate;
		this.sessionId = sessionId;
	}

	public TrackingDto(Tracking tracking) {
		this.licensePlate = tracking.getLicensePlate();
		this.sessionId = tracking.getSessionId();
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public String getSessionId() {
		return sessionId;
	}
}
