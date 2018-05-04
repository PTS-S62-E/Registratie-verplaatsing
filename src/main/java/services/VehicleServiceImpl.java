package services;

import dao.VehicleDao;
import dto.VehicleDto;
import entities.Category;
import entities.Vehicle;
import exceptions.CategoryException;
import exceptions.VehicleException;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class VehicleServiceImpl implements VehicleService {

	@Inject
	VehicleDao vehicleDao;

	@Inject
	CategoryService categoryService;

	@Override
	public VehicleDto getVehicle(long id) throws VehicleException {
		return new VehicleDto(vehicleDao.getVehicle(id));
	}

	@Override
	public void createVehicle(VehicleDto vehicleDto) throws VehicleException, CategoryException {
		vehicleDao.createVehicle(convertCreateVehicleDtoToVehicle(vehicleDto));

	}

	@Override
	public void updateVehicle(VehicleDto vehicleDto) throws VehicleException, CategoryException {
		vehicleDao.updateVehicle(convertCreateVehicleDtoToVehicle(vehicleDto));
	}


	private Vehicle convertCreateVehicleDtoToVehicle(VehicleDto vehicleDto) throws VehicleException, CategoryException {

		vehicleDto.setCategory(vehicleDto.getCategory().toUpperCase());

		if(vehicleDao.getVehicleByLicensPlate(vehicleDto.getLicensePlate()) != null){
			throw new VehicleException("There's already a vehicle registered with this license plate.");
		}

		if(!categoryService.checkIfCategoryExists(vehicleDto.getCategory())){
			StringBuilder builder = new StringBuilder();
			builder.append("Category: ");
			builder.append(vehicleDto.getCategory());
			builder.append(" does not exists, please choose an existing category.");
			throw new VehicleException(builder.toString());
		}

		Category category = new Category(vehicleDto.getCategory());

		Vehicle vehicle = new Vehicle(
				vehicleDto.getId(),
				vehicleDto.getLicensePlate(),
				vehicleDto.getBrand(),
				vehicleDto.getType(),
				category,
				null,
				vehicleDto.getHardwareSn());

		return vehicle;
	}
}
