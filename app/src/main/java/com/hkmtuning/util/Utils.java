package com.hkmtuning.util;

import android.content.Context;

import com.hkmtuning.api.items.product.Products;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cantador on 07.11.17.
 */

public class Utils {

  private Context context;
  private DB snappydb;

  public Utils(Context context) {
    this.context = context;
  }

  public void storeInDB(List list, String key) {
    try {
      snappydb = DBFactory.open(context);
      snappydb.put(key, list);
      snappydb.close();
    } catch (SnappydbException e) {

    }
  }

  public List getFromDB(String key) {
    List list = new ArrayList();
    try {
      snappydb = DBFactory.open(context);
      list = snappydb.get(key, ArrayList.class);
      snappydb.close();
    } catch (SnappydbException e) {

    }
    return list;
  }

  public List getFromDB(String key, String category) {

    List<Products> list = new ArrayList();
    List<Products> listByCategory = new ArrayList();

    try {
      snappydb = DBFactory.open(context);
      list = snappydb.get(key, ArrayList.class);

      for (int i = 0; i < list.size(); i++) {
        Products product = list.get(i);
        for (int j = 0; j < product.getCategory().size(); j++) {
          if (product.getCategory().get(j).getCat().equals(category)){
            listByCategory.add(product);
            break;
          }
        }
      }

      snappydb.close();
    } catch (SnappydbException e) {

    }
    return listByCategory;
  }
}
