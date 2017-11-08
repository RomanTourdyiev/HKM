package com.hkmtuning.api;

import com.hkmtuning.api.items.category.Category;
import com.hkmtuning.api.items.product.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Cantador on 09.09.17.
 */

public interface Api {

  @GET("categories")
  Call<List<Category>> getCategories();

  @GET("products")
  Call<Product> getProducts();
}
