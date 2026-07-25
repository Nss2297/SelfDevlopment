package com.waseel.pbmnotificationservice.repository.prescriptionservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbmnotificationservice.persist.prescriptionservice.PayerNotifications;

@Repository
public interface PayerNotificationsRepository extends CrudRepository<PayerNotifications, Long> {

}
