package com.easypeach.shroopadmin.modules.member.dto.response;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLogDto {
	private Long memberId;
	private String log;
	private String paramName;
	private String paramValue;
	private LocalDateTime createdTime;
}
