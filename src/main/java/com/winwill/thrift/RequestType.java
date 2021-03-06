/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.winwill.thrift;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum RequestType implements TEnum {
  SAY_HELLO(0),
  QUERY_TIME(1);

  private final int value;

  private RequestType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static RequestType findByValue(int value) { 
    switch (value) {
      case 0:
        return SAY_HELLO;
      case 1:
        return QUERY_TIME;
      default:
        return null;
    }
  }
}
