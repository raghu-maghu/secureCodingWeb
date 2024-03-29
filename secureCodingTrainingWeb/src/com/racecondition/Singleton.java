package com.racecondition;

import org.apache.log4j.Logger;
public class Singleton {
  private static Singleton singleton = null;
  private static Logger logger = Logger.getRootLogger();
  private static boolean firstThread = true;
  protected Singleton() {
    // Exists only to defeat instantiation.
  }
  public static Singleton getInstance() {
	  if(singleton == null) {
		     synchronized(Singleton.class) {
		       if(singleton == null) {
		         singleton = new Singleton();
		       }
		    }
	  }
     logger.info("created singleton: " + singleton);
     return singleton;
  }
  private static void simulateRandomActivity() {
     try {
        if(firstThread) {
           firstThread = false;
           logger.info("sleeping...");
           // This nap should give the second thread enough time
           // to get by the first thread.
             Thread.currentThread().sleep(50);
       }
     }
     catch(InterruptedException ex) {
        logger.warn("Sleep interrupted");
     }
  }
}

