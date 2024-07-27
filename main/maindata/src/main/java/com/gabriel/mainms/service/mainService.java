package com.gabriel.mainms.service;
import com.gabriel.mainms.model.main;
public interface mainService {
	main[] getAll() throws Exception;
	main get(Integer id) throws Exception;
	main create(main main) throws Exception;
	main update(main main) throws Exception;
	void delete(Integer id) throws Exception;
}
