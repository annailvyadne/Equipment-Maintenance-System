package com.gabriel.mainms.serviceimpl;
import com.gabriel.mainms.entity.TaskData;
import com.gabriel.mainms.model.Task;
import com.gabriel.mainms.repository.TaskDataRepository;
import com.gabriel.mainms.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class TaskServiceImpl implements TaskService {
	Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
	@Autowired
	TaskDataRepository taskDataRepository;
	@Autowired
	@Override
	public Task[] getAll() {
		List<TaskData> tasksData = new ArrayList<>();
		List<Task> tasks = new ArrayList<>();
		taskDataRepository.findAll().forEach(tasksData::add);
		Iterator<TaskData> it = tasksData.iterator();
		while(it.hasNext()) {
			TaskData taskData = it.next();
			Task task = new Task();
			task.setId(taskData.getId());
			task.setName(taskData.getName());
			tasks.add(task);
		}
		Task[] array = new Task[tasks.size()];
		for  (int i=0; i<tasks.size(); i++){
			array[i] = tasks.get(i);
		}
		return array;
	}
	@Override
	public Task create(Task task) {
		logger.info(" add:Input " + task.toString());
		TaskData taskData = new TaskData();
		taskData.setName(task.getName());
		taskData = taskDataRepository.save(taskData);
		logger.info(" add:Input " + taskData.toString());
			Task newTask = new Task();
			newTask.setId(taskData.getId());
			newTask.setName(taskData.getName());
		return newTask;
	}
	@Override
	public Task update(Task task) {
		TaskData taskData = new TaskData();
		taskData.setId(task.getId());
		taskData.setName(task.getName());
		taskData.setCreated(null);
		taskData = taskDataRepository.save(taskData);
		Task newTask = new Task();
		newTask.setId(taskData.getId());
		newTask.setName(taskData.getName());
		return newTask;
	}
	@Override
	public Task get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Optional<TaskData> optional = taskDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			TaskData taskData = optional.get();
			Task task = new Task();
			task.setId(taskData.getId());
			task.setName(taskData.getName());
			return task;
		}
		logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		return null;
	}
	@Override
	public void delete(Integer id) {
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<TaskData> optional = taskDataRepository.findById(id);
		if( optional.isPresent()) {
			TaskData taskDatum = optional.get();
			taskDataRepository.delete(taskDatum);
			logger.info(" Success >> " + taskDatum.toString());
		}
		else {
			logger.info(" Failed >> unable to locatetask id:" +  Integer.toString(id));
		}
	}
}
