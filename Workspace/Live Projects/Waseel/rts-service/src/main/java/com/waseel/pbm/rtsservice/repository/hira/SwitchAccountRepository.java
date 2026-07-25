package com.waseel.pbm.rtsservice.repository.hira;

import java.math.BigDecimal;

import org.springframework.data.repository.CrudRepository;

import com.waseel.pbm.rtsservice.persist.hira.SwitchAccount;


public interface SwitchAccountRepository  extends CrudRepository<SwitchAccount, BigDecimal> {

}
