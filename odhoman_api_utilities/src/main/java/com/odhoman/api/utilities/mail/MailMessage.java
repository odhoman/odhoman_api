package com.odhoman.api.utilities.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.odhoman.api.utilities.CmIOFormat;
import com.odhoman.api.utilities.config.AppConfig;
import com.odhoman.api.utilities.config.ConfigConstants;

/**
 * The <code>MailMessage</code> class represents an e-mail message. It is a convenience wrapper of JavaMail's API.
 * 
 * @author Emilio M&uuml;ller (em06297)
 * @version 11/10/2010
 * @see http://java.sun.com/products/javamail/javadocs/index.html
 */
public class MailMessage {

	private String from;

	private String to;

	private String cc;

	private String bcc;

	private String subject;

	private String body;

	/**
	 * Creates a new e-mail message
	 * 
	 * @param from
	 *            Address of the sender
	 * @param to
	 *            Addresses of the receivers
	 * @param subject
	 *            Title of this message
	 * @param body
	 *            Contents of message
	 */
	public MailMessage(String from, String to, String subject, String body) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.body = body;
	}

	/**
	 * Creates a new e-mail message
	 * 
	 * @param from
	 *            Address of the sender
	 * @param to
	 *            Addresses of the receivers. Can be empty or <code>null</code> if <code>cc</code> is specified
	 * @param cc
	 *            Addresses of carbon copy receivers. Can be empty or <code>null</code> if <code>to</code> is specified
	 * @param subject
	 *            Title of this message
	 * @param body
	 *            Contents of message
	 */
	public MailMessage(String from, String to, String cc, String subject, String body) {
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.subject = subject;
		this.body = body;
	}

	/**
	 * Creates a new e-mail message
	 * 
	 * @param from
	 *            Address of the sender
	 * @param to
	 *            Addresses of the receivers. Can be empty or <code>null</code> if <code>cc</code> or <code>bcc</code>
	 *            is specified
	 * @param cc
	 *            Addresses of carbon copy receivers. Can be empty or <code>null</code> if <code>to</code> or
	 *            <code>bcc</code> is specified
	 * @param bcc
	 *            Addresses of blind carbon copy receivers. Can be empty or <code>null</code> if <code>to</code> or
	 *            <code>cc</code> is specified.
	 * @param subject
	 *            Title of this message
	 * @param body
	 *            Contents of message
	 */
	public MailMessage(String from, String to, String cc, String bcc, String subject, String body) {
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.subject = subject;
		this.body = body;
	}

	/**
	 * Sends this e-mail message
	 * 
	 * @throws MessagingException
	 *             If any error occur.
	 */
	public void send() throws MessagingException {

		MimeMessage message = buildMimeMessage(AppConfig.getInstance());

		message.setFrom(new InternetAddress(from));

		if (isValidString(to)) {
			message.addRecipients(RecipientType.TO, to);
		}

		if (isValidString(cc)) {
			message.addRecipients(RecipientType.CC, cc);
		}

		if (isValidString(bcc)) {
			message.addRecipients(RecipientType.BCC, bcc);
		}

		message.setSentDate(new Date());
		message.setSubject(subject);
		message.setText(body);

		Transport.send(message);
	}

	private boolean isValidString(String s) {
		return s != null && !"".equals(s);
	}

	private MimeMessage buildMimeMessage(AppConfig config) throws MessagingException {

		Properties props = new Properties();
		
		String host = config.getString(ConfigConstants.MAIL_HOST);
		
		if (!isValidString(host)) {
			throw new MessagingException("Invalid mail host! Check configuration properties");
		}
		
		Long portNumber = CmIOFormat.getLong(config.getString(ConfigConstants.MAIL_HOST_PORT));
		
		if (portNumber != 0){
			props.put(ConfigConstants.MAIL_HOST_PORT, portNumber.toString());
		}
		
		props.put(ConfigConstants.MAIL_HOST, host);

		Boolean authorize = Boolean.parseBoolean(config.getString(ConfigConstants.MAIL_AUTH));
		props.put(ConfigConstants.MAIL_AUTH, authorize.toString());

		MimeMessage message;

		if (authorize) {
			message = new MimeMessage(Session.getInstance(props, new MailAuthenticator(config)));
		} else {
			message = new MimeMessage(Session.getInstance(props));
		}

		return message;
	}
}
