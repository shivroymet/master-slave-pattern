/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: ConnectionProperties.java
 * Description: This is a model class for Connection Properties of Databases
 */

package edu.bu.met.cs665.model;

import java.util.Objects;

public class ConnectionProperties {
  private String ip;
  private String userName;
  private String password;
  private String filename;
  
  public String getIp() {
    return ip;
  }
  
  public String getUserName() {
    return userName;
  }
  
  public String getPassword() {
    return password;
  }
  
  public String getFilename() {
    return filename;
  }
  
  
  /**
   * This is a constructor.
   *
   * @param ip ip
   * @param userName userName
   * @param password password
   * @param filename filename
   */
  public ConnectionProperties(String ip, String userName, String password, String filename) {
    this.ip = ip;
    this.userName = userName;
    this.password = password;
    this.filename = filename;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConnectionProperties that = (ConnectionProperties) o;
    return Objects.equals(ip, that.ip) && Objects.equals(userName, that.userName)
           && Objects.equals(password, that.password) && Objects.equals(filename, that.filename);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(ip, userName, password, filename);
  }
  
  @Override
  public String toString() {
    return "ConnectionProperties{" + "ip='" + ip + '\'' + ", userName='" + userName + '\''
           + ", password='" + password + '\'' + ", filename='" + filename + '\'' + '}';
  }
}

