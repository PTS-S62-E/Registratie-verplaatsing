package datagenerator;

import dao.TranslocationDao;
import dao.VehicleDao;
import entities.Category;
import entities.Translocation;
import entities.Vehicle;
import exceptions.CategoryException;
import exceptions.TranslocationException;
import exceptions.VehicleException;
import io.sentry.Sentry;
import services.CategoryService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		try {

			List<Translocation> translocations = new ArrayList<>();

			LocalDateTime timestamp = LocalDateTime.now();

			Category category = getCategory();
			String licensePlate = getUniqueLicensePlate();

			Vehicle vehicle = new Vehicle(0, licensePlate, "MERCEDES", "VRACHTWAGEN", category, translocations, "serialNumber", "NL");
			Vehicle vehicle2 = new Vehicle(0, licensePlate + "2", "FERRARI", "VRACHTWAGEN", category, translocations, "serialNumber2", "FI");
			vehicleDao.createVehicle(vehicle);
			vehicleDao.createVehicle(vehicle2);

			for (int i = 0; i <= 100; i++) {
				int minutesToAdd = 10;

				if (i == 25 || i == 33 || i == 68 || i == 77) {
					minutesToAdd = 30;
				}

				timestamp = timestamp.plusMinutes(minutesToAdd);
				Translocation translocation = new Translocation(vehicle, i, i, timestamp, "FI", false);
				Translocation translocation2 = new Translocation(vehicle2, i, i, timestamp, "FI", false);
				translocationDao.createTranslocation(translocation);
				translocationDao.createTranslocation(translocation2);
			}
		}
		catch(Exception e){
			Sentry.capture(e.toString());
			System.out.println(e.toString());
		}
	}

	private String getUniqueLicensePlate() throws VehicleException {
		Random rand = new Random();
		String licensePlate = "LICENSPLATE";
		while(vehicleDao.checkIfLicensePlateAlreadyExists(-1, licensePlate)){
			licensePlate += Integer.toString(rand.nextInt(8) + 1);
		}
		return licensePlate;
	}

	private Category getCategory() throws CategoryException {
		Category category;

		List<Category> categories = categoryService.getCategories();

		if (categories == null || categories.size() == 0) {
			category = new Category("TRUCK");
			categoryService.createCategory(category);
		}
		else {
			category = categories.get(0);
		}
		return category;
	}

}
