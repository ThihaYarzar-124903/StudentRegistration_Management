package dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;

import com.mysql.cj.jdbc.Blob;

import dto.StudentDTO;

public class StudentReportDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/student_mvc_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private Connection conn;

    public StudentReportDAO() throws SQLException {
        conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public List<StudentDTO> getStudentsByDateRangeAndUserId(String fromDate, String toDate, int userId) throws SQLException, UnsupportedEncodingException {
        List<StudentDTO> studentsList = new ArrayList<>();
        String sql = "SELECT s.id,s.student_code,s.name AS student_name,s.date,s.gender,s.dob,s.phone,s.education,s.photo FROM student AS s INNER JOIN user AS u on u.id = s.user_id WHERE s.date BETWEEN ? AND ? AND s.user_id = ?";
        CourseDAO courseDAO = new CourseDAO();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fromDate);
            pstmt.setString(2, toDate);
            pstmt.setInt(3, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                	StudentDTO students = new StudentDTO();
                	
                	students.setStudent_code(rs.getString("student_code"));
                	students.setName(rs.getString("student_name"));
                	students.setDate(rs.getString("date"));
                	students.setGender(rs.getString("gender"));
                	students.setPhone(rs.getString("phone"));
                	students.setDob(rs.getString("dob"));
                	students.setEducation(rs.getString("education"));
                    
                    Blob image_Blob = (Blob) rs.getBlob("photo");
    				byte[] bytes = image_Blob.getBytes(1, (int) image_Blob.length());
    				byte[] encodeBase64 = Base64.encodeBase64(bytes);
    				students.setPhoto(new String(encodeBase64, "UTF-8"));
    				
    				students.setCourses(courseDAO.getCoursesByStudentId(rs.getInt("id")));
                	
                	studentsList.add(students);
                }
            }
        }
        return studentsList;
    }
}
