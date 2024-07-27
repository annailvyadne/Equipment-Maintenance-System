package com.gabriel.mainms.serviceimpl;
import com.gabriel.mainms.entity.mainData;
import com.gabriel.mainms.model.main;
import com.gabriel.mainms.repository.mainDataRepository;
import com.gabriel.mainms.service.mainService;
import com.gabriel.mainms.transform.TransformmainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class mainServiceImpl implements mainService {
	Logger logger = LoggerFactory.getLogger(mainServiceImpl.class);
	@Autowired
	mainDataRepository mainDataRepository;
	@Autowired
	TransformmainService tansformmainService;
	@Override
	public main[] getAll() {
		List<mainData> mainsData = new ArrayList<>();
		List<main> mains = new ArrayList<>();
		mainDataRepository.findAll().forEach(mainsData::add);
		Iterator<mainData> it = mainsData.iterator();
		while(it.hasNext()) {
			mainData mainData = it.next();
			main main = tansformmainService.transform(mainData);
			mains.add(main);
		}
		main[] array = new main[mains.size()];
		for  (int i=0; i<mains.size(); i++){
			array[i] = mains.get(i);
		}
		return array;
	}
	@Override
	public main create(main main) {
		logger.info(" add:Input " + main.toString());
		mainData mainData = tansformmainService.transform(main);
		mainData = mainDataRepository.save(mainData);
		logger.info(" add:Input " + mainData.toString());
			main newmain = tansformmainService.transform(mainData);
		return newmain;
	}
	@Override
	public main update(main main) {
		mainData mainData = tansformmainService.transform(main);
		mainData.setCreated(null);
		mainData = mainDataRepository.save(mainData);
		main newmain = tansformmainService.transform(mainData);
		return newmain;
	}
	@Override
	public main get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Optional<mainData> optional = mainDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			mainData mainData = optional.get();
			main main = tansformmainService.transform(mainData);
			return main;
		}
		logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		return null;
	}
	@Override
	public void delete(Integer id) {
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<mainData> optional = mainDataRepository.findById(id);
		if( optional.isPresent()) {
			mainData mainDatum = optional.get();
			mainDataRepository.delete(mainDatum);
			logger.info(" Success >> " + mainDatum.toString());
		}
		else {
			logger.info(" Failed >> unable to locatemain id:" +  Integer.toString(id));
		}
	}
}
