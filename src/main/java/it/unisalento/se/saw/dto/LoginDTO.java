package it.unisalento.se.saw.dto;

public class LoginDTO {
	
	private String email;
	private String password;
	private TypeDTO typeDTO;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public TypeDTO getTypeDTO() {
		return typeDTO;
	}
	public void setTypeDTO(TypeDTO typeDTO) {
		this.typeDTO = typeDTO;
	}
	

}
