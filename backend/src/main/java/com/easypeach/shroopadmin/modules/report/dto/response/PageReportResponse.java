package com.easypeach.shroopadmin.modules.report.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PageReportResponse {

	private int totalCount;

	private List<ReportResponse> responseList;

	public PageReportResponse(int totalCount, List<ReportResponse> responseList) {
		this.totalCount = totalCount;
		this.responseList = responseList;
	}
}


