package com.gabriel.mainms.service;
import com.gabriel.mainms.model.Hospital;
public interface HospitalService {
	Hospital[] getAll() throws Exception;
	Hospital get(Integer id) throws Exception;
	Hospital create(Hospital hospital) throws Exception;
	Hospital update(Hospital hospital) throws Exception;
	void delete(Integer id) throws Exception;
}
