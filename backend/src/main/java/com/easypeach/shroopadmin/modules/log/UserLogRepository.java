package com.easypeach.shroopadmin.modules.log;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog,Long> {
	Page<UserLog> findLogsByMemberId(Long memberId, Pageable pageable);
}