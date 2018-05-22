package datagenerator;

import dao.TranslocationDao;
import dao.VehicleDao;
import entities.Category;
import entities.Translocation;
import entities.Vehicle;
import exceptions.CategoryException;
import exceptions.TranslocationException;
import exceptions.VehicleException;
import services.CategoryService;

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

	@Inject
	CategoryService categoryService;

	@Override
	public void generateTestData() throws VehicleException, TranslocationException, CategoryException {

		List<Translocation> translocations = new ArrayList<>();

		LocalDateTime timestamp = LocalDateTime.now();

		Category category = new Category("TRUCK");
		categoryService.createCategory(category);

		Vehicle vehicle = new Vehicle(0, "LICENSPLATE", "MERCEDES", "VRACHTWAGEN", category, translocations, "HARDWARESN", "NL");
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
