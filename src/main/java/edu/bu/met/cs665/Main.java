/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: Main.java
 * Description: This class acts as a client
 */

package edu.bu.met.cs665;


import edu.bu.met.cs665.cache.CacheStore;
import edu.bu.met.cs665.dao.DatabaseAccessObject;
import edu.bu.met.cs665.model.CountryCode;
import edu.bu.met.cs665.model.TableObject;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * This is the Main class.
 */
public class Main {
  
  private static final Logger logger = Logger.getLogger(Main.class);
  
  /**
   * A main method to run examples.
   * You may use this method for development purposes as you start building your
   * assignments/final project.  This could prove convenient to test as you are developing.
   * However, please note that every assignment/final projects requires JUnit tests.
   */
  public static void main(String[] args) {
    PropertyConfigurator.configure("log4j.properties");
    logger.info("=======Executing Main=========");
    CacheStore.initCapacity(5);
    CountryCode countryCode = new CountryCode();
    countryCode.setCountryCode("+1");
    countryCode.setName("United State of America");
    countryCode.setNameAbbreviation("US");
    DatabaseAccessObject databaseAccessObject = new DatabaseAccessObject();
    databaseAccessObject.putData(countryCode);
    TableObject tableObject = databaseAccessObject.getData("US");
    logger.info(tableObject);
    TableObject tableObject2 = databaseAccessObject.getData("US");
    logger.info(tableObject2);
    
    
  }
  
  
}
