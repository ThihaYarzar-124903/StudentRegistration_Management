package controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dao.OtpDAO;
import models.ForgetBean;
import models.NPassBean;
import models.OTPbean;
import models.otpConfirmBean;

@Controller
public class OtpController {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private  OtpDAO otpDAO;
	

	
    public OtpController(OtpDAO otpDAO) {
        this.otpDAO = otpDAO;
    }
    
    @Scheduled(fixedRate = 60000) // Run every 1 minutes (300,000 milliseconds)
    public void cleanupExpiredOTPs() {
    	
        LocalDateTime currentDateTime = LocalDateTime.now();
        otpDAO.deleteExpiredOTPs(currentDateTime);
    }

	
	//forgot password otp start
	
	
	//Generate OTP from Math Library
	private String generateOTP() {
        // Generate a random 6-digit OTP
        int otp = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(otp);
    }

    // Method to send OTP via email
    private void sendOTPEmail(String userEmail, String otp) {
    	
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);
        mailSender.send(message);
    }
    

    
    
    @RequestMapping(value = "/emailcheck", method = RequestMethod.GET)
	public ModelAndView fpass() {

		return new ModelAndView("forget", "fbean", new ForgetBean());
	}
	
	@RequestMapping(value = "/emailcheckprocess", method = RequestMethod.POST)
	public String emailCheck(@ModelAttribute("fbean") @Validated ForgetBean fbean, BindingResult bs, ModelMap model,HttpSession session) {
		if (bs.hasErrors()) {
            System.out.print("Error Code: 1111");
            return "forget";
        }
        
        ForgetBean req = new ForgetBean();
        req.setEmail(fbean.getEmail());
        
        OtpDAO otpDAO = new OtpDAO();
        ForgetBean res = otpDAO.selectEmail(req);
       
        session.setAttribute("otpemail", res.getEmail());
        //System.out.print("OTP Email---------"+ session.getAttribute("otpemail"));
        if (req.getEmail().equals(res.getEmail())) {
            // Generate OTP
            String otp = generateOTP();
            
            // Save OTP to database
            OTPbean otpBean = new OTPbean();
            otpBean.setOtp(Integer.parseInt(otp));
            otpBean.setOtpCreate(Timestamp.valueOf(LocalDateTime.now()));
            otpBean.setOtpExpired(Timestamp.valueOf(LocalDateTime.now().plusMinutes(1))); // OTP expires after 1 minutes
            otpBean.setEmail(req.getEmail());
            otpDAO.insertOTP(otpBean);
            
            // Send OTP via email
            sendOTPEmail(req.getEmail(), otp);
            
            return "redirect:/otp/"; // Redirect to OTP verification page
        } else {
            model.addAttribute("error", "Email Wrong!!!");
            return "forget";
        }
	}
	
	
    @RequestMapping(value = "/otp", method = RequestMethod.GET)
	public ModelAndView otp() {

		return new ModelAndView("otp", "obean", new otpConfirmBean());
	}
    
    @RequestMapping(value = "/otpprogress", method = RequestMethod.POST)
	public String otpProcess(@ModelAttribute("obean") @Validated otpConfirmBean obean, BindingResult bs, ModelMap model) {
		if (bs.hasErrors()) {
			//System.out.print("Error Code: 1111");
			return "otp";
		}
		
		otpConfirmBean req = new otpConfirmBean();
		req.setOtp(obean.getOtp());
			
			OtpDAO dao = new OtpDAO();
			otpConfirmBean res = dao.selectOTP(req);
			if (req.getOtp() == (res.getOtp())) {
				
				return "redirect:/newpass/";
			} else {
				model.addAttribute("error", "LoginFailed");
				return "otp";
			
		
		}
	}
    
    @RequestMapping(value = "/newpass", method = RequestMethod.GET)
 	public ModelAndView newpass() {

 		return new ModelAndView("newpass", "nbean", new NPassBean());
 	}
    
    @RequestMapping(value = "/newpassprogress", method = RequestMethod.POST)
    public String newPassProgress(@ModelAttribute("nbean") @Validated NPassBean nbean, BindingResult bs, ModelMap model,HttpSession session) {
        if (bs.hasErrors()) {
            System.out.print("Error Code: 1111");
            return "newpass";
        }
        
        OtpDAO otpDAO = new OtpDAO(); // Instantiate your UserDAO class
        
        // Update password in the user table
        boolean isPasswordUpdated = otpDAO.updatePassword((String) session.getAttribute("otpemail"), nbean.getPassword());
        
        if (isPasswordUpdated) {
            // Password updated successfully
            return "redirect:/"; // Redirect to login page
        } else {
            // Password update failed
            model.addAttribute("error", "Password update failed. Please try again.");
            return "newpass";
        }
    }

    
   

	
	
}
