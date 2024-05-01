package controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import dao.StudentDAO;
import dao.StudentMapper;
import dto.StudentDTO;
import dto.UserDTO;
import generator.IdGenerator;
import models.CourseBean;
import models.StudentBean;

@Controller
@RequestMapping("/students")
public class StudentController {
	@Autowired
	StudentDAO studentDAO;
	@Autowired
	CourseDAO courseDAO;
	@Autowired
	StudentMapper studentMapper;
	@Autowired
	CourseMapper courseMapper;
	
	@ModelAttribute("coursesOptionList")
	List<CourseBean> getAllCourses() {
		List<CourseBean> courses = courseMapper.mapToListBean(courseDAO.getAllCourses());	
		return courses;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView displayStudents(HttpSession session, ModelMap m) throws UnsupportedEncodingException {
        
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            
            return new ModelAndView("redirect:/");
        }

        List<StudentDTO> dtos = studentDAO.getAllStudents();
        List<StudentBean> students = studentMapper.mapToListBean(dtos);
        
        m.addAttribute("students", students);

        return new ModelAndView("displaystudent");
    }
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ModelAndView addStudent(ModelMap model) throws UnsupportedEncodingException {
		
		StudentBean bean = new StudentBean();
		bean.setStudent_code(IdGenerator.generateStudentCode(studentDAO.getLastStudentCode()));
				
		return new ModelAndView("addstudent","student",bean);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addStudent(@ModelAttribute("student") @Validated StudentBean student,BindingResult bResult,ModelMap model) throws SerialException, SQLException, IOException {

		if(bResult.hasErrors()) {
			return "addstudent";
		}
		
		StudentDTO req = new StudentDTO();
		req.setPhone(student.getPhone());
		StudentDTO existingPhone = studentDAO.selectStudentByPhone(req);
		if (existingPhone != null) {
			model.addAttribute("errs", "Phone already exists!!");
			return "addstudent"; // Return to the form with an error message
		}
		
		student.setImage_blob(new SerialBlob(student.getMultipart().getBytes()));
		
		StudentDTO dto = studentMapper.mapToDTO(student);
		
		
		int rs = studentDAO.addStudent(dto);
		if(rs==0) {
			
			model.addAttribute("error","Insert Fail(SQL Error)");
			return "addstudent"; 
		}
		
		model.addAttribute("result",rs);
		return "addstudent";
	}
	
	
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView editStudent(@PathVariable int id,ModelMap model) throws UnsupportedEncodingException {
		
		StudentDTO dto = studentDAO.getStudentWithLazyById(id);
		StudentBean updatedStudent = studentMapper.mapToBean(dto);
		
		return new ModelAndView("editstudent","student",updatedStudent);
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String editStudent(@ModelAttribute("student") @Validated StudentBean student,BindingResult bResult,ModelMap model) throws SerialException, SQLException, IOException {
		if(bResult.hasErrors()) {
			
			StudentDTO dto = studentDAO.getStudentWithLazyById(student.getId());
			
			@SuppressWarnings("unused")
			StudentBean updatedStudent = studentMapper.mapToBean(dto);
			
			return "editstudent";
			
		}
		
		student.setImage_blob(new SerialBlob(student.getMultipart().getBytes()));
		
		StudentDTO dto = studentMapper.mapToDTO(student);		
		int rs = studentDAO.editStudent(dto);
		if(rs == 0) {
			model.addAttribute("error","Update Fail(SQL Error)");
			return "editstudent"; 
		}
		
		model.addAttribute("result",rs);
		return "editstudent";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        boolean success = studentDAO.updateDelete(id, 1); // Set delete flag to 1
        if(success) {
            return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete student", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@RequestMapping(value="/callback/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<String> callBackStudent(@PathVariable int id) {
        boolean success = studentDAO.updateDelete(id, 0); // Set delete flag to 0
        if(success) {
            return new ResponseEntity<>("Student call back successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to call back student", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	
	@RequestMapping(value = "/oldstudent", method = RequestMethod.GET)
	public String viewOldStudent(ModelMap model) throws UnsupportedEncodingException{
		
		List<StudentDTO> dtos = studentDAO.getAllOldStudents();
        List<StudentBean> students = studentMapper.mapToListBean(dtos);
        
        model.addAttribute("students", students);

		return "oldstudentlist";

	}
	
}
