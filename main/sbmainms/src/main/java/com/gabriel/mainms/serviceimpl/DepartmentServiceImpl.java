package com.gabriel.mainms.serviceimpl;
import com.gabriel.mainms.entity.DepartmentData;
import com.gabriel.mainms.model.Department;
import com.gabriel.mainms.repository.DepartmentDataRepository;
import com.gabriel.mainms.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class DepartmentServiceImpl implements DepartmentService {
	Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
	@Autowired
	DepartmentDataRepository departmentDataRepository;
	@Autowired
	@Override
	public Department[] getAll() {
		List<DepartmentData> departmentsData = new ArrayList<>();
		List<Department> departments = new ArrayList<>();
		departmentDataRepository.findAll().forEach(departmentsData::add);
		Iterator<DepartmentData> it = departmentsData.iterator();
		while(it.hasNext()) {
			DepartmentData departmentData = it.next();
			Department department = new Department();
			department.setId(departmentData.getId());
			department.setName(departmentData.getName());
			departments.add(department);
		}
		Department[] array = new Department[departments.size()];
		for  (int i=0; i<departments.size(); i++){
			array[i] = departments.get(i);
		}
		return array;
	}
	@Override
	public Department create(Department department) {
		logger.info(" add:Input " + department.toString());
		DepartmentData departmentData = new DepartmentData();
		departmentData.setName(department.getName());
		departmentData = departmentDataRepository.save(departmentData);
		logger.info(" add:Input " + departmentData.toString());
			Department newDepartment = new Department();
			newDepartment.setId(departmentData.getId());
			newDepartment.setName(departmentData.getName());
		return newDepartment;
	}
	@Override
	public Department update(Department department) {
		DepartmentData departmentData = new DepartmentData();
		departmentData.setId(department.getId());
		departmentData.setName(department.getName());
		departmentData.setCreated(null);
		departmentData = departmentDataRepository.save(departmentData);
		Department newDepartment = new Department();
		newDepartment.setId(departmentData.getId());
		newDepartment.setName(departmentData.getName());
		return newDepartment;
	}
	@Override
	public Department get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Optional<DepartmentData> optional = departmentDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			DepartmentData departmentData = optional.get();
			Department department = new Department();
			department.setId(departmentData.getId());
			department.setName(departmentData.getName());
			return department;
		}
		logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		return null;
	}
	@Override
	public void delete(Integer id) {
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<DepartmentData> optional = departmentDataRepository.findById(id);
		if( optional.isPresent()) {
			DepartmentData departmentDatum = optional.get();
			departmentDataRepository.delete(departmentDatum);
			logger.info(" Success >> " + departmentDatum.toString());
		}
		else {
			logger.info(" Failed >> unable to locatedepartment id:" +  Integer.toString(id));
		}
	}
}
