package com.waseel.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.waseel.authentication.model.portal.enity.SwitchUser;


public interface SwitchUserRepository extends CrudRepository<SwitchUser, String>{	
	
	
	Optional<SwitchUser> findById(String userName);
	
	@Query(value = "select u.\"SwitchUserId\" from \"SwitchUser\" u where " + 
			"u.\"IsEnabled\" = 1 and u.\"IsDeleted\" = 0 and " + 
			"u.\"RoleId\" = (select MAX(r.\"RoleId\") from \"RolePrivilege\" r where r.\"Source\" = :providerId AND \"Destination\" <> 101 " + 
			"AND \"TransactionId\">= 3 " + 
			"AND \"TransactionId\" < 4 " + 
			"and r.\"RoleId\" in (select \"RoleId\" from \"SwitchUser\")) " + 
			"order by u.\"LastModifiedDate\" Desc " + 
			"OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
	public String findUserNameByProviderId(String providerId);
	
}
