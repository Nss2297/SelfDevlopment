package com.waseel.eligibility.service;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Service;

import com.waseel.eligibility.client.portal.model.DepartmentCodeST;
import com.waseel.eligibility.client.portal.model.EligibilitySubmissionRequestCT;
import com.waseel.eligibility.client.portal.model.InteractionCT;
import com.waseel.eligibility.client.portal.model.MemberCT;
import com.waseel.eligibility.client.portal.model.MessageHeaderCT;
import com.waseel.eligibility.client.portal.model.TransactionCT;
import com.waseel.eligibility.client.portal.model.TransactionDirectionST;
import com.waseel.eligibility.client.portal.model.TransactionSubTypeST;
import com.waseel.eligibility.client.portal.model.TransactionTypeST;
import com.waseel.eligibility.client.portal.model.TransactionWrapper;
import com.waseel.eligibility.client.portal.model.UserCT;
import com.waseel.eligibility.client.portal.model.VisitInfoCT;
import com.waseel.eligibility.client.portal.model.VisitTypeST;
import com.waseel.eligibility.entity.WslGeninfo;
import com.waseel.eligibility.model.PortalSettings;

@Service
public class EligibilityGenerator {

	public TransactionWrapper generate(WslGeninfo wslGeninfo, PortalSettings portalUser) {
		
		TransactionWrapper tw = new TransactionWrapper();
		tw.setCommonMessageHeader(createMessageHeader(wslGeninfo, portalUser));
		tw.setEligibilitySubmissionRequest(createEligibilityRequest(wslGeninfo));
		return tw;
	}

	private MessageHeaderCT createMessageHeader(WslGeninfo claim, PortalSettings portalUser) {
		MessageHeaderCT messageHeader = new MessageHeaderCT();
		InteractionCT interaction = new InteractionCT();
		interaction.setBroadcastList(null);
		interaction.setInteractionCode(new BigInteger("101"));
		interaction.setReceiverCode(claim.getPayerid());
		interaction.setSenderCode(claim.getProviderid());
		messageHeader.setInteraction(interaction);
		// DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString);
		TransactionCT transaction = new TransactionCT();
		transaction.setClientTransactionID("-1");
		transaction.setDirection(TransactionDirectionST.REQUEST);
		transaction.setSubType(TransactionSubTypeST.NEW);
		transaction.setTransactionID(-1L);
		transaction.setType(TransactionTypeST.VALUE_1);
		messageHeader.setTransaction(transaction);
		UserCT user = new UserCT();
		user.setLogin(portalUser.getUsername());
		user.setPassword(portalUser.getPassword());
		messageHeader.setUser(user);
		return messageHeader;
	}
	
	private EligibilitySubmissionRequestCT createEligibilityRequest(WslGeninfo claim) {
		EligibilitySubmissionRequestCT eligibility = new EligibilitySubmissionRequestCT();
		eligibility.setMember(getMemeberInfo(claim));
		eligibility.setVisitInformation(getVisitInfo(claim));
		return eligibility;
	}

	private MemberCT getMemeberInfo(WslGeninfo claim) {
		MemberCT member = new MemberCT();
		member.setIDNumber(claim.getIdnumber());
		member.setMemberID(claim.getMemberid());
		member.setPolicyNumber(claim.getPolicynumber());
		return member;
	}
	
	private VisitInfoCT getVisitInfo(WslGeninfo claim) {
		VisitInfoCT visit = new VisitInfoCT();
		visit.setDepartmentCode(DepartmentCodeST.fromValue(claim.getDepartmentcode()));
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(claim.getVisitdate());
		XMLGregorianCalendar claimDate;
		try {
			claimDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			visit.setVisitDate(claimDate);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		
		visit.setVisitType(VisitTypeST.fromValue(claim.getVisittype()));
		return visit;
	}
}
