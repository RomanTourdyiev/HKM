package com.hkmtuning.ui.fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hkmtuning.R;
import com.hkmtuning.api.items.product.Child;
import com.hkmtuning.api.items.product.Products;
import com.hkmtuning.ui.activities.ActivityMain;
import com.hkmtuning.ui.adapters.AdapterChildProducts;
import com.hkmtuning.ui.adapters.AdapterProducts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cantador on 08.11.17.
 */

public class FragmentProductCard
    extends
    android.support.v4.app.Fragment
    implements View.OnClickListener {

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.back:
        ((ActivityMain) getActivity()).hideFragmentProductСard();
        break;
    }
  }

  private ImageView back;
  private ImageView image;
  private TextView productName;
  private TextView category;
  private TextView artNo;
  private TextView priceFrom;
  private TextView priceForSale;
  private TextView description;
  private TextView brand;
  private TextView noData;
  private TextView price5;
  private TextView price10;
  private TextView price25;
  private TextView price50;
  private TextView price100;
  private TextView price250;
  private RecyclerView childsRecycler;
  private ProgressBar childProgress;

  private Products product;
  private String categoryName;
  private List<Child> childs;
  private AdapterChildProducts adapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    ((ActivityMain) getActivity()).startCountTime();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_product_card, container, false);
    initViews(rootView);

    Bundle bundle = getArguments();
    product = (Products) bundle.getSerializable("product");
    categoryName = bundle.getString("category");
    setData();

    back.setOnClickListener(this);
    return rootView;
  }

  private void initViews(View rootView) {
    back = rootView.findViewById(R.id.back);
    image = rootView.findViewById(R.id.image);
    productName = rootView.findViewById(R.id.product_name);
    category = rootView.findViewById(R.id.category);
    artNo = rootView.findViewById(R.id.art_no);
    priceFrom = rootView.findViewById(R.id.price_from);
    priceForSale = rootView.findViewById(R.id.price_for_sale);
    description = rootView.findViewById(R.id.description);
    brand = rootView.findViewById(R.id.brand);
    childsRecycler = rootView.findViewById(R.id.childs_recycler);
    childProgress = rootView.findViewById(R.id.childs_progress);
    noData = rootView.findViewById(R.id.no_data);
    price5 = rootView.findViewById(R.id.price_5);
    price10 = rootView.findViewById(R.id.price_10);
    price25 = rootView.findViewById(R.id.price_25);
    price50 = rootView.findViewById(R.id.price_50);
    price100 = rootView.findViewById(R.id.price_100);
    price250 = rootView.findViewById(R.id.price_250);
  }

  private void setData() {
    Glide.with(getActivity())
        .load(
            Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/hkm/" +
                product.getPath()
                + "/" +
                product.getImage()))
        )
        .into(image);

    productName.setText(product.getName_translate_en());
    category.setText(categoryName);
    artNo.setText(product.getSku());
    priceFrom.setText(product.getPrice());
    priceForSale.setText(product.getPrice_enduser());
    description.setText(Html.fromHtml(product.getDesc_translate_en()));
    brand.setText(product.getPrd_brand());
    childProducts();
    discountPrices();
  }

  private void childProducts() {
    AsyncTaskChildProducts childsTask = new AsyncTaskChildProducts();
    childsTask.execute();
  }

  private void discountPrices(){
    price5.setText(String.valueOf(Float.parseFloat(product.getPrice_enduser())-Float.parseFloat(product.getPrice_enduser())*0.025f));
    price10.setText(String.valueOf(Float.parseFloat(product.getPrice_enduser())-Float.parseFloat(product.getPrice_enduser())*0.05f));
    price25.setText(String.valueOf(Float.parseFloat(product.getPrice_enduser())-Float.parseFloat(product.getPrice_enduser())*0.06f));
    price50.setText(String.valueOf(Float.parseFloat(product.getPrice_enduser())-Float.parseFloat(product.getPrice_enduser())*0.07f));
    price100.setText(String.valueOf(Float.parseFloat(product.getPrice_enduser())-Float.parseFloat(product.getPrice_enduser())*0.075f));
    price250.setText(String.valueOf(Float.parseFloat(product.getPrice_enduser())-Float.parseFloat(product.getPrice_enduser())*0.1f));
  }


  private class AsyncTaskChildProducts extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      childProgress.setVisibility(View.VISIBLE);
      noData.setVisibility(View.GONE);
      childsRecycler.setVisibility(View.GONE);
    }

    @Override
    protected Void doInBackground(Void... params) {
      childs = product.getChild();
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      if (childs.size() == 0) {
        noData.setVisibility(View.VISIBLE);
      } else {
        childsRecycler.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        adapter = new AdapterChildProducts(
            getActivity(),
            FragmentProductCard.this,
            childs);
        childsRecycler.setLayoutManager(linearLayoutManager);
        childsRecycler.setAdapter(adapter);
        childProgress.setVisibility(View.GONE);
        ((ActivityMain) getActivity()).stopCountTime(
            getResources().getString(R.string.products) +
                " " +
                getResources().getString(R.string.from_db_to_list) +
                " (product Card)");
      }
    }


  }
}
