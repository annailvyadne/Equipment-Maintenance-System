package com.gabriel.mainms.service;
import com.gabriel.mainms.model.Task;
public interface TaskService {
	Task[] getAll() throws Exception;
	Task get(Integer id) throws Exception;
	Task create(Task task) throws Exception;
	Task update(Task task) throws Exception;
	void delete(Integer id) throws Exception;
}
