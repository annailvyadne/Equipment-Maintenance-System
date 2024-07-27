package com.gabriel.mainms.serviceimpl;
import com.gabriel.mainms.entity.HospitalData;
import com.gabriel.mainms.model.Hospital;
import com.gabriel.mainms.repository.HospitalDataRepository;
import com.gabriel.mainms.service.HospitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class HospitalServiceImpl implements HospitalService {
	Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);
	@Autowired
	HospitalDataRepository hospitalDataRepository;
	@Autowired
	@Override
	public Hospital[] getAll() {
		List<HospitalData> hospitalsData = new ArrayList<>();
		List<Hospital> hospitals = new ArrayList<>();
		hospitalDataRepository.findAll().forEach(hospitalsData::add);
		Iterator<HospitalData> it = hospitalsData.iterator();
		while(it.hasNext()) {
			HospitalData hospitalData = it.next();
			Hospital hospital = new Hospital();
			hospital.setId(hospitalData.getId());
			hospital.setName(hospitalData.getName());
			hospitals.add(hospital);
		}
		Hospital[] array = new Hospital[hospitals.size()];
		for  (int i=0; i<hospitals.size(); i++){
			array[i] = hospitals.get(i);
		}
		return array;
	}
	@Override
	public Hospital create(Hospital hospital) {
		logger.info(" add:Input " + hospital.toString());
		HospitalData hospitalData = new HospitalData();
		hospitalData.setName(hospital.getName());
		hospitalData = hospitalDataRepository.save(hospitalData);
		logger.info(" add:Input " + hospitalData.toString());
			Hospital newHospital = new Hospital();
			newHospital.setId(hospitalData.getId());
			newHospital.setName(hospitalData.getName());
		return newHospital;
	}
	@Override
	public Hospital update(Hospital hospital) {
		HospitalData hospitalData = new HospitalData();
		hospitalData.setId(hospital.getId());
		hospitalData.setName(hospital.getName());
		hospitalData.setCreated(null);
		hospitalData = hospitalDataRepository.save(hospitalData);
		Hospital newHospital = new Hospital();
		newHospital.setId(hospitalData.getId());
		newHospital.setName(hospitalData.getName());
		return newHospital;
	}
	@Override
	public Hospital get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Optional<HospitalData> optional = hospitalDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			HospitalData hospitalData = optional.get();
			Hospital hospital = new Hospital();
			hospital.setId(hospitalData.getId());
			hospital.setName(hospitalData.getName());
			return hospital;
		}
		logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		return null;
	}
	@Override
	public void delete(Integer id) {
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<HospitalData> optional = hospitalDataRepository.findById(id);
		if( optional.isPresent()) {
			HospitalData hospitalDatum = optional.get();
			hospitalDataRepository.delete(hospitalDatum);
			logger.info(" Success >> " + hospitalDatum.toString());
		}
		else {
			logger.info(" Failed >> unable to locatehospital id:" +  Integer.toString(id));
		}
	}
}
