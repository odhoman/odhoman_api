package com.odhoman.api.utilities.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import com.odhoman.api.utilities.config.AppConfig;
import com.odhoman.api.utilities.config.ConfigConstants;

/**
 * The <code>MailAuthenticator</code> class is used to authenticate e-mails. It is used by {@link MailMessage}
 *
 * @author Emilio M&uuml;ller (em06297)
 * @version 12/10/2010
 *
 */
public class MailAuthenticator extends Authenticator {

	private String user;
	
	private String password;
	
	public MailAuthenticator(AppConfig config) {
		user = config.getString(ConfigConstants.MAIL_USERNAME);
		password = config.getString(ConfigConstants.MAIL_PASSWORD);
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password);
	}

}
