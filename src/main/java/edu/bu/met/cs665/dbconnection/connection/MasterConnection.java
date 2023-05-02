/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: MasterConnection.java
 * Description: This is a singleton class and is an extension of Connection  for Master Db
 * Connection
 */

package edu.bu.met.cs665.dbconnection.connection;

import edu.bu.met.cs665.model.ConnectionProperties;

public class MasterConnection extends Connection {
  private static MasterConnection instance;
  
  private MasterConnection() {
    super(new ConnectionProperties("123.45.67.89", "admin", "temp123",
        "master.txt"));
  }
  
  /**
   * Returns singleton instance of Master DB.
   *
   * @return
   */
  public static MasterConnection getInstance() {
    if (instance == null) {
      instance = new MasterConnection();
    }
    return instance;
  }
  
}

