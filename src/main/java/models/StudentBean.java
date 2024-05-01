package models;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;


public class StudentBean {
	
	private int id;
	@NotEmpty(message = "Enter student name")
	private String name;
	private String student_code;
	@NotEmpty(message = "Enter student date of birth")
	private String dob;
	@NotEmpty(message = "Enter student join date")
	private String date;
	@NotEmpty(message = "Gender is required")
	private String gender;
	@NotEmpty(message = "Enter student phone number")
	private String phone;
	private String education;
	private MultipartFile multipart;
	private Blob image_blob;
	private String photo;
	private int user_id;
	private int delete;
	
	private List<CourseBean> courses = new ArrayList<CourseBean>();
	private String course_names;
	
	public StudentBean() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStudent_code() {
		return student_code;
	}

	public void setStudent_code(String student_code) {
		this.student_code = student_code;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public MultipartFile getMultipart() {
		return multipart;
	}

	public void setMultipart(MultipartFile multipart) {
		this.multipart = multipart;
	}

	public Blob getImage_blob() {
		return image_blob;
	}

	public void setImage_blob(Blob image_blob) {
		this.image_blob = image_blob;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<CourseBean> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseBean> courses) {
		this.courses = courses;
	}

	public String getCourse_names() {
		return course_names;
	}

	public void setCourse_names(String course_names) {
		this.course_names = course_names;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getDelete() {
		return delete;
	}

	public void setDelete(int delete) {
		this.delete = delete;
	}
	
	
	
}
