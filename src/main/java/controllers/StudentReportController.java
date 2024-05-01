package controllers;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.StudentMapper;
import dao.StudentReportDAO;
import dto.StudentDTO;
import models.StudentBean;

@Controller
public class StudentReportController {
	@Autowired
    private StudentReportDAO studentReportDAO;
	
	@Autowired
    private StudentMapper studentMapper;
	
	@GetMapping("/details")
    public String showStudentsForm(HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }
        return "reportsdetailsform";
    }
	
	@PostMapping("/detail")
    public String getSalesByDateAndAccount(@RequestParam String fromDate, @RequestParam String toDate, @RequestParam int userId, Model model, HttpSession session) throws SQLException, UnsupportedEncodingException {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/"; // Redirect to login page if user is not logged in
        }
        
        List<StudentDTO> dtos = studentReportDAO.getStudentsByDateRangeAndUserId(fromDate, toDate, userId);
        List<StudentBean> studentsList = studentMapper.mapToListBean(dtos);
        
        model.addAttribute("studentsList", studentsList);
        return "studentsresult";
    }
}
