package it.unisalento.se.saw.dto;

import java.util.List;

public class BuildingDTO {
	
	private int id;
	private String name;
	private String address;
	private float lat;
	private float lng;
	private List<ClassroomDTO> classrooms;
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLng() {
		return lng;
	}
	public void setLng(float lng) {
		this.lng = lng;
	}
	public List<ClassroomDTO> getClassrooms() {
		return classrooms;
	}
	public void setClassrooms(List<ClassroomDTO> classrooms) {
		this.classrooms = classrooms;
	}
	

}
