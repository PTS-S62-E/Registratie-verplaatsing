package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeParser {
	//TODO: make date agreements
	public static LocalDateTime stringToLocalDateTime(String date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String formatterDate = date.replace("Z", "");
		return LocalDateTime.parse(formatterDate);
	}

	public static String localDateTimeToString(LocalDateTime timestamp){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return timestamp.format(formatter);
	}
}
