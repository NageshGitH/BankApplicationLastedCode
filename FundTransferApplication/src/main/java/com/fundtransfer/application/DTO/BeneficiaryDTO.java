package com.fundtransfer.application.DTO;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class BeneficiaryDTO implements Serializable
{
	 private static final long serialVersionUID = 354675822400537614L;
	 @NotNull(message = "{beneficiary.name.not.blank}")
	 @ApiModelProperty(value = "Beneficiary Name", position = 1)
	 @Size(min = 5, max = 50) 
	 @Pattern(regexp = "^[a-zA-Z0-9_ ]*$",message = "{beneficiary.name.invalid}")
	 private String beneficiaryName;
	 
	 @ApiModelProperty(value = "Beneficiary AccountNo", position = 2)
	 @Size(min = 5, max = 12)
	 @NotNull(message="provide account no ,only digits")
	 @Pattern(regexp = "[0-9]{12}",message = "provide a valid account no") 
	 private String beneficiaryAccountNo;
	 
	 @ApiModelProperty(value = "Transfer Limit", position = 3)
	 @NotNull(message="provide transfer limit ,only digits")
	 @Size(min = 3, max = 7,message = "maximum transfer limit allowed is 10 lakhs ")
	 @Pattern(regexp = "[0-9.#]+",message = "provide valid Transfer Limit amount")
	 private String transferLimit;
	 
	 @ApiModelProperty(value = "IFSCode", position = 4)
	 @NotEmpty(message="provide IFSC Code")
	 @Size(min = 5, max = 15) 
	 @Pattern(regexp = "[a-zA-Z0-9]+",message = "provide valid ifsCode")
	 private String ifsCode;

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBeneficiaryAccountNo() {
		return beneficiaryAccountNo;
	}

	public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
		this.beneficiaryAccountNo = beneficiaryAccountNo;
	}

	public String getTransferLimit() {
		return transferLimit;
	}

	public void setTransferLimit(String transferLimit) {
		this.transferLimit = transferLimit;
	}

	public String getIfsCode() {
		return ifsCode;
	}

	public void setIfsCode(String ifsCode) {
		this.ifsCode = ifsCode;
	}
	 
}
	 
	