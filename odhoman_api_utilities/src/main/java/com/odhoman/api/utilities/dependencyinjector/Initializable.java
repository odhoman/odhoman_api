package com.odhoman.api.utilities.dependencyinjector;

/**
 * 
 * Interface que indica que un objeto es inicializable
 * 
 * @author Fabian Benitez (fb70883)
 * @version 13/07/2011 
 * 
 */

public interface Initializable {
	
	/** Permite inicializar el objeto */
	
	public void init(Object...params);

}
