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

public class ReportDAO {
	private static final String JDBC_URL = "jdbc:mysql://localhost:3307/student_mvc_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    
    public List<StudentDTO> getDetailsStudents(int userId) throws UnsupportedEncodingException {
        List<StudentDTO> DetailsLists = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT s.id,s.student_code,s.name AS student_name,s.date,s.gender,s.dob,s.phone,s.education,s.photo FROM student AS s INNER JOIN user AS u on u.id = s.user_id WHERE s.user_id = ? AND DATE(s.date) = CURDATE()";
            CourseDAO courseDAO = new CourseDAO();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                    	StudentDTO report = new StudentDTO();
                    	
                    	report.setStudent_code(resultSet.getString("student_code"));
                        report.setName(resultSet.getString("student_name"));
                        report.setDate(resultSet.getString("date"));
                        report.setGender(resultSet.getString("gender"));
                        report.setPhone(resultSet.getString("phone"));
                        report.setDob(resultSet.getString("dob"));
                        report.setEducation(resultSet.getString("education"));
                        
                        Blob image_Blob = (Blob) resultSet.getBlob("photo");
        				byte[] bytes = image_Blob.getBytes(1, (int) image_Blob.length());
        				byte[] encodeBase64 = Base64.encodeBase64(bytes);
        				report.setPhoto(new String(encodeBase64, "UTF-8"));
        				
        				report.setCourses(courseDAO.getCoursesByStudentId(resultSet.getInt("id")));
        				
                        //report.setProducts(getProductsForSale(resultSet.getInt("sales_id")));
                        DetailsLists.add(report);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return DetailsLists;
    }
}
