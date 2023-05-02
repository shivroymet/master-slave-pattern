/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: SlaveManager.java
 * Description: This is Master DB manager
 */

package edu.bu.met.cs665.dbconnection.manager;

import edu.bu.met.cs665.dbconnection.SlavePublisher;
import edu.bu.met.cs665.dbconnection.connection.MasterConnection;
import edu.bu.met.cs665.model.TableObject;

public class MasterManager extends DbManager implements Manager {
  
  public MasterManager() {
    connection = MasterConnection.getInstance();
  }
  
  
  @Override
  public boolean writeToDb(TableObject tableObject) {
    boolean isAdded = super.writeToDb(tableObject);
    if (isAdded) {
      SlavePublisher.getInstance().sendUpdate(tableObject, "Insert");
    }
    return isAdded;
  }
  
  
  @Override
  public boolean updateDb(TableObject tableObject) {
    boolean isUpdated = super.updateDb(tableObject);
    if (isUpdated) {
      SlavePublisher.getInstance().sendUpdate(tableObject, "Update");
    }
    return isUpdated;
  }
  
  @Override
  public boolean deleteDb(TableObject tableObject) {
    boolean isDeleted = super.deleteDb(tableObject);
    if (isDeleted) {
      SlavePublisher.getInstance().sendUpdate(tableObject, "Delete");
    }
    return isDeleted;
  }
}
