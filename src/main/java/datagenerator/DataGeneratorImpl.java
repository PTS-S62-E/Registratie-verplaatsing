package datagenerator;

import dao.TranslocationDao;
import dao.VehicleDao;
import entities.Translocation;
import entities.Vehicle;
import exceptions.TranslocationException;
import exceptions.VehicleException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DataGeneratorImpl implements DataGenerator {

	@Inject
	VehicleDao vehicleDao;

	@Inject
	TranslocationDao translocationDao;

	@Override
	public void generateTestData() throws VehicleException, TranslocationException {

		List<Translocation> translocations = new ArrayList<>();

		LocalDateTime timestamp = LocalDateTime.now();


		Vehicle vehicle = new Vehicle("LICENSPLATE", "MERCEDES", "VRACHTWAGEN", "DIKKE BAK", translocations, "HARDWARESN");
		vehicleDao.createVehicle(vehicle);

		for (int i = 0; i <= 100; i++ ){
			int minutesToAdd = 10;

			if (i == 25 || i == 33 || i == 68 || i == 77){
				minutesToAdd = 30;
			}

			timestamp = timestamp.plusMinutes(minutesToAdd);
			Translocation translocation = new Translocation(vehicle, i, i, timestamp, "FI", false);
			translocationDao.createTranslocation(translocation);
		}
	}
}
