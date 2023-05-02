/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: Manager.java
 * Description: This is Interface for DB Manager
 */

package edu.bu.met.cs665.dbconnection.manager;

import edu.bu.met.cs665.model.TableObject;
import java.net.ConnectException;

public interface Manager {
  
  void connect() throws ConnectException;
  
  void disconnect();
  
  TableObject readFromDb(String name) throws Exception;
  
  boolean writeToDb(TableObject tableObject);
  
  boolean updateDb(TableObject tableObject);
  
  boolean deleteDb(TableObject tableObject);
}
