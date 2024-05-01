package dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.util.codec.binary.Base64;
import com.mysql.cj.jdbc.Blob;
import dto.UserDTO;


public class UserDAO {
	
	public static Connection con=null;
	static {
		con=MyConnection.getConnection();
	}
	
	//Login select email 
	public static UserDTO getUserByEmail(String email) {
		UserDTO user = new UserDTO();
        String sql = "SELECT * FROM user WHERE email=?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setRole_id(rs.getInt("role_id"));
                user.setUser_code(rs.getString("user_code"));
                user.setDob(rs.getString("dob"));
                user.setGender(rs.getString("gender"));
                user.setPhone(rs.getString("phone"));
                user.setNrc(rs.getString("nrc"));
                // Add more fields as needed
                user.setPassword(rs.getString("password"));
                user.setIs_disabled(rs.getBoolean("is_disabled"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
	}
	
	// insert
	public int addUser(UserDTO userDTO) {
		int result = 0;
		String sql = "INSERT INTO user(name,email,user_code,role_id,dob,gender,phone,nrc,image,password) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userDTO.getName());
			ps.setString(2, userDTO.getEmail());
			ps.setString(3, userDTO.getUser_code());
			ps.setInt(4, 2);
			ps.setString(5, userDTO.getDob());
			ps.setString(6, userDTO.getGender());
			ps.setString(7, userDTO.getPhone());
			ps.setString(8, userDTO.getNrc());
			ps.setBlob(9, userDTO.getImage_blob());
			ps.setString(10, userDTO.getPassword());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Insert error: " + e);
		}

		return result;
	}

	// update
	public int editUser(UserDTO userDTO) {
		int result = 0;

		String sql = "UPDATE user SET name=?,email=?, user_code=?, dob=?, gender=?, phone=?, nrc=?, image=?, password=?"
				+ " WHERE id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userDTO.getName());
			ps.setString(2, userDTO.getEmail());
			ps.setString(3, userDTO.getUser_code());
			ps.setString(4, userDTO.getDob());
			ps.setString(5, userDTO.getGender());
			ps.setString(6, userDTO.getPhone());
			ps.setString(7, userDTO.getNrc());
			ps.setBlob(8, userDTO.getImage_blob());
			ps.setString(9, userDTO.getPassword());

			ps.setInt(10, userDTO.getId());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Insert error: " + e);
		}
		return result;
	}
	
