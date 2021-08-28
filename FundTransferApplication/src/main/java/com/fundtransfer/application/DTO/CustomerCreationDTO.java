package com.fundtransfer.application.DTO;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class CustomerCreationDTO implements Serializable {
	private static final long serialVersionUID = 3994192272098493497L;
	
	@ApiModelProperty(value = "User Name", position = 1)
	@NotEmpty(message = "provide user name for login")
	@Size(min = 5, max = 50)
	@Pattern(regexp = "[a-zA-Z0-9]+")
	private String userName;

	@ApiModelProperty(value = "Customer Name", position = 2)
	@NotEmpty(message = "provide Customer name")
	@Size(min = 2, max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9_ ]*$")
	private String customerName;

	@ApiModelProperty(value = "Date of Birth", position = 3)
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	@NotEmpty(message = "Provide date of birth ")
	@Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$",message = "Provide date of birth (yyyy-MM-dd) format" )
	private String dateOfBirth;

	@ApiModelProperty(value = "Mobile No",position = 5)
	@NotEmpty(message = "provide mobile no ,only digits")
	@Pattern(regexp = "[0-9]{10}", message = "provide valid mobile no")
	private String mobileNo;
	
	@ApiModelProperty(value = "Gender",position = 4)
	@NotEmpty(message = "provide gender")
	private String gender;

	@ApiModelProperty(value = "Email Id",position = 6)
	@NotEmpty(message = "Provide email id")
	@Email
	private String emailId;

	@ApiModelProperty(value = "Pan Number",position = 7)
	@NotEmpty(message = "provide pan number")
	@Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "provide valid pan number")
	private String panCardNo;

	@ApiModelProperty(value = "Aadhaar No",position = 8)
	@NotEmpty(message = "provide aadhaar number,only digits ")
	@Pattern(regexp = "[0-9]{12}", message = "provide valid aadhaar number")
	private String aadhaarCardNo;

	@ApiModelProperty(value = "Address",position = 9)
	@NotEmpty(message = "Provide Customer Address")
	@Size(min = 10, max = 100, message = "Address must be between 10 and 100 characters")
	private String address;

	@ApiModelProperty(value = "Account Type",position = 10)
	@NotEmpty(message = "provide account type")
	@Size(min = 5, max = 10) // Value must contain at least 5 characters and a maximum of 10 characters
	@Pattern(regexp = "[a-zA-Z]+", message = "provide valid account type")
	private String accountType;

	@ApiModelProperty(value = "Opening Deposit",position = 11)
	@NotEmpty(message = "provide opening deposit ,only digits")
	@Pattern(regexp = "([^.\\d]+|[\\d+\\.]{2,})", message = "provide valid amount ")
	private String openingDeposit;

	@ApiModelProperty(value = "Bank Name",position = 12)
	@Size(min = 2, max = 25)
	@Pattern(regexp = "[a-zA-Z]+", message = "provide valid  Bank Name")
	@NotEmpty(message = "provide Bank Name")
	private String bankName;

	@ApiModelProperty(value = "Branch Name",position = 13)
	@Size(min = 5, max = 25)
	@Pattern(regexp = "[a-zA-Z]+", message = "provide valid  Branch Name")
	@NotEmpty(message = "provide Branch Name")
	private String branchName;
	
	@ApiModelProperty(value = "IFSC Code",position = 14)
	@NotEmpty(message="provide IFSC Code")
	 @Size(min = 5, max = 15)
	 @Pattern(regexp = "[a-zA-Z0-9]+",message = "provide ifsCode")
	 private String ifsCode;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPanCardNo() {
		return panCardNo;
	}

	public void setPanCardNo(String panCardNo) {
		this.panCardNo = panCardNo;
	}

	public String getAadhaarCardNo() {
		return aadhaarCardNo;
	}

	public void setAadhaarCardNo(String aadhaarCardNo) {
		this.aadhaarCardNo = aadhaarCardNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getOpeningDeposit() {
		return openingDeposit;
	}

	public void setOpeningDeposit(String openingDeposit) {
		this.openingDeposit = openingDeposit;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getIfsCode() {
		return ifsCode;
	}

	public void setIfsCode(String ifsCode) {
		this.ifsCode = ifsCode;
	}

	

}
