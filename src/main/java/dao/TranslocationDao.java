package dao;

import entities.Translocation;

import java.util.List;

public interface TranslocationDao {
	List<Translocation> getTranslocationsByVehicleId(long id);
	Translocation getTranslocation(long id);
	void createTranslocation(Translocation translocation);
	void updateTranslocation(Translocation translocation);
}
