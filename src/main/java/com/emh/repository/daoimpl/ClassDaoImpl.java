package com.emh.repository.daoimpl;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emh.model.User;
import com.emh.repository.dao.ClassDao;

@Repository("classDao")
public class ClassDaoImpl implements ClassDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public Object Save(Object entityClass) {

		return sessionFactory.getCurrentSession().save(entityClass);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	@Transactional
	public <T> List<T> getListEntity(Class<T> entityClass) {

		return (List<T>) sessionFactory.getCurrentSession().createCriteria(entityClass.getName()).list();
	}

	@Override
	@Transactional
	public <T> T getEntity(Class<T> entityClass, Object id) {

		return (T) sessionFactory.getCurrentSession().get(entityClass, (Serializable) id);
	}

	@Override
	@Transactional
	public void update(Object entity) {

		sessionFactory.getCurrentSession().update(entity);
	}

	@Override
	@Transactional
	public void delete(Object entity) {

		sessionFactory.getCurrentSession().delete(entity);
	}

	@Override
	@Transactional
	public Object getEntityByHQL(String HQL) {

		Query<?> query = sessionFactory.getCurrentSession().createQuery(HQL);
		query.setMaxResults(1);

		return query.uniqueResult();
	}
	
	@Override
	@Transactional
	public <T> List<T> getEntitysByHQL(Class<T> entityClass, String HQL) {
		@SuppressWarnings("unchecked")
		Query<T> query = sessionFactory.getCurrentSession().createQuery(HQL);
		List<T> t = query.list();
		return t;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public User getUserByUsernameAndPassword(User user) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);

		if (user.getPassword() != null) {
			criteria.add(Restrictions.and(Restrictions.eq("username", user.getUsername()),
					Restrictions.eq("password", user.getPassword())));
		} else {
			criteria.add(Restrictions.eq("username", user.getUsername()));
		}
		User currentUser = (User) criteria.uniqueResult();
		if (currentUser != null) {
			return currentUser;
		}
		return null;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
