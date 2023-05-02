/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: TableObjectCache.java
 * Description: This class stores TableObject Cache
 */

package edu.bu.met.cs665.cache;

import edu.bu.met.cs665.model.TableObject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class TableObjectCache {
  
  private static final Logger logger = Logger.getLogger(TableObjectCache.class);
  
  public static final Long DEFAULT_CACHE_TIMEOUT = 6000L;
  
  class Node {
    LocalDateTime createdTime;
    String name;
    TableObject tableObject;
    Node previous;
    Node next;
    
    public Node(String name, TableObject tableObject) {
      this.name = name;
      this.tableObject = tableObject;
    }
  }
  
  int capacity;
  Map<String, Node> cache = new HashMap<>();
  Node head;
  Node end;
  
  public TableObjectCache(int capacity) {
    this.capacity = capacity;
  }
  
  
  /**
   * Get CountryCode.
   *
   * @param name name
   * @return
   */
  public TableObject get(String name) {
    if (cache.containsKey(name)) {
      Node node = cache.get(name);
      if (LocalDateTime.now().isBefore(node.createdTime
          .plus(TableObjectCache.DEFAULT_CACHE_TIMEOUT, ChronoUnit.MILLIS))) {
        remove(node);
        setHead(node);
        return node.tableObject;
      } else {
        this.invalidate(name);
        cache.remove(name);
      }
    }
    return null;
  }
  
  
  /**
   * Remove node from linked list.
   *
   * @param node node
   */
  public void remove(Node node) {
    if (node.previous != null) {
      node.previous.next = node.next;
    } else {
      head = node.next;
    }
    if (node.next != null) {
      node.next.previous = node.previous;
    } else {
      end = node.previous;
    }
  }
  
  
  /**
   * Move node to the front of the list.
   *
   * @param node node
   */
  public void setHead(Node node) {
    node.next = head;
    node.previous = null;
    if (head != null) {
      head.previous = node;
    }
    head = node;
    if (end == null) {
      end = head;
    }
  }
  
  
  /**
   * Set tableObject.
   *
   * @param name        name
   * @param tableObject tableObject
   */
  public void set(String name, TableObject tableObject) {
    if (cache.containsKey(name)) {
      Node old = cache.get(name);
      old.tableObject = tableObject;
      remove(old);
      setHead(old);
    } else {
      Node newNode = new Node(name, tableObject);
      newNode.createdTime = LocalDateTime.now();
      if (cache.size() >= capacity) {
        logger.info("# Cache is FULL! Removing " + end.name + " from cache...");
        cache.remove(end.name); // remove LRU data from cache.
        remove(end);
        setHead(newNode);
      } else {
        setHead(newNode);
      }
      cache.put(name, newNode);
    }
  }
  
  /**
   * Checks if the data is present.
   *
   * @param name name
   * @return
   */
  public boolean contains(String name) {
    return cache.containsKey(name);
  }
  
  
  /**
   * Invalidate cache.
   *
   * @param name name
   */
  public void invalidate(String name) {
    System.out.println("# " + name + " has been updated! Removing older version from cache...");
    Node toBeRemoved = cache.get(name);
    remove(toBeRemoved);
    cache.remove(name);
  }
  
  /**
   * Checks if the cache list is full.
   *
   * @return
   */
  public boolean isFull() {
    return cache.size() >= capacity;
  }
  
  /**
   * Returns least read data.
   *
   * @return
   */
  public TableObject getLeastReadData() {
    return end.tableObject;
  }
  
  
  /**
   * Clear cache.
   */
  public void clear() {
    head = null;
    end = null;
    cache.clear();
  }
  
  /**
   * Returns cache data in list form.
   *
   * @return
   */
  public List<TableObject> getCacheDataInListForm() {
    ArrayList<TableObject> listOfCacheData = new ArrayList<>();
    Node temp = head;
    while (temp != null) {
      listOfCacheData.add(temp.tableObject);
      temp = temp.next;
    }
    return listOfCacheData;
  }
  
  /**
   * Set cache capacity.
   *
   * @param newCapacity new Capacity of Cache Object
   */
  public void setCapacity(int newCapacity) {
    if (capacity > newCapacity) {
      clear();
    } else {
      this.capacity = newCapacity;
    }
  }
}
