package org.data.storage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.data.storage.dao.ChartDataDAO;
import org.data.storage.model.ChartData;

@Service
public class ChartDataService {
	
	@Autowired
	ChartDataDAO chartDataDAO;
	
	public List<ChartData> getChartDatas(){
		return chartDataDAO.getChartData();
	}
	
	@Transactional
	public ChartData createOrUpdateChartData(ChartData chartData) {
		return chartDataDAO.createOrUpdateChartData(chartData);
	}
	
	public ChartData getChartData(int id) {
		return chartDataDAO.getChartData(id);
	}
	
	@Transactional
	public int deleteChartData(int id) {
		chartDataDAO.deleteChartData(id);
		return id;
	}
	
}
