package com.gabriel.mainms.serviceimpl;
import com.gabriel.mainms.entity.EquipmentData;
import com.gabriel.mainms.model.Equipment;
import com.gabriel.mainms.repository.EquipmentDataRepository;
import com.gabriel.mainms.service.EquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class EquipmentServiceImpl implements EquipmentService {
	Logger logger = LoggerFactory.getLogger(EquipmentServiceImpl.class);
	@Autowired
	EquipmentDataRepository equipmentDataRepository;
	@Autowired
	@Override
	public Equipment[] getAll() {
		List<EquipmentData> equipmentsData = new ArrayList<>();
		List<Equipment> equipments = new ArrayList<>();
		equipmentDataRepository.findAll().forEach(equipmentsData::add);
		Iterator<EquipmentData> it = equipmentsData.iterator();
		while(it.hasNext()) {
			EquipmentData equipmentData = it.next();
			Equipment equipment = new Equipment();
			equipment.setId(equipmentData.getId());
			equipment.setName(equipmentData.getName());
			equipments.add(equipment);
		}
		Equipment[] array = new Equipment[equipments.size()];
		for  (int i=0; i<equipments.size(); i++){
			array[i] = equipments.get(i);
		}
		return array;
	}
	@Override
	public Equipment create(Equipment equipment) {
		logger.info(" add:Input " + equipment.toString());
		EquipmentData equipmentData = new EquipmentData();
		equipmentData.setName(equipment.getName());
		equipmentData = equipmentDataRepository.save(equipmentData);
		logger.info(" add:Input " + equipmentData.toString());
			Equipment newEquipment = new Equipment();
			newEquipment.setId(equipmentData.getId());
			newEquipment.setName(equipmentData.getName());
		return newEquipment;
	}
	@Override
	public Equipment update(Equipment equipment) {
		EquipmentData equipmentData = new EquipmentData();
		equipmentData.setId(equipment.getId());
		equipmentData.setName(equipment.getName());
		equipmentData.setCreated(null);
		equipmentData = equipmentDataRepository.save(equipmentData);
		Equipment newEquipment = new Equipment();
		newEquipment.setId(equipmentData.getId());
		newEquipment.setName(equipmentData.getName());
		return newEquipment;
	}
	@Override
	public Equipment get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Optional<EquipmentData> optional = equipmentDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			EquipmentData equipmentData = optional.get();
			Equipment equipment = new Equipment();
			equipment.setId(equipmentData.getId());
			equipment.setName(equipmentData.getName());
			return equipment;
		}
		logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		return null;
	}
	@Override
	public void delete(Integer id) {
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<EquipmentData> optional = equipmentDataRepository.findById(id);
		if( optional.isPresent()) {
			EquipmentData equipmentDatum = optional.get();
			equipmentDataRepository.delete(equipmentDatum);
			logger.info(" Success >> " + equipmentDatum.toString());
		}
		else {
			logger.info(" Failed >> unable to locateequipment id:" +  Integer.toString(id));
		}
	}
}
