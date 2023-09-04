package com.easypeach.shroopadmin.modules.report.dto.response;

import java.time.LocalDateTime;

import com.easypeach.shroopadmin.modules.report.domain.Report;
import com.easypeach.shroopadmin.modules.report.domain.ReportStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReportResponse {

	private Long id;

	private Long memberId;

	private String loginId;

	private Long productId;

	private String productTitle;

	private Long categoryId;

	private String categoryName;

	private String title;

	private String content;

	private boolean isMediate;

	private ReportStatus status;

	private LocalDateTime createDate;

	public ReportResponse(Report report) {
		this.id = report.getId();
		this.memberId = report.getMember().getId();
		this.loginId = report.getMember().getLoginId();
		this.productId = report.getProduct().getId();
		this.productTitle = report.getProduct().getTitle();
		this.categoryId = report.getProduct().getCategory().getId();
		this.categoryName = report.getProduct().getCategory().getName();
		this.title = report.getTitle();
		this.content = report.getContent();
		this.isMediate = report.isMediate();
		this.status = report.getStatus();
		this.createDate = report.getCreateDate();
	}

}
