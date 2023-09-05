package com.easypeach.shroopadmin.modules.report.dto.response;

import java.time.LocalDateTime;

import com.easypeach.shroopadmin.modules.report.domain.ReportImg;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReportImgResponse {

	private Long id;

	private Long reportId;

	private String imgUrl;

	private LocalDateTime createDate;

	public ReportImgResponse(ReportImg reportImg) {
		this.id = reportImg.getId();
		this.reportId = reportImg.getReport().getId();
		this.imgUrl = reportImg.getImgUrl();
		this.createDate = reportImg.getCreateDate();
	}
}
