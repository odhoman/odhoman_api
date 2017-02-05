package com.odhoman.api.utilities.security.filter;

import java.util.ArrayList;
import java.util.List;

public class InputFilterManager {
	
	List<InputFilter> filters = null;
	
	public InputFilterManager() {
		filters = new ArrayList<InputFilter>();
		filters.add(new StringInputFilter());
	}
	
	public <T>T filter(T input) {
		T filteredInput = input;
		for(InputFilter f: filters) {
			filteredInput = f.apply(filteredInput);
		}
		return filteredInput;
	}
	
	public <T>List<T> filter(List<T> inputList) {
		List<T> filteredList = new ArrayList<T>();
		for(T input : inputList) {
			filteredList.add(filter(input));
		}
		return filteredList;
	}

}
