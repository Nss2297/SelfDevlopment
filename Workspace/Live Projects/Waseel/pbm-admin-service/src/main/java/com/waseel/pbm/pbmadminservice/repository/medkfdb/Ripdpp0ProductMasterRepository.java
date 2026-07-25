package com.waseel.pbm.pbmadminservice.repository.medkfdb;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.pbmadminservice.persist.medkfdb.Ripdpp0ProductMaster;


public interface Ripdpp0ProductMasterRepository extends CrudRepository<Ripdpp0ProductMaster, Integer> {


    @Query("select distinct model.gcnSeqno from Ripdpp0ProductMaster model where model.gcnSeqno = :gcnSeqNo")
    Optional<Ripdpp0ProductMaster> findByGcnSeqNo(@Param("gcnSeqNo") Integer gcnSeqNo);
}