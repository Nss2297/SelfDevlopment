package com.waseel.pbm.pbmadminservice.repository.mdss;

import com.waseel.pbm.pbmadminservice.dto.ServiceInfoDto;
import com.waseel.pbm.pbmadminservice.persist.mdss.ServiceInfo;
import com.waseel.pbm.pbmadminservice.persist.mdss.ServiceInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceInfoRepository extends JpaRepository<ServiceInfo, ServiceInfoId> {

    @Query(value = "SELECT ri.\"PayerId\",ri.\"RequestStatus\",\n" +
            "si.\"ServiceDate\", si.\"ServiceCode\", si.\"ServiceQuantity\",\n" +
            "si.\"ServiceAmount\", si.\"DaysOfSupply\", sd.\"Status\",\n" +
            "srr.\"RejectionCode\", srr.\"RejectionReason\"\n" +
            "FROM\n" +
            "\"RequestInfo\" ri,\n" +
            "\"ServiceInfo\"  si,\n" +
            "\"ServiceDecision\"  sd,\n" +
            "\"ServiceRejectionReason\" srr\n" +
            "where ri.\"RequestId\" = si.\"RequestId\"\n" +
            "and ri.\"RequestId\" = sd.\"RequestId\"\n" +
            "and ri.\"RequestId\" = srr.\"RequestId\"\n" +
            "and si.\"ServiceId\" = sd.\"ServiceId\"\n" +
            "and si.\"ServiceId\" = srr.\"ServiceId\"\n" +
            "and ri.\"IsDeletedFromProvider\" = 0\n" +
            "and si.\"IsDeletedFromProvider\" = 0\n" +
            "and ri.\"RequestId\" = ?1", nativeQuery = true)
    public List<ServiceInfoDto> findServiceInfoDetails(String requestId);
}
