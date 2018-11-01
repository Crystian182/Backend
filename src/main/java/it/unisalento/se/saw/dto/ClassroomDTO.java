package it.unisalento.se.saw.dto;

import java.util.List;

public class ClassroomDTO {
	
	private int id;
	private String name;
	private int seats;
	private float latitude;
	private float longitude;
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
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
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
	
	
	

}