	// delete
	public int deleteUser(int id) {
		int result = 0;
		String sql = "DELETE FROM user WHERE id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Delete error: " + e);
		}

		return result;
	}
	
	// selectById
	public UserDTO getUserById(int id) {
		UserDTO user = new UserDTO();
		String sql = "SELECT * FROM  user WHERE id=?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setRole_id(rs.getInt("role_id"));
				user.setUser_code(rs.getString("user_code"));
				user.setDob(rs.getString("dob"));
				user.setGender(rs.getString("gender"));
				user.setPhone(rs.getString("phone"));
				user.setNrc(rs.getString("nrc"));
				user.setPassword(rs.getString("password"));
				user.setImage(rs.getString("image"));

			}

		} catch (SQLException e) {
			System.out.println("select by id error" + e);
		}

		return user;
	}
	
	// selectAll
	public static List<UserDTO> getAllUser() throws UnsupportedEncodingException {
		List<UserDTO> users = new ArrayList<UserDTO>();
		String sql = "SELECT * FROM user";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				UserDTO user = new UserDTO();
				
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setRole_id(rs.getInt("role_id"));
				user.setUser_code(rs.getString("user_code"));
				user.setDob(rs.getString("dob"));
				user.setGender(rs.getString("gender"));
				user.setPhone(rs.getString("phone"));
				user.setNrc(rs.getString("nrc"));

				Blob image_Blob = (Blob) rs.getBlob("image");
				byte[] bytes = image_Blob.getBytes(1, (int) image_Blob.length());
				byte[] encodeBase64 = Base64.encodeBase64(bytes);
				user.setImage(new String(encodeBase64, "UTF-8"));

				user.setPassword(rs.getString("password"));
				user.setIs_disabled(rs.getBoolean("is_disabled"));

				users.add(user);
			}
		} catch (SQLException e) {
			System.out.println("select all error: " + e);
		}

		return users;
	}
	
	public int disableUser(int userId) {
		int result = 0;
		String sql = "UPDATE user SET is_disabled=?" + " WHERE id=?";
		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setBoolean(1, false);
			ps.setInt(2, userId);

			result = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Update error: " + e);
		}

		return result;

	}

	public int enableUser(int userId) {
		int result = 0;
		String sql = "UPDATE user SET is_disabled=?" + " WHERE id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setBoolean(1, true);
			ps.setInt(2, userId);

			result = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Update error: " + e);
		}

		return result;

	}
	
    public int getLastUserCode() {
        int lastUserCode = 0;
        String sql = "SELECT MAX(id) as userCode FROM user";
           
         try {
             
            PreparedStatement ps=con.prepareStatement(sql);      
            ResultSet rs=ps.executeQuery();  
             
             while(rs.next()) {
            	 lastUserCode = rs.getInt("userCode"); 
             } 
             
         }catch(SQLException e) {
            System.out.println("select Last Id error: "+e);
         }
           return lastUserCode;
     }
    
    public UserDTO selectUserByEmail(UserDTO email) {
    	UserDTO res = null;
        String sql = "SELECT * FROM user WHERE email=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email.getEmail());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                res = new UserDTO();
                
                res.setId(rs.getInt("id"));
                res.setName(rs.getString("name"));
                res.setEmail(rs.getString("email"));
				res.setRole_id(rs.getInt("role_id"));
				res.setUser_code(rs.getString("user_code"));
				res.setDob(rs.getString("dob"));
				res.setGender(rs.getString("gender"));
				res.setPhone(rs.getString("phone"));
				res.setNrc(rs.getString("nrc"));
				res.setImage(rs.getString("image"));
            }
        } catch (SQLException e) {
            // Handle or log the exception appropriately
            e.printStackTrace();
        }
        return res;
    }

    // Method to select a course by NRC
    public UserDTO selectUserByNrc(UserDTO nrc) {
    	UserDTO res = null;
        String sql = "SELECT * FROM user WHERE nrc=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nrc.getNrc());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                res = new UserDTO();
                
                res.setId(rs.getInt("id"));
                res.setName(rs.getString("name"));
                res.setEmail(rs.getString("email"));
				res.setRole_id(rs.getInt("role_id"));
				res.setUser_code(rs.getString("user_code"));
				res.setDob(rs.getString("dob"));
				res.setGender(rs.getString("gender"));
				res.setPhone(rs.getString("phone"));
				res.setNrc(rs.getString("nrc"));
				res.setImage(rs.getString("image"));
            }
        } catch (SQLException e) {
            // Handle or log the exception appropriately
            e.printStackTrace();
        }
        return res;
    }
    
    public UserDTO selectUserByPhone(UserDTO phone) {
    	UserDTO res = null;
        String sql = "SELECT * FROM user WHERE phone=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, phone.getPhone());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                res = new UserDTO();
                
                res.setId(rs.getInt("id"));
                res.setName(rs.getString("name"));
                res.setEmail(rs.getString("email"));
				res.setRole_id(rs.getInt("role_id"));
				res.setUser_code(rs.getString("user_code"));
				res.setDob(rs.getString("dob"));
				res.setGender(rs.getString("gender"));
				res.setPhone(rs.getString("phone"));
				res.setNrc(rs.getString("nrc"));
				res.setImage(rs.getString("image"));
            }
        } catch (SQLException e) {
            // Handle or log the exception appropriately
            e.printStackTrace();
        }
        return res;
    }

}
