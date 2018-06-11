package dto;

import java.util.ArrayList;
import java.util.List;

public class JourneyDto {

	List<InternalTranslocationDto> internalTranslocationDtos;

	public JourneyDto(){
		this.internalTranslocationDtos = new ArrayList<>();
	}

	public List<InternalTranslocationDto> getTranslocations() {
		return internalTranslocationDtos;
	}

	public void setTranslocations(List<InternalTranslocationDto> translocationDtos) {
		this.internalTranslocationDtos = translocationDtos;
	}

	public void addTranslocation(InternalTranslocationDto translocationDto){
		this.internalTranslocationDtos.add(translocationDto);
	}
}
