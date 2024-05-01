package dto;

import java.sql.Blob;

import org.springframework.web.multipart.MultipartFile;

public class UserDTO {
	private int id;
	private String name;
	private String user_code;
	private String email;
	private String password;
	private String confirm_password;
	private MultipartFile multipart;
	private Blob image_blob;
	private String image;
	private int role_id;
	private String dob;
	private String gender;
	private String phone;
	private String nrc;
	private boolean is_disabled;
	
	public UserDTO() {}

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

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm_password() {
		return confirm_password;
	}

	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
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

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public boolean isIs_disabled() {
		return is_disabled;
	}

	public void setIs_disabled(boolean is_disabled) {
		this.is_disabled = is_disabled;
	}

	
}
