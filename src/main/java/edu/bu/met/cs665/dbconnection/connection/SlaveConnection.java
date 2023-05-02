/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: MasterConnection.java
 * Description: This is a singleton class and is an extension of Connection  for Slave Db
 * Connection
 */

package edu.bu.met.cs665.dbconnection.connection;

import edu.bu.met.cs665.model.ConnectionProperties;

public class SlaveConnection extends Connection {
  private static SlaveConnection instance;
  
  private SlaveConnection() {
    super(new ConnectionProperties("123.45.67.81", "admin", "temp123",
        "slave.txt"));
  }
  
  /**
   * Return singleton instance of Slave DB.
   *
   * @return
   */
  public static SlaveConnection getInstance() {
    if (instance == null) {
      instance = new SlaveConnection();
    }
    return instance;
  }
}
