package it.unisalento.se.saw.dto;

public class UserDTO {

	private int id;
	private String name;
	private String surname;
	private String username;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public TypeDTO getTypeDTO() {
		return typeDTO;
	}
	public void setTypeDTO(TypeDTO typeDTO) {
		this.typeDTO = typeDTO;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
