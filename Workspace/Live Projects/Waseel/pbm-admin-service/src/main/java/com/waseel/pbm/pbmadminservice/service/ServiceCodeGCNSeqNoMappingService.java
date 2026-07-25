package com.waseel.pbm.pbmadminservice.service;

import com.waseel.pbm.pbmadminservice.model.ServiceCodeGCNSeqNoMappingModel;
import com.waseel.pbm.pbmadminservice.model.ServiceCodeGCNSeqNoMappingRequest;
import org.springframework.data.domain.Page;

public interface ServiceCodeGCNSeqNoMappingService {

    public Page<ServiceCodeGCNSeqNoMappingModel> getServiceCodeGCNSequenceNumberMappingData(
            int pageNumber, int recordSize, int gcnSeqNumber, String serviceCode);

    public void addServiceCodeGCNSequenceNumberMappingData(
            ServiceCodeGCNSeqNoMappingRequest request);

    public void updateServiceCodeGCNSequenceNumberMappingData(
            ServiceCodeGCNSeqNoMappingRequest request, Long id);

    public void deleteServiceCodeGCNSequenceNumberMappingData(Long id);
}
