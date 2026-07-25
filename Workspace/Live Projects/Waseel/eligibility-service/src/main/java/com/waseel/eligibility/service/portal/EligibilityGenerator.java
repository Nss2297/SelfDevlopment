package com.waseel.eligibility.service.portal;

import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Value;
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
import com.waseel.eligibility.model.EligibilityRequestModel;

@Service
public class EligibilityGenerator {

	@Value("${portal.username}")
	public String username;

	@Value("${portal.password}")
	public String password;

	public TransactionWrapper generate(String idNumber, EligibilityRequestModel eligibilityRequestModel) {
		TransactionWrapper tw = new TransactionWrapper();
		tw.setCommonMessageHeader(createMessageHeader(eligibilityRequestModel));
		tw.setEligibilitySubmissionRequest(createEligibilityRequest(idNumber));
		return tw;
	}

	private MessageHeaderCT createMessageHeader(EligibilityRequestModel eligibilityRequestModel) {
		MessageHeaderCT messageHeader = new MessageHeaderCT();
		InteractionCT interaction = new InteractionCT();
		interaction.setBroadcastList(null);
		interaction.setInteractionCode(new BigInteger("101"));
		interaction.setReceiverCode(eligibilityRequestModel.getPayerId());
		interaction.setSenderCode(eligibilityRequestModel.getProviderId());
		messageHeader.setInteraction(interaction);
		TransactionCT transaction = new TransactionCT();
		transaction.setClientTransactionID("-1");
		transaction.setDirection(TransactionDirectionST.REQUEST);
		transaction.setSubType(TransactionSubTypeST.NEW);
		transaction.setTransactionID(-1L);
		transaction.setType(TransactionTypeST.VALUE_1);
		messageHeader.setTransaction(transaction);
		UserCT user = new UserCT();
		user.setLogin(username);
		user.setPassword(password);
		messageHeader.setUser(user);
		return messageHeader;
	}

	private EligibilitySubmissionRequestCT createEligibilityRequest(String idNumber) {
		EligibilitySubmissionRequestCT eligibility = new EligibilitySubmissionRequestCT();
		eligibility.setMember(getMemberInfo(idNumber));
		eligibility.setVisitInformation(getVisitInfo());
		return eligibility;
	}

	private MemberCT getMemberInfo(String idNumber) {
		MemberCT member = new MemberCT();
		member.setIDNumber(idNumber);
		member.setMemberID("");
		member.setPolicyNumber("");
		return member;
	}

	private VisitInfoCT getVisitInfo() {
		VisitInfoCT visit = new VisitInfoCT();
		visit.setDepartmentCode(DepartmentCodeST.fromValue("7"));
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar xmlDate;
		try {
			xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			visit.setVisitDate(xmlDate);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return visit;
	}
}
