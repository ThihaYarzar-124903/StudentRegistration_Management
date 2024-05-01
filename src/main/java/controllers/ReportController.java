package controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.ReportDAO;
import dao.StudentMapper;
import dto.StudentDTO;
import dto.UserDTO;
import models.StudentBean;

@Controller
public class ReportController {
	@Autowired
    private ReportDAO reportDAO;
	
	@Autowired
    private StudentMapper studentMapper;
	
	
	
	@RequestMapping(value="/studentsreports", method = RequestMethod.GET)
    public String showDailyStudentsReport(Model model, HttpSession session) throws UnsupportedEncodingException{
        UserDTO user = (UserDTO) session.getAttribute("loggedInUser");
        
        if (user == null) {            
            return "redirect:/login";
        }

        int userId = user.getId();
        
        List<StudentDTO> dtos = reportDAO.getDetailsStudents(userId);
        List<StudentBean> studentsReport = studentMapper.mapToListBean(dtos);
               
        model.addAttribute("studentsReport", studentsReport);

        return "dailystudentsreports";
    }
}
