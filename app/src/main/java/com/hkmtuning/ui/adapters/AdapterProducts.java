package com.hkmtuning.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkmtuning.R;
import com.hkmtuning.api.items.product.Products;
import com.hkmtuning.ui.activities.ActivityMain;
import com.hkmtuning.ui.fragments.FragmentProducts;

import java.util.List;

/**
 * Created by Cantador on 08.11.17.
 */

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ViewHolder> {

  private Context context;
  private List<Products> list;
  private FragmentProducts fragment;
  private String categoryName;

  public AdapterProducts(Context context,
                         FragmentProducts fragment,
                         List<Products> list,
                         String categoryName) {
    this.context = context;
    this.fragment = fragment;
    this.list = list;
    this.categoryName = categoryName;
  }

  @Override
  public AdapterProducts.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);
    return new AdapterProducts.ViewHolder(view);
  }

  public int getItemCount() {
    return list.size();
  }

  @Override
  public void onBindViewHolder(final AdapterProducts.ViewHolder holder, int i) {
    final View itemView = holder.itemView;

    holder.title.setText(list.get(holder.getAdapterPosition()).getName_translate_en());
    holder.price.setText(list.get(holder.getAdapterPosition()).getPrice());

    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ((ActivityMain)context).productCard(
            list.get(holder.getAdapterPosition()),
            categoryName);
      }
    });

  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    protected ImageView image;
    protected TextView title;
    protected TextView price;

    public ViewHolder(View view) {
      super(view);
      this.image = view.findViewById(R.id.image);
      this.title = view.findViewById(R.id.title);
      this.price = view.findViewById(R.id.price);
    }
  }
}
