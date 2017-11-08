package com.hkmtuning.api.items.category;

import java.io.Serializable;

/**
 * Created by Cantador on 07.11.17.
 */

public class Category implements Serializable{

  private String id;
  private int parent_id;
  private int products;
  private String translate_es;
  private String translate_fr;
  private String translate_en;
  private String translate_it;
  private String translate_de;
  private Name[] name;

  public Category() {

  }

  public Category(
      String id,
      int parent_id,
      int products,
      String translate_es,
      String translate_fr,
      String translate_en,
      String translate_it,
      String translate_de,
      Name[] name
  ) {
    this.id = id;
    this.parent_id = parent_id;
    this.products = products;
    this.translate_es = translate_es;
    this.translate_fr = translate_fr;
    this.translate_en = translate_en;
    this.translate_it = translate_it;
    this.translate_de = translate_de;
    this.name = name;
  }

  public String getTranslate_es() {
    return translate_es;
  }

  public void setTranslate_es(String translate_es) {
    this.translate_es = translate_es;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTranslate_fr() {
    return translate_fr;
  }

  public void setTranslate_fr(String translate_fr) {
    this.translate_fr = translate_fr;
  }

  public String getTranslate_en() {
    return translate_en;
  }

  public void setTranslate_en(String translate_en) {
    this.translate_en = translate_en;
  }

  public String getTranslate_it() {
    return translate_it;
  }

  public void setTranslate_it(String translate_it) {
    this.translate_it = translate_it;
  }

  public int getProducts() {
    return products;
  }

  public void setProducts(int products) {
    this.products = products;
  }

  public String getTranslate_de() {
    return translate_de;
  }

  public void setTranslate_de(String translate_de) {
    this.translate_de = translate_de;
  }

  public int getParent_id() {
    return parent_id;
  }

  public void setParent_id(int parent_id) {
    this.parent_id = parent_id;
  }

  public Name[] getName() {
    return name;
  }

  public void setName(Name[] name) {
    this.name = name;
  }

}


