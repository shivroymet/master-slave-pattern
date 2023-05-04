/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: SlaveObserver.java
 * Description: This class receives Master Db changes and updates Slave DB
 */

package edu.bu.met.cs665.dbconnection;

import edu.bu.met.cs665.dbconnection.manager.SlaveManager;
import org.apache.log4j.Logger;

public class SlaveObserver implements Observer {
  
  private final Logger logger = Logger.getLogger(SlaveObserver.class);
  private static SlaveObserver instance;
  
  private SlaveObserver() {
  }
  
  /**
   * Return Singleton instance of SlaveObserver.
   *
   * @return
   */
  public static SlaveObserver getInstance() {
    if (instance == null) {
      instance = new SlaveObserver();
    }
    return instance;
  }
  
  SlavePublisher publisher;
  
  @Override
  public void notifySelf(Publisher publisher) {
    logger.info("Notification received");
    this.publisher = (SlavePublisher) publisher;
    SlaveManager slaveManager = new SlaveManager();
    try {
      slaveManager.connect();
      switch (this.publisher.getOperation()) {
        case "Update":
          slaveManager.updateDb(this.publisher.getTableObject());
          break;
        case "Insert":
          slaveManager.writeToDb(this.publisher.getTableObject());
          break;
        case "Delete":
          slaveManager.deleteDb(this.publisher.getTableObject());
          break;
        default:
          break;
      }
    } catch (Exception e) {
      logger.error(e);
    }
    
  }
}
