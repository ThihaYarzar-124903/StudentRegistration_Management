package controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dao.CourseDAO;
import dao.StudentDAO;
import dao.UserDAO;
import dto.UserDTO;
import models.UserBean;
@Controller
public class LoginController {
	
	private final CourseDAO courseDAO;

    private final StudentDAO studentDAO;

    public LoginController(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
        this.studentDAO = new StudentDAO();
    }
	
	@GetMapping("/")
    public ModelAndView displayLogin() {
        return new ModelAndView("login", "user", new UserBean());
    }
	
    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserBean user, HttpSession session, ModelMap model) {
    	
    	if (user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
            
            model.addAttribute("error", "Email and password are required!!!");
            model.addAttribute("errorMessageColor", "red");
            
            return "login"; 
        }
    	
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        UserDTO loggedInUser = UserDAO.getUserByEmail(dto.getEmail());

        if (loggedInUser != null && loggedInUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("loggedInUser", loggedInUser);
            if (loggedInUser.getRole_id() == 1) {
                
                return "redirect:/admin";
            } else if (loggedInUser.getRole_id() == 2) {
               
                if (loggedInUser.isIs_disabled()) {
                   
                    model.addAttribute("error", "This account is disabled.");
                    return "login";
                    
                } else {
                    return "/user";
                }
            }
        } else {
            // Authentication failed
            if (loggedInUser == null) {
                model.addAttribute("error", "Invalid email.");
            } else {
                model.addAttribute("error", "Invalid password.");
            }
            model.addAttribute("errorMessageColor", "red"); // Add a color attribute for error message
            return "login"; 
        }
       
        model.addAttribute("error", "Invalid email or password.");
        model.addAttribute("errorMessageColor", "red"); // Add a color attribute for error message
        return "login";
    }
    
    @GetMapping("/admin")
    public String owner(HttpSession session, ModelMap model) {
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");

        if (loggedInUser != null && loggedInUser.getRole_id() == 1) {
        	
            model.addAttribute("courseCount", courseDAO.getCoursesCount());
            model.addAttribute("studentCount", studentDAO.getStudentsCount());
            
            return "admin";
        } else {
            return "redirect:/";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirect) {
        session.invalidate();
        redirect.addFlashAttribute("success", "Logout Successfully!");
        return "redirect:/";
    }
    
    @GetMapping("/user")
    public String userPage() {
        return "user";
    }
     
}
