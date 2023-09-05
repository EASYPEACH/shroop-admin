package com.easypeach.shroopadmin.modules.report.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.easypeach.shroopadmin.modules.report.domain.ReportImg;
import com.easypeach.shroopadmin.modules.report.domain.ReportImgRepository;
import com.easypeach.shroopadmin.modules.report.dto.response.ReportImgResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReportImgService {

	private final ReportImgRepository reportImgRepository;

	public List<ReportImgResponse> findByReportId(final Long reportId) {
		List<ReportImg> list = reportImgRepository.findByReportId(reportId);
		return list.stream().map(ReportImgResponse::new).collect(Collectors.toList());
	}

}
