package com.blackchicktech.healthdiet.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@Component
public class CustomDateDeserializer extends JsonDeserializer<Date> {

	private FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd");

	@Override
	public Date deserialize(JsonParser paramJsonParser,
							DeserializationContext paramDeserializationContext)
			throws IOException {
		String str = paramJsonParser.getText().trim();
		try {
			return dateFormat.parse(str);
		} catch (ParseException e) {
			// Handle exception here
		}
		return paramDeserializationContext.parseDate(str);
	}
}

