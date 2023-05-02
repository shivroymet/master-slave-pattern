/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: SlavePublisher.java
 * Description: This class publisher any changes in Master DB to Slave DB
 */

package edu.bu.met.cs665.dbconnection;

import edu.bu.met.cs665.model.TableObject;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class SlavePublisher implements Publisher {
  
  Logger logger = Logger.getLogger(SlavePublisher.class);
  private static SlavePublisher instance;
  private List<Observer> observers;
  
  private String operation;
  
  private TableObject tableObject;
  
  private SlavePublisher() {
    observers = new ArrayList<>();
    observers.add(SlaveObserver.getInstance());
  }
  
  public List<Observer> getObservers() {
    return observers;
  }
  
  public String getOperation() {
    return operation;
  }
  
  /**
   * Returns singleton instance.
   *
   * @return
   */
  public static SlavePublisher getInstance() {
    if (instance == null) {
      instance = new SlavePublisher();
    }
    return instance;
  }
  
  public TableObject getTableObject() {
    return tableObject;
  }
  
  
  /**
   * This method is used to add observer.
   *
   * @param observer observer
   * @return
   */
  public List<Observer> addObserver(Observer observer) {
    observers.add(observer);
    return observers;
  }
  
  /**
   * This method is used to remove observer.
   *
   * @param observer observer
   * @return
   */
  public List<Observer> removeObserver(Observer observer) {
    observers.remove(observer);
    return observers;
  }
  
  /**
   * This method receives updates and forwards it for notification.
   *
   * @param tableObject table object
   * @param operation   database operation
   */
  public void sendUpdate(TableObject tableObject, String operation) {
    logger.info("Master Db Changes detected");
    this.tableObject = tableObject;
    this.operation = operation;
    this.notifyObserver();
  }
  
  /**
   * Sends notification to slave.
   */
  public void notifyObserver() {
    logger.info("Notifying Slave");
    for (Observer observer : observers) {
      SlaveObserver slaveObserver = (SlaveObserver) observer;
      slaveObserver.notifySelf(this);
    }
  }
  
}

