package com.waseel.prescription.util;

import com.waseel.prescription.model.enums.DomainName;
import com.waseel.prescription.model.enums.SourceType;

public class SourceTypeUtil {

	private SourceTypeUtil() {
		super();
	}

	public static String getSourceTypeBasedOnHeaderOrigin(String headerOrigin) {
		return headerOrigin != null && headerOrigin.contains(DomainName.WASEEL.value()) ? SourceType.PBM_GUI.value()
				: SourceType.INTEGRATION.value();
	}
}
