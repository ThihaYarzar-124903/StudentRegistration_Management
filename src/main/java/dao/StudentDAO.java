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
import dto.CourseDTO;
import dto.StudentDTO;


public class StudentDAO {
	
	public static Connection con=null;
	static {
		con=MyConnection.getConnection();
	}
	
	// insert
	public int addStudent(StudentDTO studentDTO) {
		int result = 0;
		String sql = "INSERT INTO student(name,student_code,dob,gender,date,education,phone,photo,user_id, `delete`) VALUES(?,?,?,?,?,?,?,?,?,?)";

		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, studentDTO.getName());
			ps.setString(2, studentDTO.getStudent_code());
			ps.setString(3, studentDTO.getDob());
			ps.setString(4, studentDTO.getGender());
			ps.setString(5, studentDTO.getDate());
			ps.setString(6, studentDTO.getEducation());
			ps.setString(7, studentDTO.getPhone());
			ps.setBlob(8, studentDTO.getImage_blob());
			ps.setInt(9, studentDTO.getUser_id());
			ps.setInt(10, studentDTO.getDelete());
			
			result = ps.executeUpdate();

			if (result != 0) {
				for (CourseDTO course : studentDTO.getCourses()) {
					sql = "INSERT INTO course_has_student(student_id,course_id) VALUES(last_insert_id(),?)";
					ps = con.prepareStatement(sql);

					System.out.println("course Id"+course.getId());
					//ps.setInt(1, studentDTO.getId());
					ps.setInt(1, course.getId());
					result = ps.executeUpdate();
				}
			}
		} catch (SQLException e) {
			System.out.println("Insert error: " + e);
		}
		return result;
	}

	


	// update
	public int editStudent(StudentDTO studentDTO) {
		int result = 0;

		String sql = "UPDATE student SET name=?,student_code=?,dob=?,gender=?,date=?,education=?,phone=?,photo=? WHERE id=?";
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, studentDTO.getName());
			ps.setString(2, studentDTO.getStudent_code());
			ps.setString(3, studentDTO.getDob());
			ps.setString(4, studentDTO.getGender());
			ps.setString(5, studentDTO.getDate());
			ps.setString(6, studentDTO.getEducation());
			ps.setString(7, studentDTO.getPhone());
			ps.setBlob(8, studentDTO.getImage_blob());
			
			ps.setInt(9, studentDTO.getId());

			result = ps.executeUpdate();

			sql = "DELETE FROM course_has_student WHERE student_id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, studentDTO.getId());
			result = ps.executeUpdate();

			for (CourseDTO course : studentDTO.getCourses()) {
				sql = "INSERT INTO course_has_student(student_id,course_id) VALUES(?,?)";
				ps = con.prepareStatement(sql);
				
				ps.setInt(1, studentDTO.getId());
				ps.setInt(2, course.getId());
				result = ps.executeUpdate();

			}
		} catch (SQLException e) {
			System.out.println("Update error: " + e);
		}
		return result;
	}
	
	// selectById
	public StudentDTO getStudentById(int id) {
		StudentDTO student = new StudentDTO();
		String sql = "SELECT * FROM student WHERE id=?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				student.setId(rs.getInt("id"));
				student.setName(rs.getString("name"));
				student.setStudent_code(rs.getString("student_code"));
				student.setDob(rs.getString("dob"));
				student.setGender(rs.getString("gender"));
				student.setDate(rs.getString("date"));
				student.setEducation(rs.getString("education"));
				student.setPhone(rs.getString("phone"));
				student.setPhoto(rs.getString("photo"));

			}
		} catch (SQLException e) {
			System.out.println("select by code error" + e);
		}
		return student;
	}
	
	public StudentDTO getStudentWithLazyById(int id) throws UnsupportedEncodingException {
		StudentDTO student = new StudentDTO();
		String sql="SELECT * FROM student WHERE id=?";
		
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("name"));
				student.setStudent_code(rs.getString("student_code"));
				student.setDob(rs.getString("dob"));
				student.setGender(rs.getString("gender"));
				student.setDate(rs.getString("date"));
				student.setEducation(rs.getString("education"));
				student.setPhone(rs.getString("phone"));
				student.setPhoto(rs.getString("photo"));
				
