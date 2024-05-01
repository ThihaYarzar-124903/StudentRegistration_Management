package dao;

import java.util.ArrayList;
import java.util.List;

import dto.CourseDTO;
import models.CourseBean;

public class CourseMapper {
	public CourseDTO mapToRequestDTO( CourseBean bean) {
		CourseDTO dto=new  CourseDTO();
		
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		dto.setCourse_code(bean.getCourse_code());
		
		return dto;
		
	}
	
	public  CourseBean mapToBean( CourseDTO dto) {
		 
		CourseBean bean=new  CourseBean();
		 bean.setId(dto.getId());
		 bean.setName(dto.getName());
		 bean.setCourse_code(dto.getCourse_code());
		 
		 return bean;
	}
	
	public List<CourseBean> mapToListBean(List<CourseDTO> dtos) {
	List<CourseBean> beans=new ArrayList<CourseBean>();
	
		for( CourseDTO dto:dtos) {
			
			CourseBean bean=mapToBean(dto);
		
			beans.add(bean);
		}
		
		return beans;
	}
}
