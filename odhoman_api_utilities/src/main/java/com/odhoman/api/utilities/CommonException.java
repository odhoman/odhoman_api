/**
 * CommonException.java
 *
 * @author tecso:
 * @version  1.0
 */

package com.odhoman.api.utilities;
                                                                        
public class CommonException extends java.lang.Exception {
   public CommonException() {
     super(); 
   } 

   public CommonException(String msg) {
     super(msg);
   }

   public CommonException(Exception e) {
     super(e);
   }
}
