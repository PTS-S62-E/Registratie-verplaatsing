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
		checkForRequiredFields(vehicleDto);
		vehicleDao.createVehicle(convertCreateVehicleDtoToVehicle(vehicleDto));
	}

	private void checkForRequiredFields(VehicleDto vehicleDto) throws VehicleException, CategoryException {
		if (vehicleDto == null){
			throw new VehicleException("Vehicle is null, please provide a vehicle.");
		}
		if (vehicleDto.getBrand() == null || vehicleDto.getBrand().equals("")){
			throw new VehicleException("Brand is empty, please provide a brand.");
		}
		if (vehicleDto.getCategory() == null || vehicleDto.getCategory().equals("")){
			throw new CategoryException("Category is empty, please provide a category.");
		}
		if (vehicleDto.getType() == null || vehicleDto.getType().equals("")){
			throw new VehicleException("Type is empty, please provide a type.");
		}
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
