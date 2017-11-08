package com.hkmtuning.api.items.category;

import java.io.Serializable;

/**
 * Created by Cantador on 07.11.17.
 */

public class Name implements Serializable {

  private String value;
  private String code;

  public Name() {

  }

  public Name(
      String value,
      String code
  ) {
    this.value = value;
    this.code = code;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
