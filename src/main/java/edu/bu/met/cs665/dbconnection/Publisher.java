/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: Publisher.java
 * Description: This is an interface for publishers
 */

package edu.bu.met.cs665.dbconnection;

import java.util.List;

public interface Publisher {
  List<Observer> addObserver(Observer observer);
  
  List<Observer> removeObserver(Observer observer);
  
  void notifyObserver();
  
  
}
