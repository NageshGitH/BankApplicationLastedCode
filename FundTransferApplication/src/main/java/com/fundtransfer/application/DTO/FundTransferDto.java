package com.fundtransfer.application.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)

public class FundTransferDto 
{
	 @ApiModelProperty(value = "From AccountNo", position = 1)
	 @Size(min = 5, max = 13)
	 @NotNull(message="provide from account no ,only digits")
	 @Pattern(regexp = "[0-9]{12}",message = "provide a valid from account no") 	
	 private String fromAccountNo;
	 
	 @ApiModelProperty(value = "To AccountNo", position = 2)
	 @Size(min = 5, max = 12)
	 @NotNull(message="provide to account no ,only digits")
	 @Pattern(regexp = "[0-9]{12}",message = "provide a valid to account no") 
	 private String toAccountNo;
	 
	 @ApiModelProperty(value = "Transfer Amount", position = 3)
	 @NotNull(message="provide transfer amount")
	 @Pattern(regexp = "[0-9.#]+",message = "provide valid Transfer amount")
     private String transferAmount;
	 
	 @ApiModelProperty(value = "Remarks", position = 4)
	 private String remarks;


	public String getFromAccountNo() {
		return fromAccountNo;
	}


	public void setFromAccountNo(String fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}


	public String getToAccountNo() {
		return toAccountNo;
	}


	public void setToAccountNo(String toAccountNo) {
		this.toAccountNo = toAccountNo;
	}


	public String getTransferAmount() {
		return transferAmount;
	}


	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	 
	 
	 
}
