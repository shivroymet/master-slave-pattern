/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: DbManager.java
 * Description: This is a DB Manager abstract class
 */

package edu.bu.met.cs665.dbconnection.manager;

import com.google.gson.Gson;
import edu.bu.met.cs665.dbconnection.connection.Connection;
import edu.bu.met.cs665.model.CountryCode;
import edu.bu.met.cs665.model.TableObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Random;
import java.util.Scanner;
import org.apache.log4j.Logger;

public abstract class DbManager implements Manager {
  
  Logger logger = Logger.getLogger(DbManager.class);
  
  Connection connection;
  boolean isConnected;
  
  Gson gson = new Gson();
  
  /**
   * Connects to Database.
   */
  @Override
  public void connect() throws ConnectException {
    File myObj = new File(this.connection.getConnectionProperties().getFilename());
    if (myObj.isDirectory()) {
      logger.error("Directory");
      this.isConnected = false;
    } else {
      Random rn = new Random();
      Integer i = rn.nextInt(101);
      if (!myObj.exists()) {
        this.createFile(this.connection.getConnectionProperties().getFilename());
        this.isConnected = true;
      } else if (myObj.exists()) {
        if (i < 99) {  // to simulate connection failure
          this.isConnected = true;
        } else {
          this.isConnected = false;
          throw new ConnectException();
        }
      }
    }
  }
  
  @Override
  public void disconnect() {
    this.isConnected = false;
  }
  
  /**
   * Fetches data from database.
   *
   * @param name name
   * @return
   */
  @Override
  public TableObject readFromDb(String name) throws Exception {
    TableObject tableObject = null;
    if (this.isConnected && null != this.connection) {
      Scanner myReader = null;
      try {
        File myObj = new File(this.connection.getConnectionProperties().getFilename());
        myReader = new Scanner(myObj);
        if (isConnected) {
          while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (data.contains(name)) {
              tableObject = gson.fromJson(data, CountryCode.class);
              break;
            }
          }
        }
        if (tableObject == null) {
          throw new NullPointerException();
        }
      } catch (Exception e) {
        logger.error("An error occurred.");
        throw e;
      } finally {
        if (myReader != null) {
          myReader.close();
        }
      }
    }
    return tableObject;
  }
  
  /**
   * Creates DB file for storing Table Objects.
   *
   * @param name name
   */
  public void createFile(String name) {
    try {
      File myObj = new File(name);
      if (myObj.createNewFile()) {
        logger.info("File created: " + myObj.getName());
      } else {
        logger.info("File already exists.");
      }
    } catch (IOException e) {
      logger.error("An error occurred.");
      e.printStackTrace();
    }
  }
  
  @Override
  public boolean updateDb(TableObject tableObject) {
    if (isConnected) {
      try {
        Scanner sc = new Scanner(this.connection.getConnectionProperties().getFilename());
        StringBuffer buffer = new StringBuffer();
        boolean isUpdated = false;
        CountryCode countryCode = (CountryCode) tableObject;
        while (sc.hasNextLine()) {
          String line = sc.nextLine();
          
          if (!line.contains(countryCode.getName())) {
            buffer.append(line + System.lineSeparator());
          } else {
            buffer.append(gson.toJson(tableObject) + System.lineSeparator());
            logger.info("Updated Successfully");
            isUpdated = true;
          }
        }
        writeToFile(buffer);
        sc.close();
        return isUpdated;
      } catch (IOException e) {
        logger.error("An error occurred.");
        e.printStackTrace();
        return false;
      }
      
    } else {
      logger.error("No Connection found");
      return false;
    }
    
  }
  
  /**
   * Deletes data from database.
   *
   * @param tableObject table object
   * @return
   */
  @Override
  public boolean deleteDb(TableObject tableObject) {
    if (isConnected) {
      try {
        Scanner sc = new Scanner(this.connection.getConnectionProperties().getFilename());
        StringBuffer buffer = new StringBuffer();
        boolean isDeleted = false;
        CountryCode countryCode = (CountryCode) tableObject;
        while (sc.hasNextLine()) {
          String line = sc.nextLine();
          
          if (!line.contains(countryCode.getName())) {
            buffer.append(line + System.lineSeparator());
          } else {
            
            logger.info("Deleted Successfully");
            isDeleted = true;
          }
        }
        writeToFile(buffer);
        sc.close();
        return isDeleted;
      } catch (IOException e) {
        logger.error("An error occurred.");
        e.printStackTrace();
        return false;
      }
      
    } else {
      logger.error("No Connection found");
      return false;
    }
  }
  
  
  /**
   * inserts data to database.
   *
   * @param tableObject table object
   * @return
   */
  @Override
  public boolean writeToDb(TableObject tableObject) {
    if (isConnected) {
      try {
        Scanner sc = new Scanner(this.connection.getConnectionProperties().getFilename());
        boolean isPresent = false;
        CountryCode countryCode = (CountryCode) tableObject;
        while (sc.hasNextLine()) {
          String line = sc.nextLine();
          if (line.contains(countryCode.getName())) {
            isPresent = true;
          }
        }
        if (!isPresent) {
          FileWriter myWriter =
              new FileWriter(this.connection.getConnectionProperties().getFilename());
          myWriter.append(gson.toJson(tableObject));
          myWriter.close();
          logger.info("Successfully wrote to the file.");
          return true;
        } else {
          logger.info("ALready present");
          return false;
        }
      } catch (IOException e) {
        logger.error("An error occurred.");
        e.printStackTrace();
        return false;
      }
      
    } else {
      logger.error("No connection found");
      return false;
    }
  }
  
  
  /**
   * Writes data to file.
   *
   * @param buffer string buffer
   * @throws IOException
   */
  void writeToFile(StringBuffer buffer) throws IOException {
    String fileContents = buffer.toString();
    FileWriter myWriter = new FileWriter(this.connection.getConnectionProperties().getFilename());
    myWriter.write(fileContents);
    myWriter.close();
  }
  
  
}
