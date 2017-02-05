package com.odhoman.api.utilities.security.filter;

import java.util.ArrayList;
import java.util.List;

public class StringInputFilter implements InputFilter {

	List<String> blackList = null;
	
	public StringInputFilter() {
		blackList = new ArrayList<String>();
		blackList.add("<script>");
		blackList.add("</script>");
		blackList.add("&&");
		blackList.add("||");
		blackList.add(".js");
		blackList.add("javascript");
		blackList.add("\u00A0");	//Es el caracter &nbsp; de HTML
	}

	@SuppressWarnings("unchecked")
	public <T>T apply(T input) {
		
		if(input == null || !(input instanceof String))
			return input;
		
		//A esta altura se que T es String
		String str = ((String) input).trim();
		if("".equals(str)) return (T) "";
		
		for(String s: blackList) {
			if(str.contains(s))
				str = str.replaceAll(s, "").trim();
		}		
		return (T) str.trim();
	}

}
