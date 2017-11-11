package com.hkmtuning.api.items.attr;

import java.util.List;

/**
 * Created by imac on 11/11/17.
 */

public class Attr {

  private List<Items> items;

  private String totalRecords;

  public List<Items> getItems() {
    return items;
  }

  public void setItems(List<Items> items) {
    this.items = items;
  }

  public String getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords(String totalRecords) {
    this.totalRecords = totalRecords;
  }
}
