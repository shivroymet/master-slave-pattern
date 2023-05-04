/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: MM/DD/YYYY
 * File Name: TestDatabase.java
 * Description: This is a testing class
 */

package edu.bu.met.cs665;

import edu.bu.met.cs665.cache.CacheStore;
import edu.bu.met.cs665.dao.DatabaseAccessObject;
import edu.bu.met.cs665.dbconnection.manager.MasterManager;
import edu.bu.met.cs665.dbconnection.manager.SlaveManager;
import edu.bu.met.cs665.model.CountryCode;
import edu.bu.met.cs665.model.TableObject;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestDatabase {
  
  @Before
  public void setSystemConfig() {
    PropertyConfigurator.configure("log4j.properties");
    CacheStore.initCapacity(5);
  }
  
  @Test
  public void testCacheHitInvalid() {
    CacheStore.clearCache();
    CountryCode countryCode = new CountryCode();
    CacheStore.clearCache();
    countryCode.setCountryCode("+1");
    countryCode.setName("United State of America");
    countryCode.setNameAbbreviation("US");
    DatabaseAccessObject databaseAccessObject = new DatabaseAccessObject();
    databaseAccessObject.putData(countryCode);
    TableObject tableObject = CacheStore.getTableObject("US");
    Assert.assertNull(tableObject);
  }
  
  @Test
  public void testCacheHitValid() {
    CountryCode countryCode = new CountryCode();
    countryCode.setCountryCode("+1");
    countryCode.setName("United State of America");
    countryCode.setNameAbbreviation("US");
    DatabaseAccessObject databaseAccessObject = new DatabaseAccessObject();
    databaseAccessObject.putData(countryCode);
    TableObject tableObject = databaseAccessObject.getData("US");
    TableObject tableObject2 = CacheStore.getTableObject("US");
    Assert.assertEquals(tableObject2, countryCode);
  }
  
  @Test
  public void testCacheCapacityError() {
    CacheStore.clearCache();
    CountryCode countryCode = new CountryCode();
    countryCode.setCountryCode("+1");
    countryCode.setName("United State of America");
    countryCode.setNameAbbreviation("US");
    
    CountryCode countryCode1 = new CountryCode();
    countryCode1.setCountryCode("+91");
    countryCode1.setName("India");
    countryCode1.setNameAbbreviation("IN");
    
    CountryCode countryCode2 = new CountryCode();
    countryCode2.setCountryCode("+44");
    countryCode2.setName("United Kingdom");
    countryCode2.setNameAbbreviation("UK");
    
    CountryCode countryCode3 = new CountryCode();
    countryCode3.setCountryCode("+7");
    countryCode3.setName("Russia");
    countryCode3.setNameAbbreviation("RU");
    
    CountryCode countryCode4 = new CountryCode();
    countryCode4.setCountryCode("+86");
    countryCode4.setName("China");
    countryCode4.setNameAbbreviation("CN");
    
    CountryCode countryCode5 = new CountryCode();
    countryCode5.setCountryCode("+34");
    countryCode5.setName("Spain");
    countryCode5.setNameAbbreviation("ES");
    
    DatabaseAccessObject databaseAccessObject = new DatabaseAccessObject();
    databaseAccessObject.putData(countryCode);
    databaseAccessObject.putData(countryCode1);
    databaseAccessObject.putData(countryCode2);
    databaseAccessObject.putData(countryCode3);
    databaseAccessObject.putData(countryCode4);
    databaseAccessObject.putData(countryCode5);
    TableObject tableObject = databaseAccessObject.getData("US");
    tableObject = databaseAccessObject.getData("RU");
    tableObject = databaseAccessObject.getData("ES");
    tableObject = databaseAccessObject.getData("CN");
    tableObject = databaseAccessObject.getData("UK");
    tableObject = databaseAccessObject.getData("US");
    tableObject = databaseAccessObject.getData("RU");
    tableObject = databaseAccessObject.getData("ES");
    tableObject = databaseAccessObject.getData("CN");
    tableObject = databaseAccessObject.getData("IN");
    TableObject tableObject2 = CacheStore.getTableObject("UK");
    Assert.assertNull(tableObject2);
  }
  
  @Test
  public void testCacheInvalidError() throws Exception {
    CacheStore.clearCache();
    CountryCode countryCode = new CountryCode();
    countryCode.setCountryCode("+1");
    countryCode.setName("United State of America");
    countryCode.setNameAbbreviation("US");
    DatabaseAccessObject databaseAccessObject = new DatabaseAccessObject();
    databaseAccessObject.putData(countryCode);
    TableObject tableObject = databaseAccessObject.getData("US");
    TableObject tableObject1 = CacheStore.getTableObject("US");
    Assert.assertEquals(tableObject1,countryCode);
    Thread.sleep(7000);
    TableObject tableObject2 = CacheStore.getTableObject("US");
    Assert.assertNull(tableObject2);
  }
  
  
  @Test
  public void testMasterSlaveSync() throws Exception {
    CacheStore.clearCache();
    CountryCode countryCode = new CountryCode();
    countryCode.setCountryCode("+1");
    countryCode.setName("United State of America");
    countryCode.setNameAbbreviation("US");
    DatabaseAccessObject databaseAccessObject = new DatabaseAccessObject();
    databaseAccessObject.putData(countryCode);
    MasterManager masterManager = new MasterManager();
    masterManager.connect();
    TableObject tableObject = masterManager.readFromDb("US");
    SlaveManager slaveManager = new SlaveManager();
    slaveManager.connect();
    TableObject tableObject1 = slaveManager.readFromDb("US");
    Assert.assertEquals(tableObject1, tableObject);
  }
  
  
}
