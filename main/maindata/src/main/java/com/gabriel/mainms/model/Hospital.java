package com.gabriel.mainms.model;
import lombok.Data;
import java.util.Date;

@Data
public class Hospital{
	int id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	String name;

	@Override
	public String toString() {
		return name;
	}
}
