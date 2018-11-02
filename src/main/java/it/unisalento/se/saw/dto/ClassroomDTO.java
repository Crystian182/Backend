package it.unisalento.se.saw.dto;

import java.util.List;

public class ClassroomDTO {
	
	private int id;
	private String name;
	private int seats;
	private float lat;
	private float lng;
	private BuildingDTO building;
	private List<ToolDTO> tools;
	
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
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
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
	public BuildingDTO getBuilding() {
		return building;
	}
	public void setBuilding(BuildingDTO building) {
		this.building = building;
	}
	public List<ToolDTO> getTools() {
		return tools;
	}
	public void setTools(List<ToolDTO> tools) {
		this.tools = tools;
	}
<<<<<<< HEAD
=======
	
	
	
>>>>>>> 7292ba6b82995e81824c24c8d2fc8432d6f05098

}
