package com.gabriel.mainms.serviceimpl;
import com.gabriel.mainms.entity.TechnicianData;
import com.gabriel.mainms.model.Technician;
import com.gabriel.mainms.repository.TechnicianDataRepository;
import com.gabriel.mainms.service.TechnicianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class TechnicianServiceImpl implements TechnicianService {
	Logger logger = LoggerFactory.getLogger(TechnicianServiceImpl.class);
	@Autowired
	TechnicianDataRepository technicianDataRepository;
	@Autowired
	@Override
	public Technician[] getAll() {
		List<TechnicianData> techniciansData = new ArrayList<>();
		List<Technician> technicians = new ArrayList<>();
		technicianDataRepository.findAll().forEach(techniciansData::add);
		Iterator<TechnicianData> it = techniciansData.iterator();
		while(it.hasNext()) {
			TechnicianData technicianData = it.next();
			Technician technician = new Technician();
			technician.setId(technicianData.getId());
			technician.setName(technicianData.getName());
			technicians.add(technician);
		}
		Technician[] array = new Technician[technicians.size()];
		for  (int i=0; i<technicians.size(); i++){
			array[i] = technicians.get(i);
		}
		return array;
	}
	@Override
	public Technician create(Technician technician) {
		logger.info(" add:Input " + technician.toString());
		TechnicianData technicianData = new TechnicianData();
		technicianData.setName(technician.getName());
		technicianData = technicianDataRepository.save(technicianData);
		logger.info(" add:Input " + technicianData.toString());
			Technician newTechnician = new Technician();
			newTechnician.setId(technicianData.getId());
			newTechnician.setName(technicianData.getName());
		return newTechnician;
	}
	@Override
	public Technician update(Technician technician) {
		TechnicianData technicianData = new TechnicianData();
		technicianData.setId(technician.getId());
		technicianData.setName(technician.getName());
		technicianData.setCreated(null);
		technicianData = technicianDataRepository.save(technicianData);
		Technician newTechnician = new Technician();
		newTechnician.setId(technicianData.getId());
		newTechnician.setName(technicianData.getName());
		return newTechnician;
	}
	@Override
	public Technician get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Optional<TechnicianData> optional = technicianDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			TechnicianData technicianData = optional.get();
			Technician technician = new Technician();
			technician.setId(technicianData.getId());
			technician.setName(technicianData.getName());
			return technician;
		}
		logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		return null;
	}
	@Override
	public void delete(Integer id) {
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<TechnicianData> optional = technicianDataRepository.findById(id);
		if( optional.isPresent()) {
			TechnicianData technicianDatum = optional.get();
			technicianDataRepository.delete(technicianDatum);
			logger.info(" Success >> " + technicianDatum.toString());
		}
		else {
			logger.info(" Failed >> unable to locatetechnician id:" +  Integer.toString(id));
		}
	}
}
