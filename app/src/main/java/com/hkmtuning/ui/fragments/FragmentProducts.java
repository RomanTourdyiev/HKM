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
import android.widget.Toast;

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

  private List<Products> products = new ArrayList<>();
  private List<Products> productsCut = new ArrayList<>();
  private int lastPosition = 0;
  private boolean loadMore = true;


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
    asyncTaskProducts.execute(categoryId, categoryName);
  }

  private class AsyncTaskProducts extends AsyncTask<String, Void, Void> {

    String categoryId;
    String categoryName;

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      products = new ArrayList<>();
      productsCut = new ArrayList<>();
      lastPosition = 0;
      loadMore = true;
      progressBar.setVisibility(View.VISIBLE);
      noData.setVisibility(View.GONE);
      productsRecycler.setVisibility(View.GONE);
    }

    @Override
    protected Void doInBackground(String... params) {
      categoryId = params[0];
      categoryName = params[1];
      products = ((ActivityMain) getActivity())
          .getUtils()
          .getFromDB(getResources().getString(R.string.products), categoryId);

      for (int i = 0; i < 12; i++) {
        if (products.size() > i + lastPosition) {
          productsCut.add(products.get(i + lastPosition));
        } else {
          loadMore = !loadMore;
          break;
        }
      }
      lastPosition = lastPosition + 12;

      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      if (products.size() == 0) {
        noData.setVisibility(View.VISIBLE);
      } else {
        productsRecycler.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        adapter = new AdapterProducts(
            getActivity(),
            FragmentProducts.this,
            productsCut,
            categoryName);
        productsRecycler.setLayoutManager(gridLayoutManager);
        productsRecycler.setAdapter(adapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
          @Override
          public int getSpanSize(int position) {
            if (adapter.getItemViewType(position) == adapter.VIEW_TYPE_ITEM) {
              return 1;
            } else {
              return 4;
            }
          }
        });
      }
      if (!loadMore){
        adapter.hideLoadingItem();
      }
      progressBar.setVisibility(View.GONE);
      ((ActivityMain) getActivity()).stopCountTime(
          getResources().getString(R.string.products) +
              " " +
              getResources().getString(R.string.from_db_to_list) +
              " (category " + categoryId + ")");
    }
  }


  public void loadMore() {
    if (loadMore) {

      for (int i = 0; i < 12; i++) {
        if (products.size() > i + lastPosition) {
          productsCut.add(products.get(i + lastPosition));
        } else {
          loadMore = !loadMore;
          adapter.hideLoadingItem();
          break;
        }
      }
      lastPosition = lastPosition + 12;

//      Toast.makeText(getActivity(), "load more", Toast.LENGTH_SHORT).show();
      productsRecycler.post(new Runnable() {
        @Override
        public void run() {
            adapter.notifyDataSetChanged();
        }
      });
    }
  }

}
