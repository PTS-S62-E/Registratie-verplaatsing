package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeParser {
	//TODO: make date agreements
	public static LocalDateTime stringToLocalDateTime(String date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String formattedDate = date;

		if (formattedDate.contains("Z")) {
			formattedDate = formattedDate.replace("Z", "");
		}

		return LocalDateTime.parse(formattedDate, formatter);
	}

	public static String localDateTimeToString(LocalDateTime timestamp){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return timestamp.format(formatter);
	}
}
