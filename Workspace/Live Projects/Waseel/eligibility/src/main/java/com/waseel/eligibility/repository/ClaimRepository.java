package com.waseel.eligibility.repository;

import org.springframework.data.repository.CrudRepository;

import com.waseel.eligibility.entity.WslGeninfo;


public interface ClaimRepository extends CrudRepository<WslGeninfo, Long>  {

}
