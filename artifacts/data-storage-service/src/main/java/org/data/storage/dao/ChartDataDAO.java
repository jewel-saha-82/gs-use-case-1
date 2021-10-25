package org.data.storage.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.data.storage.model.ChartData;


@Repository
public class ChartDataDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<ChartData> getChartData(){
		
		Session session = sessionFactory.getCurrentSession();

		// Using Criteria builder
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<ChartData> query = builder.createQuery(ChartData.class);
		Root<ChartData> root = query.from(ChartData.class);
		query.select(root);
		return session.createQuery(query).getResultList();
		
		//return session.createQuery("From ChartData",ChartData.class).list();		
		
	}
	
	public ChartData createOrUpdateChartData(ChartData chartData) {
		Session session = sessionFactory.getCurrentSession();	
		session.saveOrUpdate(chartData);
		return chartData;
	}
	
	public ChartData getChartData(int id) {
		Session session = sessionFactory.getCurrentSession();
		ChartData chartData = session.get(ChartData.class, id);
		return chartData;
	}
	
	public void deleteChartData(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.createQuery("delete from ChartData where id=:id").setParameter("id", id).executeUpdate();
	}

}

