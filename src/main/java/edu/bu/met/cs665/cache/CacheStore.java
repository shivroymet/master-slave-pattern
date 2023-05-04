/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: CacheStore.java
 * Description: This class fetches data from cache
 */

package edu.bu.met.cs665.cache;

import edu.bu.met.cs665.model.CountryCode;
import edu.bu.met.cs665.model.TableObject;
import java.util.List;
import org.apache.log4j.Logger;

public class CacheStore {
  
  private static final Logger logger = Logger.getLogger(CacheStore.class);
  
  static TableObjectCache cache;
  
  private CacheStore() {
  }
  
  
  /**
   * Init cache capacity.
   *
   * @param capacity capacity
   */
  public static void initCapacity(int capacity) {
    if (null == cache) {
      cache = new TableObjectCache(capacity);
    } else {
      cache.setCapacity(capacity);
    }
  }
  
  
  /**
   * Gets tableObject from Cache.
   *
   * @param name name
   * @return
   */
  public static TableObject getTableObject(String name) {
    if (cache.contains(name)) {
      logger.info("# Cache Hit!");
      return cache.get(name);
    }
    logger.error("# Cache Miss!");
    return null;
  }
  
  /**
   * Puts data to Cache Object.
   *
   * @param tableObject table object
   * @return
   */
  public static void putTableObject(TableObject tableObject) {
    CountryCode countryCode = (CountryCode) tableObject;
    if (!cache.contains(countryCode.getName())) {
      cache.set(countryCode.getNameAbbreviation(), countryCode);
    }
  }
  
  
  /**
   * Flushes existing cached data.
   *
   * @param name name
   */
  public static void flushCache(String name) {
    if (cache.contains(name)) {
      cache.invalidate(name);
    }
  }
  
  
  /**
   * Clears all cached items.
   */
  public static void clearCache() {
    if (null != cache) {
      cache.clear();
    }
  }
  
  
  /**
   * Print Table objects.
   *
   * @return
   */
  public static String print() {
    List<TableObject> tableObjectList = cache.getCacheDataInListForm();
    StringBuilder sb = new StringBuilder();
    sb.append("\n--CACHE--\n");
    for (TableObject tableObject : tableObjectList) {
      sb.append(tableObject + "\n");
    }
    sb.append("----\n");
    return sb.toString();
  }
}
