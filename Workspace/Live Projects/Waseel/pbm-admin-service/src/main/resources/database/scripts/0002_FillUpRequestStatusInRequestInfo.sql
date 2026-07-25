--liquibase formatted sql

--changeset PbmAdminService:0002

UPDATE "RequestInfo" r SET r."RequestStatus" =
(
   select "RequestStatus" from (
      select
     	 CASE
	         WHEN LISTAGG(y."Status",',') like  '%APPROVED%' and 
	         	  LISTAGG(y."Status",',') like  '%REJECTED%' THEN 'PARTIAL_APPROVED'
	         WHEN LISTAGG(y."Status",',') like '%APPROVED%' and 
	        	  LISTAGG(y."Status",',') Not like '%REJECTED%' THEN  'APPROVED'
			 else 'REJECTED'
     	 END "RequestStatus",y."RequestId"
      FROM "RequestInfo" x,"ServiceDecision" y
      where x."RequestId" = y."RequestId"
      group by y."RequestId"
	)b
	WHERE r."RequestId" = b."RequestId"
);