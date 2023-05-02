/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: SlaveManager.java
 * Description: This is Slave DB manager
 */

package edu.bu.met.cs665.dbconnection.manager;

import edu.bu.met.cs665.dbconnection.connection.SlaveConnection;

public class SlaveManager extends DbManager implements Manager {
  public SlaveManager() {
    connection = SlaveConnection.getInstance();
  }
}