//				Blob image_Blob = (Blob) rs.getBlob("photo");
//				byte[] bytes = image_Blob.getBytes(1, (int) image_Blob.length());
//				byte[] encodeBase64 = Base64.encodeBase64(bytes);
//				student.setPhoto(new String(encodeBase64, "UTF-8"));
				
				sql="SELECT c.* FROM course_has_student cs inner join course c on cs.course_id = c.id WHERE cs.student_id=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet join_rs=ps.executeQuery();
				
				List<CourseDTO> courses=new ArrayList<CourseDTO>();
				while(join_rs.next()) {
					CourseDTO course = new CourseDTO();
					
					course.setId(join_rs.getInt("id"));
					course.setName(join_rs.getString("name"));
					course.setCourse_code(join_rs.getString("course_code"));
					
					
					courses.add(course);				
				}				
				student.setCourses(courses);				
			}				
		}catch(SQLException e) {
			System.out.println("select by id error"+e);
		}
		return student;
	}
	
	// selectAll
	public List<StudentDTO> getAllStudents() throws UnsupportedEncodingException {
		List<StudentDTO> students = new ArrayList<StudentDTO>();
		String sql = "SELECT * FROM student WHERE `delete` = 0";

		CourseDAO courseDAO = new CourseDAO();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				StudentDTO student = new StudentDTO();
				
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("name"));
				student.setStudent_code(rs.getString("student_code"));
				student.setDob(rs.getString("dob"));
				student.setGender(rs.getString("gender"));
				student.setDate(rs.getString("date"));
				student.setEducation(rs.getString("education"));
				student.setPhone(rs.getString("phone"));
				
				Blob image_Blob = (Blob) rs.getBlob("photo");
				byte[] bytes = image_Blob.getBytes(1, (int) image_Blob.length());
				byte[] encodeBase64 = Base64.encodeBase64(bytes);
				student.setPhoto(new String(encodeBase64, "UTF-8"));

				student.setCourses(courseDAO.getCoursesByStudentId(rs.getInt("id")));

				students.add(student);
			}
		} catch (SQLException e) {
			System.out.println("select all error: " + e);
		}
		return students;
	}

	
	public int getLastStudentCode() {
        int lastStudentCode = 0;
        String sql = "SELECT MAX(id) as studentCode FROM student";
           
         try {
             
            PreparedStatement ps=con.prepareStatement(sql);      
            ResultSet rs=ps.executeQuery();  
             
             while(rs.next()) {
            	 lastStudentCode = rs.getInt("studentCode"); 
             } 
             
         }catch(SQLException e) {
            System.out.println("select Last Id error: "+e);
         }
           return lastStudentCode;
     }
	
	public Integer getStudentsCount() {
		try {
			Integer count = 0;
			PreparedStatement ps = con.prepareStatement("Select count(*) as student_count from student where `delete` = 0");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("student_count");
			}
			return count;
		} catch (SQLException e) {
			System.out.print("Error at getStudentsCount() " + e.getMessage());
		}
		return null;
	}
	
    public StudentDTO selectStudentByPhone(StudentDTO phone) {
    	StudentDTO res = null;
        String sql = "SELECT * FROM student WHERE phone=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, phone.getPhone());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                res = new StudentDTO();
                
                res.setId(rs.getInt("id"));
                res.setName(rs.getString("name"));
                res.setStudent_code(rs.getString("student_code"));
                res.setDob(rs.getString("dob"));
                res.setGender(rs.getString("gender"));
                res.setDate(rs.getString("date"));
                res.setEducation(rs.getString("education"));
                res.setPhone(rs.getString("phone"));
                res.setPhoto(rs.getString("photo"));
                
            }
        } catch (SQLException e) {
            // Handle or log the exception appropriately
            e.printStackTrace();
        }
        return res;
    }
    
    public boolean updateDelete(int id, int delete) {
        boolean success = false;
        String sql = "UPDATE student SET `delete` = ? WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, delete);
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                // Password updated successfully
                success = true;
            } else {
                // No rows affected, password update failed
                System.err.println("No rows affected, password update failed for student ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Update failed due to SQL exception: " + e.getMessage());
        }
        return success;
    }
    
    public List<StudentDTO> getAllOldStudents() throws UnsupportedEncodingException {
		List<StudentDTO> students = new ArrayList<StudentDTO>();
		String sql = "SELECT * FROM student WHERE `delete` = 1";

		CourseDAO courseDAO = new CourseDAO();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				StudentDTO student = new StudentDTO();
				
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("name"));
				student.setStudent_code(rs.getString("student_code"));
				student.setDob(rs.getString("dob"));
				student.setGender(rs.getString("gender"));
				student.setDate(rs.getString("date"));
				student.setEducation(rs.getString("education"));
				student.setPhone(rs.getString("phone"));
				
				Blob image_Blob = (Blob) rs.getBlob("photo");
				byte[] bytes = image_Blob.getBytes(1, (int) image_Blob.length());
				byte[] encodeBase64 = Base64.encodeBase64(bytes);
				student.setPhoto(new String(encodeBase64, "UTF-8"));

				student.setCourses(courseDAO.getCoursesByStudentId(rs.getInt("id")));

				students.add(student);
			}
		} catch (SQLException e) {
			System.out.println("select all error: " + e);
		}
		return students;
	}

}
