package com.easypeach.shroopadmin.modules.report.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PageMediateResponse {

	private int totalCount;

	private List<MediateResponse> responseList;

	public PageMediateResponse(int totalCount, List<MediateResponse> responseList) {
		this.totalCount = totalCount;
		this.responseList = responseList;
	}
}
