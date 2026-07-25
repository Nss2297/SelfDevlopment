package com.waseel.pbm.idfvalidationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.pbm.idfvalidationservice.model.DssRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Service
public class MapperService {

    public DssRequest mapRTSRequest(ContentCachingRequestWrapper request) {
        ObjectMapper mapper = new ObjectMapper();
        DssRequest dssReq = null;
        try {
            dssReq = mapper.readValue(new String(request.getContentAsByteArray()), DssRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return dssReq;
    }
}
