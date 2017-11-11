package com.hkmtuning.api.items.attr;

/**
 * Created by imac on 11/11/17.
 */

public class Items {
  private Representative[] representative;

  private Billing[] billing;

  private Shipping[] shipping;

  private String products_count;

  private Color[] color;

  private Currency[] currency;

  private Prd_brand[] prd_brand;

  private Size[] size;

  public Representative[] getRepresentative() {
    return representative;
  }

  public void setRepresentative(Representative[] representative) {
    this.representative = representative;
  }

  public Billing[] getBilling() {
    return billing;
  }

  public void setBilling(Billing[] billing) {
    this.billing = billing;
  }

  public Shipping[] getShipping() {
    return shipping;
  }

  public void setShipping(Shipping[] shipping) {
    this.shipping = shipping;
  }

  public String getProducts_count() {
    return products_count;
  }

  public void setProducts_count(String products_count) {
    this.products_count = products_count;
  }

  public Color[] getColor() {
    return color;
  }

  public void setColor(Color[] color) {
    this.color = color;
  }

  public Currency[] getCurrency() {
    return currency;
  }

  public void setCurrency(Currency[] currency) {
    this.currency = currency;
  }

  public Prd_brand[] getPrd_brand() {
    return prd_brand;
  }

  public void setPrd_brand(Prd_brand[] prd_brand) {
    this.prd_brand = prd_brand;
  }

  public Size[] getSize() {
    return size;
  }

  public void setSize(Size[] size) {
    this.size = size;
  }
}