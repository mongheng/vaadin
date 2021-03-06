package com.emh.repository.business;

import java.util.List;

import com.emh.model.User;

public interface ClassBusiness {
	
	public Object createEntity(Object entityObject);
	
	public <T> List<T> selectAllEntity(Class<T> entityClass);
	
	public <T, S> List<T> selectListEntity(Class<T> firstEntityClass, Class<S> secondEntityClass,String property ,final Object id);
	
	public <T> T selectEntity(Class<T> entityClass, final Object id);
	
	public void updateEntity(Object entity);
	
	public void deleteEntity(Object entity);
	
	public Object selectEntityByHQL(String HQL);
	
	public <T> List<T> selectListEntityByHQL(Class<T> entityClass, String HQL);
	
	public User selectUserByUsernameAndPassword(User user);
}
