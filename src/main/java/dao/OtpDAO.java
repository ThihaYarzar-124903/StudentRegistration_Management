package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import models.ForgetBean;

import models.OTPbean;
import models.otpConfirmBean;

public class OtpDAO {
	
	public static Connection con=null;
	static {
		con=MyConnection.getConnection();
	}
	
	
	public int insertOTP(OTPbean otpBean) {
        int result = 0;
        String sql = "INSERT INTO otp (otp, otpCreated, otpExpired, email) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, otpBean.getOtp());
            ps.setTimestamp(2, otpBean.getOtpCreate());
            ps.setTimestamp(3, otpBean.getOtpExpired());
            ps.setString(4, otpBean.getEmail());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public ForgetBean selectEmail(ForgetBean dto) {
		ForgetBean res=new ForgetBean();
		String sql= "select * from user where email=?";
		try {
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, dto.getEmail());
			
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				
				res.setEmail(rs.getString("email"));
			
			
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
    
    
    public otpConfirmBean selectOTP(otpConfirmBean dto) {
    	otpConfirmBean res=new otpConfirmBean();
		String sql= "select * from otp where otp=?";
		try {
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setInt(1, dto.getOtp());
			
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				
				res.setOtp(rs.getInt("otp"));
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
    
 // Method to delete OTP by email
    public int deleteOTPByEmail(String email) {
        int result = 0;
        String sql = "DELETE FROM otp WHERE email = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    public void deleteExpiredOTPs(LocalDateTime currentDateTime) {
        String sql = "DELETE FROM otp WHERE otpExpired <= ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(currentDateTime));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }
    
    public boolean updatePassword(String email, String newPassword) {
        boolean success = false;
        String sql = "UPDATE user SET password = ? WHERE email = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, email);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                // Password updated successfully
                success = true;
            } else {
                // No rows affected, password update failed
                System.err.println("No rows affected, password update failed for email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Password update failed due to SQL exception: " + e.getMessage());
        }
        return success;
    }

    
    
   


}
