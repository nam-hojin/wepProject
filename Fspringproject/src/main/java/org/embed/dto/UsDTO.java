package org.embed.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsDTO {

	private int userId;
	private String userName;
	private String password;
	private String passwordCheck; 
	private String name;
	private String phone;
	private String email;
	private String birth;
	private String gender;
	
	
}
