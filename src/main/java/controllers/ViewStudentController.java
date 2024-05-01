package controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.StudentDAO;
import dao.StudentMapper;
import dto.StudentDTO;
import models.StudentBean;

@Controller
@RequestMapping("/student")
public class ViewStudentController {
	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private StudentMapper studentMapper;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String viewStudent(ModelMap model) throws UnsupportedEncodingException{
		
		List<StudentDTO> dtos = studentDAO.getAllStudents();
        List<StudentBean> students = studentMapper.mapToListBean(dtos);
        
        model.addAttribute("students", students);

		return "viewstudent";

	}
}
