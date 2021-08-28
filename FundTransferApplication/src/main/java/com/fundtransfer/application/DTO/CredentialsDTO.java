package com.fundtransfer.application.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonIgnoreProperties(ignoreUnknown = true)


public class CredentialsDTO 
{

	 @ApiModelProperty(value = "User Name", position = 1)
	@NotEmpty(message = "provide user name for login")
	@Size(min = 5, max = 50)
	@Pattern(regexp = "[a-zA-Z0-9]+")
	private String userName;

	 @ApiModelProperty(value = "Password", position = 2) 
	@NotEmpty(message = "provide password")
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
