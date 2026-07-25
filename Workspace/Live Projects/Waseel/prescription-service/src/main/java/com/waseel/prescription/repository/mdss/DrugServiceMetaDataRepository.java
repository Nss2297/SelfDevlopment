package com.waseel.prescription.repository.mdss;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.persist.mdss.DrugServiceMetaData;

@Repository
public interface DrugServiceMetaDataRepository extends CrudRepository<DrugServiceMetaData, Long> {

    @Query(value = "select \"DrugListId\" from \"DrugServiceMetaData\" " +
            "where :date >= \"Effective_Date\" " +
            "order by \"Effective_Date\" desc " +
            "fetch next 1 rows only", nativeQuery = true)
    Optional<Long> getActiveDrugServiceList(Date date);
}
