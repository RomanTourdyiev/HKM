package com.hkmtuning.ui.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkmtuning.R;
import com.hkmtuning.api.items.product.Products;
import com.hkmtuning.ui.activities.ActivityMain;

/**
 * Created by Cantador on 08.11.17.
 */

public class FragmentProductCard
    extends
    android.support.v4.app.Fragment
    implements View.OnClickListener {

  private ImageView back;
  private TextView productName;
  private TextView category;
  private TextView artNo;
  private TextView priceFrom;
  private TextView priceForSale;
  private TextView description;

  private Products product;
  private String categoryName;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
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
    productName = rootView.findViewById(R.id.product_name);
    category = rootView.findViewById(R.id.category);
    artNo = rootView.findViewById(R.id.art_no);
    priceFrom = rootView.findViewById(R.id.price_from);
    priceForSale = rootView.findViewById(R.id.price_for_sale);
    description = rootView.findViewById(R.id.description);
  }

  private void setData(){
    productName.setText(product.getName_translate_en());
    category.setText(categoryName);
    artNo.setText(product.getSku());
    priceFrom.setText(product.getPrice());
    priceForSale.setText(product.getPrice_enduser());
    description.setText(Html.fromHtml(product.getDesc_translate_en()));
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.back:
        ((ActivityMain) getActivity()).hideFragmentProductСard();
        break;
    }
  }
}
