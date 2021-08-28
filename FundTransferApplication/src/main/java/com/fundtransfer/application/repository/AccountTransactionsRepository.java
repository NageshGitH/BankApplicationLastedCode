package com.fundtransfer.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundtransfer.application.model.AccountTransactions;

public interface AccountTransactionsRepository extends JpaRepository<AccountTransactions, Long>
{

}
