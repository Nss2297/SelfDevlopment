package com.ezybytes.demo_spring_security.repository;

import com.ezybytes.demo_spring_security.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepository extends CrudRepository<Contact, String> {
	
	
}
