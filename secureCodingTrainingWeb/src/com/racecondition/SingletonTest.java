package com.racecondition;

import org.apache.log4j.Logger;
//import junit.framework.Assert;
//import junit.framework.TestCase;
public class SingletonTest {
   private static ClassicSingleton sone = null, stwo = null;
   private static Logger logger = Logger.getRootLogger();
   public SingletonTest() {
      
   }
   public  static void main (String args[]) {
      System.out.println("getting singleton...");
      sone = ClassicSingleton.getInstance();
      System.out.println("...got singleton: " + sone);
      System.out.println("getting singleton...");
      stwo = ClassicSingleton.getInstance();
      System.out.println("...got singleton: " + stwo);
   }
   public void testUnique() {
      System.out.println("checking singletons for equality");
      
   }
}

