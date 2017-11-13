package com.hkmtuning.util;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;

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
  List<Products> list = new ArrayList();
  List<Products> listByCategory = new ArrayList();

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

    list.clear();
    listByCategory.clear();

    try {
      snappydb = DBFactory.open(context);
      list = snappydb.get(key, ArrayList.class);

      for (int i = 0; i < list.size(); i++) {
        Products product = list.get(i);
        for (int j = 0; j < product.getCategory().size(); j++) {
          if (product.getCategory().get(j).getCat().equals(category)) {
            if (Integer.parseInt(product.getStatus()) != 0)
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

  public int whichDateIsGreater(String date_1, String date_2) {

    if (toInt(date_1, 2) > toInt(date_2, 2)) {
      return 1;//1 date has greater year
    } else if (toInt(date_1, 2) < toInt(date_2, 2)) {
      return 2;//2 date has greater year
    } else if (toInt(date_1, 2) == toInt(date_2, 2)) {
      //years equals
      if (toInt(date_1, 1) > toInt(date_2, 1)) {
        return 1;//1 date has greater month
      } else if (toInt(date_1, 1) < toInt(date_2, 1)) {
        return 2;//2 date has greater month
      } else if (toInt(date_1, 1) == toInt(date_2, 1)) {
        //months equals
        if (toInt(date_1, 0) > toInt(date_2, 0)) {
          return 1;//1 date has greater day
        } else if (toInt(date_1, 0) < toInt(date_2, 0)) {
          return 2;//2 date has greater day
        } else if (toInt(date_1, 0) == toInt(date_2, 0)) {
          //days equals
          return 3;//equals
        }
      }
    }
    return 0; //error
  }

  private int toInt(String string, int part){
    return Integer.parseInt(string.split("\\.")[part]);
  }

  public String todayToString(){
    return DateFormat.format("dd.MM.yy", System.currentTimeMillis()).toString();
  }
}
