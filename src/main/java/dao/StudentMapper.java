package dao;

import java.util.ArrayList;
import java.util.List;

import dto.CourseDTO;
import dto.StudentDTO;
import models.CourseBean;
import models.StudentBean;

public class StudentMapper {
	public StudentDTO mapToDTO( StudentBean bean) {
		CourseMapper course_mapper = new CourseMapper();
		
		StudentDTO dto = new  StudentDTO();
		
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		dto.setStudent_code(bean.getStudent_code());
		dto.setDob(bean.getDob());
		dto.setGender(bean.getGender());
		dto.setDate(bean.getDate());
		dto.setPhone(bean.getPhone());
		dto.setEducation(bean.getEducation());
		dto.setImage_blob(bean.getImage_blob());
		dto.setUser_id(bean.getUser_id());
		dto.setDelete(bean.getDelete());
		
		List<CourseDTO> courses = new ArrayList<CourseDTO>();	
		
		for(CourseBean course : bean.getCourses()) {
			CourseDTO course_dto = course_mapper.mapToRequestDTO(course);			
			courses.add(course_dto);			
		}
		
		dto.setCourses(courses);
		
		return dto;
	}
	
	public StudentBean mapToBean(StudentDTO dto) {
		StudentBean bean = new StudentBean();
	    
		bean.setId(dto.getId());
		bean.setName(dto.getName());
		bean.setStudent_code(dto.getStudent_code());
		bean.setDob(dto.getDob());
		bean.setGender(dto.getGender());
		bean.setDate(dto.getDate());
		bean.setPhone(dto.getPhone());
		bean.setEducation(dto.getEducation());
		bean.setPhoto(dto.getPhoto());
		bean.setDelete(dto.getDelete());
	    
	    StringBuilder course_names = new StringBuilder();
	    
	    List<CourseBean> courses = new ArrayList<CourseBean>();
//	    boolean isFirst = true;
	    
	    for(CourseDTO dto_course : dto.getCourses()) {
	    	
	        CourseBean course = new CourseBean();
	        
//	        course.setId(dto_course.getId());
//	        course.setName(dto_course.getName());
//	        course.setCourse_code(dto_course.getCourse_code());
//	        if (!isFirst) {
//	        	course_names.append(", ");
//	        } else {
//	            isFirst = false;
//	        }
	        
	        course.setId(dto_course.getId());
	        course.setName(dto_course.getName());
	        course_names.append(dto_course.getName()+",");
	        course.setCourse_code(dto_course.getCourse_code());
	        
	        
	        courses.add(course);
	    } 
	    
	    bean.setCourse_names(course_names.toString());
	    
	    bean.setCourses(courses);
	    return bean;
	}
	
	public List<StudentBean> mapToListBean(List<StudentDTO> dtos) {
		List<StudentBean> beans = new ArrayList<StudentBean>();
		for(StudentDTO dto:dtos) {
			
			StudentBean bean = mapToBean(dto);
			
			beans.add(bean);
		}
		
		return beans;
	}

}
