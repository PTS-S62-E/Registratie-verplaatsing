package datagenerator;

import exceptions.TranslocationException;
import exceptions.VehicleException;

public interface DataGenerator {
	void generateTestData() throws VehicleException, TranslocationException;
}
