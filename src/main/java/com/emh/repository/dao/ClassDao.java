package com.emh.repository.dao;

import java.util.List;

import com.emh.model.User;

public interface ClassDao {
	
	public Object Save(Object entityObject);
	
	public <T> List<T> getListEntity(Class<T> entityClass);
	
	public <T, S> List<T> getListEntity(Class<T> firstEntityClass, Class<S> secondEntityClass,String property ,final Object id);
	
	public <T> T getEntity(Class<T> entityClass, final Object id);
	
	public void update(Object entity);
	
	public void delete(Object entity);
	
	public Object getEntityByHQL(String HQL);
	
	public <T> List<T> getEntitysByHQL(Class<T> entityClass, String HQL);
	
	public User getUserByUsernameAndPassword(User user);
}
