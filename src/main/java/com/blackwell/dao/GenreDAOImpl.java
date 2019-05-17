package com.blackwell.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.blackwell.entity.Genre;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Repository
public class GenreDAOImpl implements GenreDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Genre> get() {
		Session session = sessionFactory.getCurrentSession();
		Query<Genre> query = session.createQuery("from Genre", Genre.class);
		return query.getResultList();
	}
	
	@Override
	public Genre get(int id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Genre.class, id);
	}

	@Override
	public Genre get(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Genre.class); // TODO change this "code"
		
	    criteria.add(Restrictions.eq("name", name));
		return (Genre) criteria.uniqueResult();
	}

	@Override
	public void save(Genre genre) {
		throw new NotImplementedException();
	}

	@Override
	public void delete(int id) {
		throw new NotImplementedException();
	}

}
