package com.gabriel.mainms.model;
import lombok.Data;
import java.util.Date;

@Data
public class Equipment{
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	int id;
	String name;

	@Override
	public String toString() {
		return name;
	}
}
