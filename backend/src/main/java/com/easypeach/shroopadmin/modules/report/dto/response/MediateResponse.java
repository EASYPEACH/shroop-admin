package com.easypeach.shroopadmin.modules.report.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.easypeach.shroopadmin.modules.report.domain.Report;
import com.easypeach.shroopadmin.modules.report.domain.ReportStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MediateResponse {

	private Long id;

	private Long reporterId;

	private String reporterLoginId;

	private Long reporteeId;

	private String reporteeLoginId;

	private Long sellerId;

	private Long productId;

	private String productTitle;

	private Long categoryId;

	private String categoryName;

	private String title;

	private String content;

	private Boolean isMediate;

	private ReportStatus status;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createDate;

	private List<ReportImgResponse> imgList;

	public MediateResponse(final Report report, final List<ReportImgResponse> imgList) {
		this.id = report.getId();
		this.reporterId = report.getReporter().getId();
		this.reporterLoginId = report.getReporter().getLoginId();
		this.reporteeId = report.getReportee().getId();
		this.reporteeLoginId = report.getReportee().getLoginId();
		this.sellerId = report.getProduct().getSeller().getId();
		this.productId = report.getProduct().getId();
		this.productTitle = report.getProduct().getTitle();
		this.categoryId = report.getProduct().getCategory().getId();
		this.categoryName = report.getProduct().getCategory().getName();
		this.title = report.getTitle();
		this.content = report.getContent();
		this.isMediate = report.isMediate();
		this.status = report.getStatus();
		this.createDate = report.getCreateDate();
		this.imgList = imgList;
	}

	public MediateResponse(final Report report) {
		this.id = report.getId();
		this.reporterId = report.getReporter().getId();
		this.reporterLoginId = report.getReporter().getLoginId();
		this.reporteeId = report.getReportee().getId();
		this.reporteeLoginId = report.getReportee().getLoginId();
		this.sellerId = report.getProduct().getSeller().getId();
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
