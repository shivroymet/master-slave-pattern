/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: Connection.java
 * Description: This is an abstract class for db connections
 */

package edu.bu.met.cs665.dbconnection.connection;

import edu.bu.met.cs665.model.ConnectionProperties;

public abstract class Connection {
  
  private ConnectionProperties connectionProperties;
  
  public Connection(ConnectionProperties connectionProperties) {
    this.connectionProperties = connectionProperties;
  }
  
  public ConnectionProperties getConnectionProperties() {
    return connectionProperties;
  }
}
