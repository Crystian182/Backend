package it.unisalento.se.saw.dto;

import java.util.HashSet;
import java.util.Set;


import it.unisalento.se.saw.domain.Subjectofstudy;
import it.unisalento.se.saw.domain.User;

public class CourseDTO {

	
	 private Integer idcourse;
     private String name;
     private String description;
     private int years;
   
    
     
	public Integer getIdcourse() {
		return idcourse;
	}
	public void setIdcourse(Integer idcourse) {
		this.idcourse = idcourse;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getYears() {
		return years;
	}
	public void setYears(int years) {
		this.years = years;
	}

	
	
}
