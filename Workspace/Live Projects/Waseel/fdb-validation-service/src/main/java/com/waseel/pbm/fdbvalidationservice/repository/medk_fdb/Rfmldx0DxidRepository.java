package com.waseel.pbm.fdbvalidationservice.repository.medk_fdb;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.fdbvalidationservice.dto.ContraIndication;
import com.waseel.pbm.fdbvalidationservice.dto.Indications;
import com.waseel.pbm.fdbvalidationservice.persist.medk_fdb.Rfmldx0Dxid;

public interface Rfmldx0DxidRepository extends CrudRepository<Rfmldx0Dxid, Integer> {

    @Query(value = "select distinct  x.\"GCN_SEQNO\" as \"GcnSeqno\" , a.\"DXID\" as \"dxid\",\r\n"
            + "a.\"DXID_DESC100\" as \"dxidDesc\" , \r\n"
            + "case b.\"FML_CLIN_CODE\" when '01' then 'indications' when '02' then 'side effects' when '03' then 'contras' when '04' then 'drc' when '05' then 'poem' when '06' then 'neonatal'   else '' end as \"fmlClinCode\",\r\n"
            + "\r\n"
            + "case b.\"FML_NAV_CODE\" when '01' then 'Equal' when '02' then 'Broader' when '03' then 'Narrower' when '04' then 'Related' end as \"fmlNavCode\",\r\n"
            + "\r\n" + "c.\"ICD_CD\" as \"icdCD\",\r\n" + "\r\n"
            + "case c.\"ICD_CD_TYPE\" when '01' then '9CM' when '02' then '9CM' when '03' then '9CM' when '04' then '9CM' when '05' then '10CM' when '06' then '10CM' when '07' then '10AM' end as \"icdCdType\",\r\n"
            + "\r\n" + "c.\"ICD_DESC\" as \"icdDesc\"\r\n" + "\r\n" + "from MEDK_FDB.\"RFMLDX0_DXID\" a\r\n" + "\r\n"
            + "inner join MEDK_FDB.\"RFMLISR1_ICD_SEARCH\" b on a.\"DXID\" = b.\"RELATED_DXID\"\r\n" + "\r\n"
            + "inner join MEDK_FDB.\"RFMLINM1_ICD_DESC\" c on c.\"ICD_CD\" = b.\"SEARCH_ICD_CD\" and c.\"ICD_CD_TYPE\"= b.\"ICD_CD_TYPE\"\r\n"
            + "\r\n" + "inner join MEDK_FDB.\"RINDMMA2_INDCTS_MSTR\" w on a.\"DXID\" = w.\"DXID\"\r\n" + "\r\n"
            + "inner join MEDK_FDB.\"RINDMGC0_INDCTS_GCNSEQNO_LINK\" x on w.\"INDCTS\" = x.\"INDCTS\"\r\n" + "\r\n"
            + "where b.\"FML_CLIN_CODE\" in ( '01')\r\n" + "\r\n"
            + "and x.\"GCN_SEQNO\" = :gcnsqno and c.\"ICD_CD\" LIKE :icdCode%\r\n" + "\r\n"
            + "and b.\"ICD_CD_TYPE\" = '07'\r\n" + "\r\n" + " ", nativeQuery = true)
    List<Indications> findIndicationUsingWildcardMatch(@Param("gcnsqno") Integer gcnSeqNo,
                                                                 @Param("icdCode") String diagnosisCode);

    
    
    @Query(value = "select distinct  x.\"GCN_SEQNO\" as \"GcnSeqno\" , a.\"DXID\" as \"dxid\",\r\n"
            + "a.\"DXID_DESC100\" as \"dxidDesc\" , \r\n"
            + "case b.\"FML_CLIN_CODE\" when '01' then 'indications' when '02' then 'side effects' when '03' then 'contras' when '04' then 'drc' when '05' then 'poem' when '06' then 'neonatal'   else '' end as \"fmlClinCode\",\r\n"
            + "\r\n"
            + "case b.\"FML_NAV_CODE\" when '01' then 'Equal' when '02' then 'Broader' when '03' then 'Narrower' when '04' then 'Related' end as \"fmlNavCode\",\r\n"
            + "\r\n" + "c.\"ICD_CD\" as \"icdCD\",\r\n" + "\r\n"
            + "case c.\"ICD_CD_TYPE\" when '01' then '9CM' when '02' then '9CM' when '03' then '9CM' when '04' then '9CM' when '05' then '10CM' when '06' then '10CM' when '07' then '10AM' end as \"icdCdType\",\r\n"
            + "\r\n" + "c.\"ICD_DESC\" as \"icdDesc\"\r\n" + "\r\n" + "from MEDK_FDB.\"RFMLDX0_DXID\" a\r\n" + "\r\n"
            + "inner join MEDK_FDB.\"RFMLISR1_ICD_SEARCH\" b on a.\"DXID\" = b.\"RELATED_DXID\"\r\n" + "\r\n"
            + "inner join MEDK_FDB.\"RFMLINM1_ICD_DESC\" c on c.\"ICD_CD\" = b.\"SEARCH_ICD_CD\" and c.\"ICD_CD_TYPE\"= b.\"ICD_CD_TYPE\"\r\n"
            + "\r\n" + "inner join MEDK_FDB.\"RINDMMA2_INDCTS_MSTR\" w on a.\"DXID\" = w.\"DXID\"\r\n" + "\r\n"
            + "inner join MEDK_FDB.\"RINDMGC0_INDCTS_GCNSEQNO_LINK\" x on w.\"INDCTS\" = x.\"INDCTS\"\r\n" + "\r\n"
            + "where b.\"FML_CLIN_CODE\" in ( '01')\r\n" + "\r\n"
            + "and x.\"GCN_SEQNO\" = :gcnsqno and c.\"ICD_CD\" in (:icdCodes)\r\n" + "\r\n"
            + "and b.\"ICD_CD_TYPE\" = '07'\r\n" + "\r\n" + " ", nativeQuery = true)
    List<Indications> findIndicationUsingExactMatch(@Param("gcnsqno") Integer gcnSeqNo,
                                                                 @Param("icdCodes") List<String> diagnosisCodes);

