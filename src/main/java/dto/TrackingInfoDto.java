package dto;

import java.io.Serializable;
import java.util.List;

public class TrackingInfoDto implements Serializable {

	List<TrackingDto> trackers;
	TranslocationDto translocation;

	public TrackingInfoDto(){}

	public TrackingInfoDto(List<TrackingDto> trackers, TranslocationDto translocationDto){
		this.trackers = trackers;
		this.translocation = translocationDto;
	}

	public List<TrackingDto> getTracker() {
		return trackers;
	}

	public void setTracker(List<TrackingDto> trackers) {
		this.trackers = trackers;
	}

	public TranslocationDto getTranslocationDto() {
		return translocation;
	}

	public void setTranslocationDto(TranslocationDto translocation) {
		this.translocation = translocation;
	}
}
