package com.easypeach.shroopadmin.modules.report.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.easypeach.shroopadmin.modules.member.domain.Member;

@Entity
public class BlackList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(name = "block_date", nullable = false)
	private LocalDate blockDate;

}
