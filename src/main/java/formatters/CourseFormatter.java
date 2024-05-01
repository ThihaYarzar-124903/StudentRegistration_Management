package formatters;


import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import models.CourseBean;

public class CourseFormatter implements Formatter<CourseBean> {
	
	@Override
	public String print(CourseBean object, Locale locale) {
		return null;
	}

	@Override
	public CourseBean parse(String text, Locale locale) throws ParseException {
		
		CourseBean course = new CourseBean();
		int id = Integer.parseInt(text);
		course.setId(id);
		//course.setId(text);
		return course;
	}
}
