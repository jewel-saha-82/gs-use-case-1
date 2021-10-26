package org.data.storage.service;

import java.util.List;

import org.data.storage.dao.ChartDataDAO;
import org.data.storage.model.ChartData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	public void insertUsingBatch(List<ChartData> chartDataList){
		chartDataDAO.inserUsingBatch(chartDataList);
	}
	
	public ChartData getChartData(int id) {
		return chartDataDAO.getChartData(id);
	}
	
	@Transactional
	public int deleteChartData(int id) {
		return chartDataDAO.deleteChartData(id);
	}
	
}
