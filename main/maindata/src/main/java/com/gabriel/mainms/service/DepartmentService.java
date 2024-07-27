package com.gabriel.mainms.service;
import com.gabriel.mainms.model.Department;
public interface DepartmentService {
	Department[] getAll() throws Exception;
	Department get(Integer id) throws Exception;
	Department create(Department department) throws Exception;
	Department update(Department department) throws Exception;
	void delete(Integer id) throws Exception;
}
