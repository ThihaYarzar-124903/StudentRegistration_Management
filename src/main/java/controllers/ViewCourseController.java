package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.CourseDAO;
import dto.CourseDTO;

@Controller
@RequestMapping("/course")
public class ViewCourseController {
	@Autowired
	private CourseDAO courseDAO;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String viewCourse(ModelMap model) {
		List<CourseDTO> dtos = courseDAO.getAllCourses();
		model.addAttribute("courses", dtos);

		return "viewcourse";

	}
}
