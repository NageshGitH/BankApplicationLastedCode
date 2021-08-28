package com.fundtransfer.application.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)

@Entity
@Table(name="Customer_Beneficiaries")
public class CustomerBeneficaries 
{
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="beneficiary_id")
	 private Long beneficiaryId;
	 
	 @Column(name="beneficiary_name")
	 private String beneficiaryName;
	 
	 @Column(name="beneficiary_account_no")
	 private long beneficiaryAccountNo;
	 
	 @Column(name="transfer_limit")
	 private double transferLimit;
	 
	 @Column(name="ifs_code")
	 private String ifsCode;
	 
	 @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	 @JoinColumn(name="customer_id")
	 private Customer customer;

	public Long getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(Long beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public long getBeneficiaryAccountNo() {
		return beneficiaryAccountNo;
	}

	public void setBeneficiaryAccountNo(long beneficiaryAccountNo) {
		this.beneficiaryAccountNo = beneficiaryAccountNo;
	}

	public double getTransferLimit() {
		return transferLimit;
	}

	public void setTransferLimit(double transferLimit) {
		this.transferLimit = transferLimit;
	}

	public String getIfsCode() {
		return ifsCode;
	}

	public void setIfsCode(String ifsCode) {
		this.ifsCode = ifsCode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	 
	 
	 

}
