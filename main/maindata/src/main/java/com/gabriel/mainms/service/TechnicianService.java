package com.gabriel.mainms.service;
import com.gabriel.mainms.model.Technician;
public interface TechnicianService {
	Technician[] getAll() throws Exception;
	Technician get(Integer id) throws Exception;
	Technician create(Technician technician) throws Exception;
	Technician update(Technician technician) throws Exception;
	void delete(Integer id) throws Exception;
}
