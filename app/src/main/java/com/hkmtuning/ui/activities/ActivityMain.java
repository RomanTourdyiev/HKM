package com.hkmtuning.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hkmtuning.App;
import com.hkmtuning.R;
import com.hkmtuning.api.Api;
import com.hkmtuning.api.items.category.Category;
import com.hkmtuning.api.items.product.Product;
import com.hkmtuning.api.items.product.Products;
import com.hkmtuning.ui.fragments.FragmentCategories;
import com.hkmtuning.ui.fragments.FragmentLog;
import com.hkmtuning.ui.fragments.FragmentProductCard;
import com.hkmtuning.ui.fragments.FragmentProducts;
import com.hkmtuning.util.NetworkCheck;
import com.hkmtuning.util.Utils;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener {


  @Inject
  public Retrofit retrofit;

  private FragmentManager fragmentManager;
  private FragmentTransaction transaction;
  private NetworkCheck networkCheck;
  private Utils utils;

  private FragmentCategories fragmentCategories;
  private FragmentProducts fragmentProducts;
  private FragmentProductCard fragmentProductСard;
  private FragmentLog fragmentLog;

  private Button fromAPItoDB;
  private Button fromDBtoList;
  private Button log;
  private ProgressBar progressBarAPItoDB;
  private TextView message;

  private String log_string = "";
  private long startTime;
  private long delay;

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ((App) getApplicationContext()).getNetworkComponent().inject(this);
    initViews();
    listeners();
    initUtils();
    categoriesList();
    productsGrid();
  }

  @Override
  public void onBackPressed() {
    if (fragmentProductСard != null) {
      hideFragmentProductСard();
    } else if (fragmentLog != null) {
      hideLog();
    } else {
      super.onBackPressed();
    }
  }

  private void initViews() {
    fromAPItoDB = (Button) findViewById(R.id.from_api_to_db);
    fromDBtoList = (Button) findViewById(R.id.from_db_to_list);
    log = (Button) findViewById(R.id.log);
    progressBarAPItoDB = (ProgressBar) findViewById(R.id.progress_bar_api_db);
    message = (TextView) findViewById(R.id.message);
  }

  private void listeners() {
    fromAPItoDB.setOnClickListener(this);
    fromDBtoList.setOnClickListener(this);
    log.setOnClickListener(this);
  }

  private void initUtils() {
    networkCheck = new NetworkCheck();
    utils = new Utils(this);
  }

  private void categoriesList() {
    fragmentCategories = new FragmentCategories();
    showFragment(R.id.categories_frame_layout, fragmentCategories);
  }

  private void productsGrid() {
    fragmentProducts = new FragmentProducts();
    showFragment(R.id.content_frame_layout, fragmentProducts);
  }

  public void productCard(Products product, String categoryName) {
    Bundle bundle = new Bundle();
    bundle.putSerializable("product", product);
    bundle.putString("category", categoryName);
    fragmentProductСard = new FragmentProductCard();
    fragmentProductСard.setArguments(bundle);
    showFragment(R.id.popup_frame_layout, fragmentProductСard);
  }

  private void showFragment(int container, Fragment fragment) {
    fragmentManager = getSupportFragmentManager();
    transaction = fragmentManager.beginTransaction();
    transaction.replace(container, fragment, "");
    transaction.commit();
  }

  public void hideFragmentProductСard() {
    transaction = fragmentManager.beginTransaction();
    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
    transaction.hide(fragmentProductСard);
    transaction.commit();
    fragmentProductСard = null;
  }

  public void hideLog() {
    transaction = fragmentManager.beginTransaction();
    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
    transaction.hide(fragmentLog);
    transaction.commit();
    fragmentLog = null;
  }

  private void getCategoriesFromAPI() {
    if (networkCheck.isNetworkAvailable(this)) {
      startCountTime();
      progressBarAPItoDB.setVisibility(View.VISIBLE);
      message.setVisibility(View.VISIBLE);
      message.setText(getResources().getString(R.string.loading) + " " + getResources().getString(R.string.categories) + " ");

      Call<List<Category>> categories = retrofit.create(Api.class).getCategories();
      categories.enqueue(new Callback<List<Category>>() {
        @Override
        public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
          utils.storeInDB(response.body(), getResources().getString(R.string.categories));
          progressBarAPItoDB.setVisibility(View.GONE);
          message.setVisibility(View.GONE);
          stopCountTime(getResources().getString(R.string.categories) +
              " " +
              getResources().getString(R.string.from_api_to_db));
          getProductsFromApi();
        }

        @Override
        public void onFailure(Call<List<Category>> call, Throwable t) {
          Log.d(getResources().getString(R.string.app_name), t.toString());
        }
      });


    } else {
      Toast.makeText(this, getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
    }
  }

  private void getProductsFromApi() {
    startCountTime();
    progressBarAPItoDB.setVisibility(View.VISIBLE);
    message.setVisibility(View.VISIBLE);
    message.setText(getResources().getString(R.string.loading) + " " + getResources().getString(R.string.products) + " ");
    Call<Product> products = retrofit.create(Api.class).getProducts();
    products.enqueue(new Callback<Product>() {
      @Override
      public void onResponse(Call<Product> call, Response<Product> response) {
        utils.storeInDB(response.body().getProducts(), getResources().getString(R.string.products));
        progressBarAPItoDB.setVisibility(View.GONE);
        message.setVisibility(View.GONE);
        stopCountTime(getResources().getString(R.string.products) +
            " " +
            getResources().getString(R.string.from_api_to_db));
      }

      @Override
      public void onFailure(Call<Product> call, Throwable t) {
        Log.d(getResources().getString(R.string.app_name), t.toString());
      }
    });
  }

  public void showLog() {
    fragmentLog = new FragmentLog();
    showFragment(R.id.popup_frame_layout, fragmentLog);
  }

  public String getLog() {
    return log_string;
  }

  public void startCountTime() {
    startTime = System.currentTimeMillis();
  }

  public void stopCountTime(String info) {
    delay = System.currentTimeMillis() - startTime;
    log_string = log_string + info + "(" + delayTime() + ");\n";
  }

  public String delayTime() {
    return delay + getResources().getString(R.string.ms);
  }

  public Utils getUtils() {
    return utils;
  }

  public FragmentProducts getFragmentProducts() {
    return fragmentProducts;
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {

      case R.id.from_api_to_db:
        getCategoriesFromAPI();
        break;

      case R.id.from_db_to_list:
        fragmentCategories.getFromDB();
        break;

      case R.id.log:
        showLog();
        break;
    }
  }
}