    @Query(value = "select distinct z.\"GCN_SEQNO\" as \"gcnSeqNo\" ,  a.\"DXID\" as \"dxid\", c.\"ICD_CD_TYPE\" as \"icdCd_Type\",a.\"DXID_DESC100\" as \"dxidDesc100\",\r\n" +
            "\r\n" +
            "case b.\"FML_CLIN_CODE\" when '01' then 'indications' when '02' then 'side effects' when '03' then 'contras' when '04' then 'drc' when '05' then 'poem' when '06' then 'neonatal'   else '' end as \"fmlClinCode\",\r\n" +
            "\r\n" +
            "case b.FML_NAV_CODE when '01' then 'Equal' when '02' then 'Broader' when '03' then 'Narrower' when '04' then 'Related' end as \"fmlNavCode\",\r\n" +
            "\r\n" +
            "c.\"ICD_CD\" as \"icdCd\",\r\n" +
            "\r\n" +
            "case c.\"ICD_CD_TYPE\" when '01' then '9CM' when '02' then '9CM' when '03' then '9CM' when '04' then '9CM' when '05' then '10CM' when '06' then '10CM' when '07' then '10AM' end as \"Icd_CdType\",\r\n" +
            "\r\n" +
            "c.\"ICD_DESC\" as \"icdDesc\", y.\"DDXCN_SL\" as \"ddxcnSl\",\r\n" +
            "\r\n" +
            "case when a.\"DXID_DISEASE_DURATION_CD\" = 0 then 'Not Applicable' when a.\"DXID_DISEASE_DURATION_CD\" =1 then 'Acute' when a.\"DXID_DISEASE_DURATION_CD\" =2 then 'Chronic' when a.\"DXID_DISEASE_DURATION_CD\" =3 then 'Both' else '' end as \"DXID_DISEASE_DURATION_CD\"\r\n" +
            "\r\n" +
            "from MEDK_FDB.\"RFMLDX0_DXID\" a\r\n" +
            "\r\n" +
            "inner join MEDK_FDB.\"RFMLISR1_ICD_SEARCH\" b on a.\"DXID\" = b.\"RELATED_DXID\"\r\n" +
            "\r\n" +
            "inner join MEDK_FDB.\"RFMLINM1_ICD_DESC\" c on c.\"ICD_CD\" = b.\"SEARCH_ICD_CD\" and c.\"ICD_CD_TYPE\"= b.\"ICD_CD_TYPE\"\r\n" +
            "\r\n" +
            "inner join MEDK_FDB.\"RDDCMMA1_CONTRA_MSTR\" y on a.\"DXID\" = y.\"DXID\"\r\n" +
            "\r\n" +
            "inner join MEDK_FDB.\"RDDCMGC0_CONTRA_GCNSEQNO_LINK\" z  on z.\"DDXCN\" = y.\"DDXCN\"\r\n" +
            "\r\n" +
            "where z.\"GCN_SEQNO\" = :gcnsqno and b.\"FML_CLIN_CODE\" in ( '03') and c.\"ICD_CD_TYPE\" in ('07') and c.\"ICD_CD\" in (:icdCodes) and y.\"DDXCN_SL\" = 1", nativeQuery = true)
    List<ContraIndication> findContraIndicationByGcnSeqNoAndDiagnosisCodes(@Param("gcnsqno") Integer gcnSeqNo,
                                                                           @Param("icdCodes") List<String> diagnosisCode);
    
    
}
    
    