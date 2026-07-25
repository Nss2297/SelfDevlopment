package com.waseel.drugexclusionvalidationservice;

import co.elastic.apm.attach.ElasticApmAttacher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DrugExclusionValidationServiceApplication {

    public static void main(String[] args) {
        String[] profiles = SpringApplication.run(DrugExclusionValidationServiceApplication.class, args).getEnvironment()
                .getActiveProfiles();
        for (int i = 0; i < profiles.length; i++) {
            String filename = "elasticapm-" + profiles[i] + ".properties";
            if (DrugExclusionValidationServiceApplication.class.getClassLoader().getResource(filename) == null)
                continue;
            ElasticApmAttacher.attach(filename);
        }
    }
}
