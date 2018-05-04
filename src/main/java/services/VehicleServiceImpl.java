package services;

import dao.CategoryDao;
import dao.VehicleDao;
import dto.CreateVehicleDto;
import dto.VehicleDto;
import entities.Category;
import entities.Vehicle;
import exceptions.VehicleException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class VehicleServiceImpl implements VehicleService {

	@Inject
	VehicleDao vehicleDao;

	@Inject
	CategoryDao categoryDao;

	@Override
	public VehicleDto getVehicle(long id) throws VehicleException {
		return new VehicleDto(vehicleDao.getVehicle(id));
	}

	@Override
	public void createVehicle(CreateVehicleDto createVehicleDto) throws VehicleException {
		vehicleDao.createVehicle(convertVehicleDtoToVehicle(createVehicleDto));

	}

	@Override
	public void updateVehicle(Vehicle vehicle) {
		vehicleDao.updateVehicle(vehicle);
	}

	private Vehicle convertVehicleDtoToVehicle(CreateVehicleDto createVehicleDto) throws VehicleException {

		if(vehicleDao.getVehicleByLicensPlate(createVehicleDto.getLicensePlate()) != null){
			throw new VehicleException("There's already a vehicle registered with this license plate.");
		}

		Category category = new Category(checkCategory(createVehicleDto.getCategory()));

		Vehicle vehicle = new Vehicle(createVehicleDto.getLicensePlate(),
				createVehicleDto.getBrand(),
				createVehicleDto.getType(),
				category,
				null,
				createVehicleDto.getHardwareSn());

		return vehicle;
	}

	private String checkCategory(String category) throws VehicleException {
		List<Category> categories = categoryDao.getCategories();
		String upperCaseCategory = category.toUpperCase();

		for(Category c : categories){
			if(c.getCategoryName() == upperCaseCategory){
				return c.getCategoryName();
			}
		}

		StringBuilder builder = new StringBuilder();
		builder.append("Category: ");
		builder.append(upperCaseCategory);
		builder.append(" does not exists, please choose an existing category.");
		throw new VehicleException(builder.toString());
	}
}
