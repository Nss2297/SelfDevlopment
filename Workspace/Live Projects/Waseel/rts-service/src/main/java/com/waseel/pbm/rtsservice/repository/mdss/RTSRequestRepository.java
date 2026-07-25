package com.waseel.pbm.rtsservice.repository.mdss;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.rtsservice.dto.ServiceInfoDto;
import com.waseel.pbm.rtsservice.persist.mdss.ServiceInfo;
import com.waseel.pbm.rtsservice.persist.mdss.ServiceInfoId;

@Repository
public interface RTSRequestRepository extends CrudRepository<ServiceInfo, ServiceInfoId> {

	@Query(value = "select  m.\"MemberId\" , s.\"RequestId\" as \"RequestId\" , s.\"ServiceCode\" as \"ServiceCode\",s.\"ServiceDate\" as \"ServiceDate\"\r\n"
			+ "            ,s.\"DaysOfSupply\" as \"DaysOfSupply\" from MDSS.\"ServiceInfo\" s, \r\n"
			+ "            MDSS.\"MemberInfo\" m, MDSS.\"ServiceDecision\" sd , MDSS.\"RequestInfo\" rs\r\n"
			+ "             where   m.\"RequestId\" = rs.\"RequestId\" \r\n"
			+ "             and s.\"RequestId\" = m.\"RequestId\"\r\n"
			+ "             and s.\"RequestId\" = sd.\"RequestId\"\r\n"
			+ "             and s.\"ServiceId\" = sd.\"ServiceId\"\r\n" + "             and s.\"ServiceCode\" = ?2\r\n"
			+ "             and m.\"MemberId\" = ?1\r\n" + "             and sd.\"Status\" = 'APPROVED'\r\n"
			+ "             and s.\"IsDeletedFromProvider\" = 0\r\n" + "             and s.\"RequestId\" <> ?3 \r\n"
			+ "             and rs.\"PayerId\" = ?4 \r\n"
			+ "            order by \"ServiceDate\" desc fetch  next 1 rows only", nativeQuery = true)
	public ServiceInfoDto findServiceInfoDetails(String memberId, String serviceCode, String requestId, String payerId);

}
