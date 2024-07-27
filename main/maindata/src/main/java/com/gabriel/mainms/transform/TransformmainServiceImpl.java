package com.gabriel.mainms.transform;
import com.gabriel.mainms.entity.mainData;
import com.gabriel.mainms.model.main;
import org.springframework.stereotype.Service;
@Service
public class TransformmainServiceImpl implements TransformmainService {
	@Override
	public mainData transform(main main){
		mainData mainData = new mainData();
		mainData.setId(main.getId());
		mainData.setHospitalName(main.getHospitalName());
		mainData.setHospitalId(main.getHospitalId());
		mainData.setDepartmentName(main.getDepartmentName());
		mainData.setDepartmentId(main.getDepartmentId());
		mainData.setEquipmentName(main.getEquipmentName());
		mainData.setEquipmentId(main.getEquipmentId());
		mainData.setStatusName(main.getStatusName());
		mainData.setStatusId(main.getStatusId());
		mainData.setTechnicianName(main.getTechnicianName());
		mainData.setTechnicianId(main.getTechnicianId());
		mainData.setTaskName(main.getTaskName());
		mainData.setTaskId(main.getTaskId());
		mainData.setTaskDescription(main.getTaskDescription());
		return mainData;
	}
	@Override

	public main transform(mainData mainData){;
		main main = new main();
		main.setId(mainData.getId());
		main.setHospitalName(mainData.getHospitalName());
		main.setHospitalId(mainData.getHospitalId());
		main.setDepartmentName(mainData.getDepartmentName());
		main.setDepartmentId(mainData.getDepartmentId());
		main.setEquipmentName(mainData.getEquipmentName());
		main.setEquipmentId(mainData.getEquipmentId());
		main.setStatusName(mainData.getStatusName());
		main.setStatusId(mainData.getStatusId());
		main.setTechnicianName(mainData.getTechnicianName());
		main.setTechnicianId(mainData.getTechnicianId());
		main.setTaskName(mainData.getTaskName());
		main.setTaskId(mainData.getTaskId());
		main.setTaskDescription(mainData.getTaskDescription());
		return main;
	}
}
