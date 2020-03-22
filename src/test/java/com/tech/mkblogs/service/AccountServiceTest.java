package com.tech.mkblogs.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tech.mkblogs.model.Account;

import lombok.extern.log4j.Log4j2;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log4j2
public class AccountServiceTest {

	@BeforeAll
	public static void beforeEachTest() throws Exception {
		log.info("==================================================================================");
		log.info("This is executed before each Test");
	}

	@AfterAll
	public static void afterEachTest() {		
		log.info("This is exceuted after each Test");
		log.info("==================================================================================");
	}
	
	@Test
	public void saveAccount(@Autowired AccountService service) throws Exception {
		Account account = new Account();
		account.setAccountName("test");
		account.setAccountType("saving");
		account.setAmount(new BigDecimal(300));
		account.setCreatedBy(1);
		account.setCreatedName("Test");
		service.saveAccount(account);
		log.info(account.toString());
		
	}
	
	@Test
	public void getAccountData(@Autowired AccountService service) throws Exception{
		Account account = new Account();
		account.setAccountName("test");
		account.setAccountType("saving");
		account.setAmount(new BigDecimal(300));
		account.setCreatedBy(1);
		account.setCreatedName("Test"); 
		account = service.saveAccount(account);
		account = service.getAccount(account.getId());
		log.info(account.toString());
	}
	
	@Test
	public void getAllAccountData(@Autowired AccountService service) throws Exception{
		List<Account> list = new ArrayList<Account>();
		list = service.getAllData();
		for(Account account:list)
			log.info(account.toString());
	}
}
