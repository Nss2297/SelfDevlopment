package com.waseel.emailservice.service;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import com.waseel.emailservice.model.EmailDetails;

@Service
public class EmailService {

	private final Logger log = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.sender}")
	private String sender;

	public void sendEmail(EmailDetails details) {
		String serviceName = getServiceNameFromToken();
		log.info("Preparing an email for the service '{}' with subject '{}' to be sent to {}", serviceName,
				details.getSubject(), details.getRecipients());
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setFrom(sender, details.getSenderName());
			helper.setTo(details.getRecipients().stream().toArray(String[]::new));
			helper.setText(details.getMsgBody(), details.isHtml());
			helper.setSubject(details.getSubject());
			javaMailSender.send(mimeMessage);
			log.info("Email send successfully to recipients {}", details.getRecipients());
		} catch (Exception e) {
			log.error(
					"Exception occur while sending mail to recipients {} from the service '{}' with subject '{}'. Error: {}",
					details.getRecipients(), serviceName, details.getSubject(), e.toString());
		}
	}

	private String getServiceNameFromToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2IntrospectionAuthenticatedPrincipal principal = (OAuth2IntrospectionAuthenticatedPrincipal) authentication
				.getPrincipal();
		return (String) principal.getAttributes().get("client_id");
	}
}
