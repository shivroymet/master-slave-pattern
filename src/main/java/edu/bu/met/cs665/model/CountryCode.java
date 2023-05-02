/**
 * Name: SHIVANI ROY
 * Course: CS-665 Software Designs & Patterns
 * Date: 05/01/2023
 * File Name: CountryCode.java
 * Description: This is a model class for CountryCodes and is an extension of Table Objects
 */

package edu.bu.met.cs665.model;

import java.util.Objects;

public class CountryCode extends TableObject {
  
  private String name;
  
  private String countryCode;
  
  private String nameAbbreviation;
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getCountryCode() {
    return countryCode;
  }
  
  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }
  
  public String getNameAbbreviation() {
    return nameAbbreviation;
  }
  
  public void setNameAbbreviation(String nameAbbreviation) {
    this.nameAbbreviation = nameAbbreviation;
  }
  
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CountryCode that = (CountryCode) o;
    return Objects.equals(name, that.name) && Objects.equals(countryCode, that.countryCode)
           && Objects.equals(nameAbbreviation, that.nameAbbreviation);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(name, countryCode, nameAbbreviation);
  }
  
  @Override
  public String toString() {
    return "CountryCode{" + "name='" + name + '\'' + ", countryCode='" + countryCode + '\''
           + ", nameAbbreviation='" + nameAbbreviation + '\'' + '}';
  }
}
