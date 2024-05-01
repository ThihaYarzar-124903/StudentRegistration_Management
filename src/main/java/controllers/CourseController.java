package controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dao.CourseDAO;
import dao.CourseMapper;
import dto.CourseDTO;
import dto.UserDTO;
import generator.IdGenerator;
import models.CourseBean;

@Controller
@RequestMapping("/courses")
public class CourseController {
	@Autowired
	CourseDAO courseDAO;
	@Autowired
	CourseMapper courseMapper;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String displayCourse(HttpSession session, ModelMap m) {
		UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
		if (loggedInUser == null || loggedInUser.getRole_id() != 1) {
			return "redirect:/";
		}

		List<CourseDTO> dtos = courseDAO.getAllCourses();
		List<CourseBean> courses = courseMapper.mapToListBean(dtos);

		m.addAttribute("courses", courses);
		return "displaycourse";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addCourse(ModelMap model) {
		CourseBean bean = new CourseBean();
		bean.setCourse_code(IdGenerator.generateCourseCode(courseDAO.getLastCourseCode()));

		return new ModelAndView("addcourse", "course", bean);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addCourse(@ModelAttribute("course") @Validated CourseBean course, BindingResult bResult,
			ModelMap model) {
		if (bResult.hasErrors()) {
			return "addcourse";
		}
		
		// Check if the course name already exists
	    CourseDTO req = new CourseDTO();
	    req.setName(course.getName());
	    CourseDTO existingCourse = courseDAO.selectNameCourse(req);
	    if (existingCourse != null) {
	        model.addAttribute("error", "Course name already exists!!");
	        return "addcourse"; // Return to the form with an error message
	    }

		CourseDTO dto = courseMapper.mapToRequestDTO(course);
		int rs = courseDAO.addCourse(dto);
		if (rs == 0) {
			model.addAttribute("error", "Insert Fail(SQL Error)");
			return "addcourse";
		}

		model.addAttribute("result", rs);
		return "addcourse";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editCourse(@PathVariable int id, ModelMap model) {
		CourseDTO dto = courseDAO.getCourseById(id);
		CourseBean updatedCourse = courseMapper.mapToBean(dto);
		return new ModelAndView("editcourse", "course", updatedCourse);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editCourse(@ModelAttribute("course") @Validated CourseBean course, BindingResult bResult,
			ModelMap model) {
		if (bResult.hasErrors()) {
			return "editcourse";
		}

		CourseDTO dto = courseMapper.mapToRequestDTO(course);
		int rs = courseDAO.editCourse(dto);
		if (rs == 0) {
			model.addAttribute("error", "Update Fail(SQL Error)");
			return "editcourse";
		}

		model.addAttribute("result", rs);
		return "editcourse";
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	public String deleteCourse(@PathVariable int id,ModelMap model) {
						
		int result = courseDAO.deleteCourse(id);
		if(result == 0) {
			
			model.addAttribute("error","Delete Fail(SQL Error)");
			return "displaycourse"; 
			
		}
		
		return "redirect:/courses/";			
	}
}
