package com.emh.repository.businessimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;
import com.emh.repository.dao.ClassDao;

@Service("ClassBusiness")
public class ClassBusinessImpl implements ClassBusiness {

	@Autowired
	private ClassDao classDao;
	
	@Override
	public Object createEntity(Object entityObject) {
		
		return classDao.Save(entityObject);
	}

	@Override
	public <T> List<T> selectAllEntity(Class<T> entityClass) {
		
		return classDao.getListEntity(entityClass);
	}

	@Override
	public <T> T selectEntity(Class<T> entityClass, Object id) {
		
		return classDao.getEntity(entityClass, id);
	}

	@Override
	public void updateEntity(Object entity) {
		
		classDao.update(entity);
	}
	
	@Override
	public void deleteEntity(Object entity) {
		
		classDao.delete(entity);
	}

	@Override
	public Object selectLastEntityByHQL(String HQL) {
		
		return classDao.getLastEntityByHQL(HQL);
	}

	@Override
	public User selectUserByUsernameAndPassword(User user) {
		
		return classDao.getUserByUsernameAndPassword(user);
	}

	public ClassDao getClassDao() {
		return classDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}
}
