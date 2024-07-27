package com.gabriel.mainms.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "main_data")
public class mainData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String hospitalName;
	int hospitalId;
	String departmentName;
	int departmentId;
	String equipmentName;
	int equipmentId;
	String statusName;
	int statusId;
	String technicianName;
	int technicianId;
	String taskName;
	int taskId;
	String taskDescription;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date lastUpdated;


	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date created;

}
