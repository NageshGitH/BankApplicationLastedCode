package com.fundtransfer.application.model;

import java.time.LocalDateTime;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="account_transactions")
public class AccountTransactions 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="transaction_id")
	private Long transactionId;
	@Column(name="transfer_amount")
	private double transferAmount;
	@Column(name="from_account")
	private long fromAccount;
	@Column(name="to_account")
	private long toAccount;
	@Column(name="transaction_time")
	private LocalDateTime transactionTime;
	@Column(name="transaction_type")
	private String transactionType;
	
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", referencedColumnName = "account_id")
	private CustomerAccount account;


	public Long getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}


	public double getTransferAmount() {
		return transferAmount;
	}


	public void setTransferAmount(double transferAmount) {
		this.transferAmount = transferAmount;
	}


	public long getFromAccount() {
		return fromAccount;
	}


	public void setFromAccount(long fromAccount) {
		this.fromAccount = fromAccount;
	}


	public long getToAccount() {
		return toAccount;
	}


	public void setToAccount(long toAccount) {
		this.toAccount = toAccount;
	}


	public LocalDateTime getTransactionTime() {
		return transactionTime;
	}


	public void setTransactionTime(LocalDateTime transactionTime) {
		this.transactionTime = transactionTime;
	}


	public String getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}


	public CustomerAccount getAccount() {
		return account;
	}


	public void setAccount(CustomerAccount account) {
		this.account = account;
	}
	
}
