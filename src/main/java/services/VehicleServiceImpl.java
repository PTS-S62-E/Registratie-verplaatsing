package services;

import dao.CategoryDao;
import dao.VehicleDao;
import dto.AdministrationDto;
import dto.ForeignVehicleDto;
import dto.VehicleDto;
import entities.Category;
import entities.Vehicle;
import exceptions.CategoryException;
import exceptions.DateException;
import exceptions.VehicleException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class VehicleServiceImpl implements VehicleService {

	@Inject
	VehicleDao vehicleDao;

	@Inject
	CategoryDao categoryDao;

	@Inject
	TranslocationService translocationService;

	@Override
	public VehicleDto getVehicle(long id) throws VehicleException {
		return new VehicleDto(vehicleDao.getVehicle(id));
	}

	@Override
	public VehicleDto getVehicle(String licensePlate) throws VehicleException {
		return new VehicleDto(vehicleDao.getVehicle(licensePlate));
	}

	@Override
	public void createVehicle(VehicleDto vehicleDto) throws VehicleException, CategoryException {
		checkForRequiredFields(vehicleDto);
		vehicleDao.createVehicle(convertCreateVehicleDtoToVehicle(vehicleDto));
	}

	@Override
	public List<ForeignVehicleDto> getForeignVehiclesAndTranslocations(LocalDateTime startDate, LocalDateTime endDate) throws DateException {

		if (endDate.isBefore(startDate)){
			throw new DateException("enddate cannot be before startdate.");
		}

		List<Vehicle> vehicles = vehicleDao.getAllVehiclesFromOtherCountry();
		List<ForeignVehicleDto> vehiclesThatHaveDriven = new ArrayList<>();

		for(Vehicle vehicle : vehicles){
			AdministrationDto administrationDto = translocationService.getAdministrationDto(vehicle.getId(), startDate, endDate);

			if(administrationDto.getJourneys() != null && administrationDto.getJourneys().size() > 0){
				vehiclesThatHaveDriven.add(new ForeignVehicleDto(vehicle, administrationDto.getJourneys()));
			}
		}

		return vehiclesThatHaveDriven;
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
		if (vehicleDto.getSerialNumber() == null || vehicleDto.getSerialNumber().equals("")){
			throw new VehicleException("Hardware serial number is empty, please provide a hardware serial number.");
		}
		if (vehicleDto.getLicensePlate() == null || vehicleDto.getLicensePlate().equals("")){
			throw new VehicleException("License plate is empty, please provide a license plate.");
		}
		if (vehicleDto.getCountryCode() == null || vehicleDto.getCountryCode().equals("")){
			throw new VehicleException("Country code is empty, please provide a country code.");
		}
	}

	@Override
	public void updateVehicle(VehicleDto vehicleDto) throws VehicleException, CategoryException {

		if (vehicleDao.getVehicle(vehicleDto.getId()) == null){
			StringBuilder builder = new StringBuilder();
			builder.append("Could not find vehicle with id: ");
			builder.append(Long.toString(vehicleDto.getId()));
			builder.append(".");
			throw new VehicleException(builder.toString());
		}

		checkForRequiredFields(vehicleDto);
		vehicleDao.updateVehicle(convertCreateVehicleDtoToVehicle(vehicleDto));
	}


	private Vehicle convertCreateVehicleDtoToVehicle(VehicleDto vehicleDto) throws VehicleException, CategoryException {

		vehicleDto.setCategory(vehicleDto.getCategory().toUpperCase());

		if(vehicleDao.checkIfLicensePlateAlreadyExists(-1, vehicleDto.getLicensePlate())){
			throw new VehicleException("There's already a vehicle registered with this license plate.");
		}

		if(categoryDao.getCategory(vehicleDto.getCategory()) == null){
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
				vehicleDto.getSerialNumber(),
				vehicleDto.getCountryCode());

		return vehicle;
	}
}
