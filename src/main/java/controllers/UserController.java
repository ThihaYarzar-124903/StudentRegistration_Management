package controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dao.UserDAO;
import dao.UserMapper;
import dto.UserDTO;
import generator.IdGenerator;
import models.UserBean;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping("/")
    public String displayUser(ModelMap m, HttpSession session) throws UnsupportedEncodingException {
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole_id() != 1) {
            
            return "redirect:/login";
        }

        @SuppressWarnings("static-access")
		List<UserDTO> dtos = userDAO.getAllUser();
        List<UserBean> user = userMapper.mapToListBean(dtos);
        m.addAttribute("users", user);
        return "displayuser";
    }
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ModelAndView addUser(ModelMap model) {
		UserBean bean = new UserBean();
	    bean.setUser_code(IdGenerator.generateUserCode(userDAO.getLastUserCode()));
	        
	    return new ModelAndView("adduser", "user", bean);
	}

	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addUser(@ModelAttribute("user") @Validated UserBean user,BindingResult bResult, ModelMap model) throws SerialException, SQLException, IOException {
		if(bResult.hasErrors()) {
			return "adduser";
		}
		
		UserDTO req = new UserDTO();
		req.setEmail(user.getEmail());
		UserDTO existingEmail = userDAO.selectUserByEmail(req);
		if (existingEmail != null) {
			model.addAttribute("errors", "Email already exists!!");
			return "adduser"; // Return to the form with an error message
		}
		
		UserDTO res = new UserDTO();
		res.setNrc(user.getNrc());
		UserDTO existingNrc = userDAO.selectUserByNrc(res);
		if (existingNrc != null) {
			model.addAttribute("err", "Nrc already exists!!");
			return "adduser"; // Return to the form with an error message
		}
		
		UserDTO re = new UserDTO();
		re.setPhone(user.getPhone());
		UserDTO existingPhone = userDAO.selectUserByPhone(re);
		if (existingPhone != null) {
			model.addAttribute("errs", "Phone already exists!!");
			return "adduser"; // Return to the form with an error message
		}
		
		user.setIs_disabled(true);
		
		user.setImage_blob(new SerialBlob(user.getMultipart().getBytes()));
		
		UserDTO dto = userMapper.mapToRequestDTO(user);
		
		int rs=userDAO.addUser(dto);
		if(rs==0) {
			model.addAttribute("error","Insert Fail(SQL Error)");
			return "adduser"; 
		}
		
		model.addAttribute("result",rs);
		return "adduser";
	}
	
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView editUser(@PathVariable int id,ModelMap model) {
		UserDTO dto=userDAO.getUserById(id);
		
		UserBean updatedUser=userMapper.mapToBean(dto);
		
		return new ModelAndView("edituser","user",updatedUser);
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String editUser(@ModelAttribute("user") @Validated UserBean user,BindingResult bResult,ModelMap model) throws SerialException, SQLException, IOException{
		
		if(bResult.hasErrors()) {
			return "edituser";
			
		}
		
		user.setImage_blob(new SerialBlob(user.getMultipart().getBytes()));
		
		UserDTO dto=userMapper.mapToRequestDTO(user);
		
		int rs=userDAO.editUser(dto);
		
		if(rs==0) {
			model.addAttribute("error","Update Fail(SQL Error)");
			return "edituser"; 
		}
		
		model.addAttribute("result",rs);
		return "edituser";
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	public String deleteUser(@PathVariable int id, ModelMap model, HttpSession session) {
	    UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
	    if (loggedInUser != null && loggedInUser.getId() == id) {
	        model.addAttribute("errors", "Cannot delete the logged-in user account.");
	        return "displayuser";
	    }
	    
	    int result = userDAO.deleteUser(id);
	    if (result == 0) {
	        model.addAttribute("error", "Delete Fail(SQL Error)");
	        return "displayuser"; 
	    }
	    
	    return "redirect:/users/";
	}
	
	@RequestMapping(value="/disable/{id}",method=RequestMethod.GET)
	public String disableUser(@PathVariable int id,ModelMap model) {
		int result=userDAO.disableUser(id);
		if(result==0) {
			model.addAttribute("error","Disable Fail(SQL Error)");
		}		
		
		return "redirect:/users/";		
	}
	
	@RequestMapping(value="/enable/{id}",method=RequestMethod.GET)
	public String enableUser(@PathVariable int id,ModelMap model) {
		int result=userDAO.enableUser(id);
		if(result==0) {
			model.addAttribute("error","Enable Fail(SQL Error)");
		}			
		
		return "redirect:/users/";		
	}
}
