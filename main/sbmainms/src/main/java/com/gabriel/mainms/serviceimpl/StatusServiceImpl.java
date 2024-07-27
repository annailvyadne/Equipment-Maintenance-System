package com.gabriel.mainms.serviceimpl;
import com.gabriel.mainms.entity.StatusData;
import com.gabriel.mainms.model.Status;
import com.gabriel.mainms.repository.StatusDataRepository;
import com.gabriel.mainms.service.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class StatusServiceImpl implements StatusService {
	Logger logger = LoggerFactory.getLogger(StatusServiceImpl.class);
	@Autowired
	StatusDataRepository statusDataRepository;
	@Autowired
	@Override
	public Status[] getAll() {
		List<StatusData> statussData = new ArrayList<>();
		List<Status> statuss = new ArrayList<>();
		statusDataRepository.findAll().forEach(statussData::add);
		Iterator<StatusData> it = statussData.iterator();
		while(it.hasNext()) {
			StatusData statusData = it.next();
			Status status = new Status();
			status.setId(statusData.getId());
			status.setName(statusData.getName());
			statuss.add(status);
		}
		Status[] array = new Status[statuss.size()];
		for  (int i=0; i<statuss.size(); i++){
			array[i] = statuss.get(i);
		}
		return array;
	}
	@Override
	public Status create(Status status) {
		logger.info(" add:Input " + status.toString());
		StatusData statusData = new StatusData();
		statusData.setName(status.getName());
		statusData = statusDataRepository.save(statusData);
		logger.info(" add:Input " + statusData.toString());
			Status newStatus = new Status();
			newStatus.setId(statusData.getId());
			newStatus.setName(statusData.getName());
		return newStatus;
	}
	@Override
	public Status update(Status status) {
		StatusData statusData = new StatusData();
		statusData.setId(status.getId());
		statusData.setName(status.getName());
		statusData.setCreated(null);
		statusData = statusDataRepository.save(statusData);
		Status newStatus = new Status();
		newStatus.setId(statusData.getId());
		newStatus.setName(statusData.getName());
		return newStatus;
	}
	@Override
	public Status get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Optional<StatusData> optional = statusDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			StatusData statusData = optional.get();
			Status status = new Status();
			status.setId(statusData.getId());
			status.setName(statusData.getName());
			return status;
		}
		logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		return null;
	}
	@Override
	public void delete(Integer id) {
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<StatusData> optional = statusDataRepository.findById(id);
		if( optional.isPresent()) {
			StatusData statusDatum = optional.get();
			statusDataRepository.delete(statusDatum);
			logger.info(" Success >> " + statusDatum.toString());
		}
		else {
			logger.info(" Failed >> unable to locatestatus id:" +  Integer.toString(id));
		}
	}
}
