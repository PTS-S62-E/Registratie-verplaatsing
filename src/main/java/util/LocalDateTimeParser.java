package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class LocalDateTimeParser {
	//TODO: make date agreements
	public static LocalDateTime stringToLocalDateTime(String date){
		String formattedDate = date;

		formattedDate = formattedDate.replace("Z", "");

		return LocalDateTime.parse(formattedDate);
	}

	public static String localDateTimeToString(LocalDateTime timestamp){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		return timestamp.format(formatter);
	}
}
