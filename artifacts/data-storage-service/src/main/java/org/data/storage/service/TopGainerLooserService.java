package org.data.storage.service;

import java.util.List;

import org.data.storage.dao.TopGainerLooserDAO;
import org.data.storage.model.TopGainerLooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopGainerLooserService {
	
	@Autowired
	TopGainerLooserDAO topGainerLooserDAO;
	
	public List<TopGainerLooser> getDatas(){
		return topGainerLooserDAO.getGainerLooser();
	}
	
	@Transactional
	public TopGainerLooser createOrUpdateData(TopGainerLooser data) {
		return topGainerLooserDAO.createOrUpdateData(data);
	}
	
	public TopGainerLooser getData(int id) {
		return topGainerLooserDAO.getData(id);
	}
	
	@Transactional
	public int deleteData(int id) {
		return topGainerLooserDAO.deleteData(id);
	}
	
}
