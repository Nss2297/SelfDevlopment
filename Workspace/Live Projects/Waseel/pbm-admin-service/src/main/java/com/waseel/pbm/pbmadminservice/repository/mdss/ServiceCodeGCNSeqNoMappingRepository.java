package com.waseel.pbm.pbmadminservice.repository.mdss;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.pbmadminservice.persist.mdss.ServiceCodeGCNSeqNoMapping;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ServiceCodeGCNSeqNoMappingRepository
        extends JpaRepository<ServiceCodeGCNSeqNoMapping, String> ,JpaSpecificationExecutor<ServiceCodeGCNSeqNoMapping>{

    @Query(value = "SELECT \"Id\" from \"ServiceCodeGCNSeqNoMapping\""
            + " ORDER BY \"Id\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
    Long findLatestId();

    @Query(value = "Select model from ServiceCodeGCNSeqNoMapping model where model.id = :id")
    Optional<ServiceCodeGCNSeqNoMapping> findValueById(Long id);
}
