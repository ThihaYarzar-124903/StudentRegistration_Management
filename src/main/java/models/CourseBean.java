package models;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;

public class CourseBean {
	
	private int id;
	@NotEmpty(message = "Enter course name")
	private String name;
	private String course_code;
	
	public CourseBean() {}

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

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseBean other = (CourseBean) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
	
	
}
