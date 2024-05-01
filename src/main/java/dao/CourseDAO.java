package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CourseDTO;

public class CourseDAO {
	public static Connection con = null;
	static {
		con = MyConnection.getConnection();
	}

	public int addCourse(CourseDTO courseDTO) {
		int result = 0;
		String sql = "INSERT INTO course(name,course_code) VALUES(?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, courseDTO.getName());
			ps.setString(2, courseDTO.getCourse_code());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Insert error: " + e);
		}

		return result;
	}

	public int editCourse(CourseDTO courseDTO) {
		int result = 0;

		String sql = "UPDATE course SET name=?,course_code=? WHERE id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, courseDTO.getName());
			ps.setString(2, courseDTO.getCourse_code());

			ps.setInt(3, courseDTO.getId());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Insert error: " + e);
		}

		return result;
	}

	public int deleteCourse(int id) {
		int result = 0;
		String sql = "DELETE FROM course WHERE id=?";
		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Delete error: " + e);
		}

		return result;
	}

	public CourseDTO getCourseById(int id) {
		CourseDTO course = new CourseDTO();
		String sql = "SELECT * FROM  course WHERE id=?";

		try {

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				course.setId(rs.getInt("id"));
				course.setName(rs.getString("name"));
				course.setCourse_code(rs.getString("course_code"));

			}

		} catch (SQLException e) {

			System.out.println("select by id error" + e);
		}

		return course;
	}

	// selectAll
	public List<CourseDTO> getAllCourses() {
		List<CourseDTO> courses = new ArrayList<CourseDTO>();
		String sql = "SELECT * FROM course";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				CourseDTO course = new CourseDTO();
				course.setId(rs.getInt("id"));
				course.setName(rs.getString("name"));
				course.setCourse_code(rs.getString("course_code"));

				courses.add(course);
			}
		} catch (SQLException e) {
			System.out.println("select all error: " + e);
		}
		return courses;
	}

//	public int getLastCourseId() {
//		int lastCourseId = 0;
//		String sql = "SELECT MAX(id) as courseId FROM course";
//
//		try {
//
//			PreparedStatement ps = con.prepareStatement(sql);
//			ResultSet rs = ps.executeQuery();
//
//			while (rs.next()) {
//				lastCourseId = rs.getInt("courseId");
//			}
//		} catch (SQLException e) {
//			System.out.println("select Last Id error: " + e);
//		}
//		return lastCourseId;
//	}

	public Integer getCoursesCount() {
		try {
			Integer count = 0;
			PreparedStatement ps = con.prepareStatement("Select count(*) as course_count from course");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("course_count");
			}
			return count;
		} catch (SQLException e) {
			System.out.print("Error at getCoursesCount() " + e.getMessage());
		}
		return null;
	}
	
	public int getLastCourseCode() {
        int lastCourseCode = 0;
        String sql = "SELECT MAX(id) as courseCode FROM course";
           
         try {
             
            PreparedStatement ps=con.prepareStatement(sql);      
            ResultSet rs=ps.executeQuery();  
             
             while(rs.next()) {
            	 lastCourseCode = rs.getInt("courseCode"); 
             } 
             
         }catch(SQLException e) {
            System.out.println("select Last Id error: "+e);
         }
           return lastCourseCode;
     }


//	public List<CourseDTO> getCoursesByWithoutStudent() {
//		
//		List<CourseDTO> courses = new ArrayList<CourseDTO>();
//		String sql="SELECT c.* FROM course c "
//				  + "WHERE c.id NOT IN (SELECT cs.course_id FROM course_has_student cs "
//				  + "JOIN student s ON cs.student_id = s.id WHERE NOT s.date < NOW());";
//		
//		try {
//			PreparedStatement ps=con.prepareStatement(sql);			
//			ResultSet rs=ps.executeQuery();			
//			while(rs.next()) {
//				
//				CourseDTO course = new CourseDTO();
//				
//				course.setId(rs.getInt("id"));
//				course.setName(rs.getString("name"));
//				course.setCourse_code(rs.getString("course_code"));
//				
//				courses.add(course);
//			}				
//		}catch(SQLException e) {
//			System.out.println("select all error: "+e);
//		}
//		return courses;
//	}
	
	public List<CourseDTO> getCoursesByStudentId(int student_id) {
		List<CourseDTO> courses = new ArrayList<CourseDTO>();			
			String sql = "SELECT c.* FROM course c INNER JOIN  course_has_student cs  ON  c.id = cs.course_id WHERE cs.student_id=?";
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, student_id);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					
					CourseDTO course = new CourseDTO();
					course.setId(rs.getInt("id"));
					course.setName(rs.getString("name"));
					course.setCourse_code(rs.getString("course_code"));
					
					
					courses.add(course);
				}
			} catch (SQLException e) {
				System.out.println("select product by id error" + e);
		}
			
		return courses;
	}
	
	public CourseDTO selectNameCourse(CourseDTO course) {
		CourseDTO res = null;
	    String sql = "SELECT * FROM course WHERE name=?";
	    try {
	    	
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, course.getName());
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            res = new CourseDTO();
	            res.setId(rs.getInt("id"));
	            res.setName(rs.getString("name"));
	            res.setCourse_code(rs.getString("course_code"));
	         
	        }
	    } catch (SQLException e) {
	        // Handle or log the exception appropriately
	        e.printStackTrace();
	    }
	    return res;
	}
}
