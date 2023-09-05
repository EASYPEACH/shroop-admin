package com.easypeach.shroopadmin.modules.report.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportImgRepository extends JpaRepository<ReportImg, Long> {

	List<ReportImg> findByReportId(Long reportId);

}
