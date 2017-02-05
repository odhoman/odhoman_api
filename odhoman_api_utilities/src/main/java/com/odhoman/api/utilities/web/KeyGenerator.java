package com.odhoman.api.utilities.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.odhoman.api.utilities.UserSession;

public class KeyGenerator implements Serializable {

	private static final long serialVersionUID = 6312603814099693109L;

	private static KeyGenerator keyGenerator = new KeyGenerator();
	
	public static final String FORM_KEY = "form_key";
	
	private KeyGenerator() {		
	}
	
	public static KeyGenerator getInstance() {
		return keyGenerator;
	}
	
	private String generateKey() {	
		return UUID.randomUUID().toString();
	}
	
	public synchronized String getFormKey(UserSession us) {
		String key = generateKey();
		
		if(!us.contains(FORM_KEY)) {
			us.put(FORM_KEY, new ArrayList<String>());			
		}
		List<String> formKeys = us.get(FORM_KEY);		
		formKeys.add(key);
		
		return "<input type=\"hidden\" name=\"form_key\" id=\"form_key\" value=\"" + key + "\"/>";
	}
	
	public synchronized boolean validateFormKey(UserSession us, String key, boolean removeKey) {
		if(!us.contains(FORM_KEY))
			return false;
		
		List<String> formKeys = us.get(FORM_KEY);
		if(!formKeys.contains(key))
			return false;
		
		if (removeKey)
			formKeys.remove(key);
		return true;
	}
}
