package datagenerator;

import exceptions.CategoryException;
import exceptions.TranslocationException;
import exceptions.VehicleException;

public interface DataGenerator {
	void generateTestData() throws VehicleException, TranslocationException, CategoryException;
}
