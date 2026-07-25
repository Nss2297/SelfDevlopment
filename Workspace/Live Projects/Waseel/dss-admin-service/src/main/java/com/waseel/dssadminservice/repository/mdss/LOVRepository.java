package com.waseel.dssadminservice.repository.mdss;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.waseel.dssadminservice.persist.mdss.LOV;

@Repository
public interface LOVRepository extends JpaRepository<LOV, Long> {

	List<LOV> findByKey(String key);
}
