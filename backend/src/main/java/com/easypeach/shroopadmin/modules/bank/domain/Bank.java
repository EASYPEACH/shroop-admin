package com.easypeach.shroopadmin.modules.bank.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class Bank {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 30, nullable = false)
	private String name;

	@Column(length = 50, nullable = false, unique = true)
	private String account;

	@Column(length = 500, nullable = false)
	private String password;

	@ColumnDefault(value = "1000000")
	@Column(nullable = false)
	private Long money;

}
