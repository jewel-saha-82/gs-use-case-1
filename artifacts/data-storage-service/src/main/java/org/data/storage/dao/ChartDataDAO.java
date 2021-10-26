package org.data.storage.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.data.storage.model.ChartData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


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
	}
	
	public ChartData createOrUpdateChartData(ChartData chartData) {
		Session session = sessionFactory.getCurrentSession();	
		session.saveOrUpdate(chartData);
		return chartData;
	}
	
	public void inserUsingBatch(List<ChartData> dataList) {
		Session session = sessionFactory.getCurrentSession();
		for(ChartData data:dataList)
			session.save(data);
	}
	
	public ChartData getChartData(int id) {
		Session session = sessionFactory.getCurrentSession();
		ChartData chartData = session.get(ChartData.class, id);
		return chartData;
	}
	
	public int deleteChartData(int id) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaDelete<ChartData> criteriaDelete = criteriaBuilder.createCriteriaDelete(ChartData.class);
		Root<ChartData> root = criteriaDelete.from(ChartData.class);
		criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));
		return session.createQuery(criteriaDelete).executeUpdate();
	}

}

