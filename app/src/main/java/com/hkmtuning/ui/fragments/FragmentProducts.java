package com.hkmtuning.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hkmtuning.R;
import com.hkmtuning.api.items.product.Products;
import com.hkmtuning.ui.activities.ActivityMain;
import com.hkmtuning.ui.adapters.AdapterProducts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cantador on 07.11.17.
 */

public class FragmentProducts extends Fragment {

  private RecyclerView productsRecycler;
  private ProgressBar progressBar;
  private TextView noData;

  private AdapterProducts adapter;

  private List<Products> products;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_products, container, false);
    initViews(rootView);
    noData.setVisibility(View.VISIBLE);
    return rootView;
  }

  private void initViews(View rootView) {
    productsRecycler = rootView.findViewById(R.id.products_recycler);
    progressBar = rootView.findViewById(R.id.progress_bar);
    noData = rootView.findViewById(R.id.no_data);
  }

  public void getFromDB(String categoryId, String categoryName) {
    ((ActivityMain) getActivity()).startCountTime();
    AsyncTaskProducts asyncTaskProducts = new AsyncTaskProducts();
    asyncTaskProducts.execute(categoryId,categoryName);
  }

  private class AsyncTaskProducts extends AsyncTask<String, Void, Void> {

    String categoryId;
    String categoryName;

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      products = new ArrayList<>();
      progressBar.setVisibility(View.VISIBLE);
      noData.setVisibility(View.GONE);
    }

    @Override
    protected Void doInBackground(String... params) {
      categoryId = params[0];
      categoryName = params[1];
      products = ((ActivityMain) getActivity())
          .getUtils()
          .getFromDB(getResources().getString(R.string.products), categoryId);
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      if (products.size() == 0) {
        noData.setVisibility(View.VISIBLE);
      } else {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        adapter = new AdapterProducts(
            getActivity(),
            FragmentProducts.this,
            products,
            categoryName);
        productsRecycler.setLayoutManager(gridLayoutManager);
        productsRecycler.setAdapter(adapter);
      }
      progressBar.setVisibility(View.GONE);
      ((ActivityMain) getActivity()).stopCountTime(getResources().getString(R.string.products) +
          " " +
          getResources().getString(R.string.from_db_to_list)+ " (category "+categoryId+")");
    }
  }

}
