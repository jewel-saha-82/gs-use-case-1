package org.data.storage.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.data.storage.model.TopGainerLooser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class TopGainerLooserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<TopGainerLooser> getGainerLooser(){
		
		Session session = sessionFactory.getCurrentSession();

		// Using Criteria builder
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<TopGainerLooser> query = builder.createQuery(TopGainerLooser.class);
		Root<TopGainerLooser> root = query.from(TopGainerLooser.class);
		query.select(root);
		return session.createQuery(query).getResultList();
		
		//return session.createQuery("From ChartData",ChartData.class).list();		
		
	}
	
	public TopGainerLooser createOrUpdateData(TopGainerLooser data) {
		Session session = sessionFactory.getCurrentSession();	
		session.saveOrUpdate(data);
		return data;
	}
	
	public TopGainerLooser getData(int id) {
		Session session = sessionFactory.getCurrentSession();
		TopGainerLooser data = session.get(TopGainerLooser.class, id);
		return data;
	}
	
	public void deleteData(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.createQuery("delete from TopGainerLooser where id=:id").setParameter("id", id).executeUpdate();
	}

}

