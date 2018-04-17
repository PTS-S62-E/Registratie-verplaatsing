package dto;

import java.util.List;

public class AdministrationDto {
	List<JourneyDto> journeys;

	public AdministrationDto(List<JourneyDto> journeys){
		this.journeys = journeys;
	}

	public List<JourneyDto> getJourneys() {
		return journeys;
	}

	public void setJourneys(List<JourneyDto> journeys) {
		this.journeys = journeys;
	}
}
