/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: DatabaseAccessObject.java
 * Description: This is DatabaseAccessObject for accessing data in DB.
 */

package edu.bu.met.cs665.dao;

import edu.bu.met.cs665.cache.CacheStore;
import edu.bu.met.cs665.dbconnection.manager.Manager;
import edu.bu.met.cs665.dbconnection.manager.MasterManager;
import edu.bu.met.cs665.dbconnection.manager.SlaveManager;
import edu.bu.met.cs665.model.TableObject;
import org.apache.log4j.Logger;

public class DatabaseAccessObject {
  
  Logger logger = Logger.getLogger(DatabaseAccessObject.class);
  
  Manager manager;
  
  
  /**
   * Gets Table Object for given name.
   *
   * @param name name
   * @return
   */
  public TableObject getData(String name) {
    TableObject tableObject = CacheStore.getTableObject(name);
    if (tableObject == null) {
      try {
        manager = new SlaveManager();
        this.manager.connect();
        tableObject = this.manager.readFromDb(name);
      } catch (Exception e) {
        this.manager.disconnect();
        try {
          logger.info("Error Occurred. Connection to Master");
          this.manager = new MasterManager();
          this.manager.connect();
          tableObject = this.manager.readFromDb(name);
        } catch (Exception f) {
          logger.error("Error Occurred while fetching data");
          tableObject = null;
        }
      } finally {
        if (tableObject != null) {
          CacheStore.putTableObject(tableObject);
        }
        this.manager.disconnect();
      }
    }
    return tableObject;
  }
  
  /**
   * Inserts Table Object Data to DB.
   *
   * @param tableObject tableObject
   * @return
   */
  public boolean putData(TableObject tableObject) {
    boolean isInserted = false;
    try {
      this.manager = new MasterManager();
      this.manager.connect();
      isInserted = this.manager.writeToDb(tableObject);
    } catch (Exception e) {
      logger.error("Connection Failed");
    } finally {
      this.manager.disconnect();
      return isInserted;
    }
  }
  
  /**
   * Updates Table Object Data in DB.
   *
   * @param tableObject table Object
   * @return
   */
  public boolean updateData(TableObject tableObject) {
    boolean isUpdated = false;
    try {
      this.manager = new MasterManager();
      this.manager.connect();
      isUpdated = this.manager.updateDb(tableObject);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.manager.disconnect();
      return isUpdated;
    }
  }
  
  /**
   * Deletes Table Object in DB.
   *
   * @param tableObject table Object
   * @return
   */
  public boolean deleteData(TableObject tableObject) {
    boolean isDeleted = false;
    try {
      this.manager = new MasterManager();
      this.manager.connect();
      isDeleted = this.manager.deleteDb(tableObject);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.manager.disconnect();
      return isDeleted;
    }
  }
  
  
}
