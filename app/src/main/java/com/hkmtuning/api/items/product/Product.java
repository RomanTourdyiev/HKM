package com.hkmtuning.api.items.product;

import java.util.List;

/**
 * Created by imac on 11/7/17.
 */


public class Product {
  private String tatal_count;

  private String products_count;

  private List<Products> products;

  public String getTatal_count() {
    return tatal_count;
  }

  public void setTatal_count(String tatal_count) {
    this.tatal_count = tatal_count;
  }

  public String getProducts_count() {
    return products_count;
  }

  public void setProducts_count(String products_count) {
    this.products_count = products_count;
  }

  public List<Products> getProducts() {
    return products;
  }

  public void setProducts(List<Products> products) {
    this.products = products;
  }
}
