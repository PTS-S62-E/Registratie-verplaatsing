import dao.TranslocationDaoImpl;
import dto.AdministrationDto;
import entities.Translocation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
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

	private static List<Translocation> translocationsToDivide;

	@Before
	public void setupBeforeMethod() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeClass
	public static void setup() {
		translocationsToDivide = new ArrayList<>();
		LocalDateTime timestamp = LocalDateTime.now();

		for (int i = 0; i < 10; i++) {

				Translocation translocation;

				if ( i == 5) {
					timestamp = timestamp.plusMinutes(25);
					translocation = new Translocation(null, 1, 1, timestamp, "FI");
				}
				else{
					timestamp = timestamp.plusMinutes(10);
					translocation = new Translocation(null, 1, 1, timestamp, "FI");
				}

				translocationsToDivide.add(translocation);
		}
	}

	@Test
	public void testGetAdministratorDto(){
		//Check if works with values
		when(translocationService.getTranslocations(1, null, null)).thenReturn(translocationsToDivide);
		AdministrationDto administrationDto = translocationService.getAdministrationDto(1, null, null);
		Assert.assertEquals(administrationDto.getJourneys().size(),2);

		//Check if works when translocationsToDivide is empty
		when(translocationService.getTranslocations(2, null, null)).thenReturn(new ArrayList<>());
		administrationDto = translocationService.getAdministrationDto(2, null, null);
		Assert.assertEquals(administrationDto.getJourneys().size(),0);
	}
}
