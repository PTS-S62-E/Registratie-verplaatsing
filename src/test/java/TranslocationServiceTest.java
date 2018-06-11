import dao.TranslocationDaoImpl;
import dto.AdministrationDto;
import dto.InternalTranslocationDto;
import entities.Category;
import entities.Translocation;
import entities.Vehicle;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import services.TranslocationServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TranslocationServiceTest {

	@Mock
	TranslocationDaoImpl translocationDaoMock;

	@InjectMocks
	TranslocationServiceImpl translocationService;

	private static List<InternalTranslocationDto> translocationsToDivide;

	@Before
	public void setupBeforeMethod() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeClass
	public static void setup() {
		LocalDateTime timestamp = LocalDateTime.now();

		translocationsToDivide = new ArrayList<>();

		Category category = new Category("DIESEL");
		Vehicle vehicle = new Vehicle(0, "DA-NK-69", "BMW", "type", category, null, "1337-420-8008135", "FI");

		for (int i = 0; i < 10; i++) {

				Translocation translocation;
				InternalTranslocationDto internalTranslocationDto;

				if ( i == 5) {
					timestamp = timestamp.plusMinutes(25);
					translocation = new Translocation(vehicle, 1, 1, timestamp, "FI", false);
					internalTranslocationDto = new InternalTranslocationDto(translocation);
				}
				else{
					timestamp = timestamp.plusMinutes(10);
					translocation = new Translocation(vehicle, 1, 1, timestamp, "FI", false);
					internalTranslocationDto = new InternalTranslocationDto(translocation);
				}

				translocationsToDivide.add(internalTranslocationDto);
		}
	}

	@Test
	public void testGetAdministratorDto(){
	}
}
