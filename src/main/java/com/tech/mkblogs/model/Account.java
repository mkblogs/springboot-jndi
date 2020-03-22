package com.tech.mkblogs.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Account {
	
	private Integer id;
	private String accountName;
	private String accountType;
	private BigDecimal amount;
	private Integer createdBy;
	private String createdName;
	private LocalDateTime createdTs;
	private Integer lastModifiedBy;
	private String lastModifiedName;
	private LocalDateTime lastModifiedTs;
	private Integer version;
}
