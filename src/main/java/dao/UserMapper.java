package dao;

import java.util.ArrayList;
import java.util.List;

import dto.UserDTO;
import models.UserBean;

public class UserMapper {
	public UserDTO mapToRequestDTO( UserBean bean) {
		UserDTO dto=new  UserDTO();
		
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		dto.setEmail(bean.getEmail());
		dto.setUser_code(bean.getUser_code());
		dto.setRole_id(bean.getRole_id());
		dto.setIs_disabled(bean.isIs_disabled());
		dto.setDob(bean.getDob());
		dto.setGender(bean.getGender());
		dto.setPhone(bean.getPhone());
		dto.setNrc(bean.getNrc());
		dto.setImage_blob(bean.getImage_blob());
		dto.setPassword(bean.getPassword());
		
		return dto;
	}
	
	public  UserBean mapToBean( UserDTO dto) {
		UserBean bean=new  UserBean();
		 
		 bean.setId(dto.getId());
		 bean.setName(dto.getName());
		 bean.setEmail(dto.getEmail());
		 bean.setUser_code(dto.getUser_code());
		 bean.setRole_id(dto.getRole_id());
		 bean.setIs_disabled(dto.isIs_disabled());
		 bean.setDob(dto.getDob());
		 bean.setGender(dto.getGender());
		 bean.setPhone(dto.getPhone());
		 bean.setNrc(dto.getNrc());
		 bean.setImage(dto.getImage());
		 bean.setPassword(dto.getPassword());
		 bean.setIs_disabled(dto.isIs_disabled());
		 
		 return bean;
	}
	
	public List<UserBean> mapToListBean(List<UserDTO> dtos) {
		List<UserBean> beans=new ArrayList<UserBean>();
		for(UserDTO dto:dtos) {
			UserBean bean=mapToBean(dto);
			beans.add(bean);
		}
		return beans;
	}
}
