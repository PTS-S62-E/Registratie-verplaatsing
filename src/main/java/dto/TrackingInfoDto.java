package dto;

import java.io.Serializable;

public class TrackingInfoDto implements Serializable {

	String licensePlate;
	InternalTranslocationDto internalTranslocationDto;

	public TrackingInfoDto(String licensePlate, InternalTranslocationDto internalTranslocationDto){
		this.licensePlate = licensePlate;
		this.internalTranslocationDto = internalTranslocationDto;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public InternalTranslocationDto getTranslocation() {
		return internalTranslocationDto;
	}

	public void setTranslocation(InternalTranslocationDto translocation) {
		this.internalTranslocationDto = translocation;
	}

	public InternalTranslocationDto getTranslocationDto() {
		return internalTranslocationDto;
	}

	public void setTranslocationDto(InternalTranslocationDto translocation) {
		this.internalTranslocationDto = translocation;
	}

	public TrackingInfoDto(){}
}
