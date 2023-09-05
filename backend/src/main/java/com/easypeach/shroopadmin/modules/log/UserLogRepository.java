package com.easypeach.shroopadmin.modules.log;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog,Long> {
	List<UserLog> findLogsByMemberId(Long memberId);
}