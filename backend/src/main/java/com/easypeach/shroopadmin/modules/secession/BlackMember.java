package com.easypeach.shroopadmin.modules.secession;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.easypeach.shroopadmin.modules.member.domain.Member;
import com.easypeach.shroopadmin.modules.member.domain.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BlackMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "login_id", length = 50, nullable = false, unique = true)
	private String loginId;

	@Column(nullable = false)
	private String password;

	@Column(length = 50, nullable = false, unique = true)
	private String nickname;

	@Column(name = "phone_number", length = 50, nullable = false, unique = true)
	private String phoneNumber;

	private String profileImg;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Role role;

	@Column(name = "login_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime loginDate;

	@Column(nullable = false)
	private Long point;

	@Column(name = "grade_score")
	private Long GradeScore;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDateTime createDate;

	@Column(name = "update_date")
	@LastModifiedDate
	private LocalDateTime updateDate;

	@Column
	private String account;

	public static BlackMember from(Member member){
		BlackMember blackMember = new BlackMember();
		blackMember.loginId = member.getLoginId();
		blackMember.nickname = member.getNickname();
		blackMember.account = member.getAccount();
		blackMember.password = member.getPassword();
		blackMember.phoneNumber = member.getPhoneNumber();
		blackMember.point = member.getPoint();
		blackMember.GradeScore = member.getGradeScore();
		blackMember.role = member.getRole();
		blackMember.createDate = member.getCreateDate();
		return blackMember;
	}

}
