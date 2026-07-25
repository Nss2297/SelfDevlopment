package com.shoppingService.patterns.factorymethod;

import org.apache.commons.lang3.StringUtils;

public class PlanFactory {

		public Plan getPlan(String planType) {
			if(null == planType) {
				return null;
			}else {
				if(StringUtils.equalsIgnoreCase(planType, "DOMESTICPLAN")) {
					return new DomesticPlan();
				}
				if(StringUtils.equalsIgnoreCase(planType, "COMMERCIALPLAN")) {
					return new CommercialPlan();
				}
				if(StringUtils.equalsIgnoreCase(planType, "INSTITUTIONALPLAN")) {
					return new IndustrialPlan();
				}
			}
			return null;
	}
}
