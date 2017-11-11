package com.hkmtuning.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hkmtuning.R;
import com.hkmtuning.api.items.product.Child;
import com.hkmtuning.api.items.product.Products;
import com.hkmtuning.ui.activities.ActivityMain;
import com.hkmtuning.ui.fragments.FragmentProductCard;
import com.hkmtuning.ui.fragments.FragmentProducts;
import com.hkmtuning.util.Utils;

import java.io.File;
import java.util.List;

import static android.view.View.GONE;

/**
 * Created by Cantador on 08.11.17.
 */

public class AdapterChildProducts
    extends RecyclerView.Adapter<AdapterChildProducts.ViewHolder> {

  private Context context;
  private List<Child> list;
  private FragmentProductCard fragment;

  public AdapterChildProducts(
      Context context,
      FragmentProductCard fragment,
      List<Child> list) {
    this.context = context;
    this.fragment = fragment;
    this.list = list;
  }


  @Override
  public AdapterChildProducts.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_child_product, viewGroup, false);
      return new AdapterChildProducts.ViewHolder(view);
  }

  public int getItemCount() {
    return list.size();
  }

  @Override
  public void onBindViewHolder(final AdapterChildProducts.ViewHolder holder, int i) {
    final View itemView = holder.itemView;
      holder.sku.setText(list.get(holder.getAdapterPosition()).getSku());
      holder.color.setText(list.get(holder.getAdapterPosition()).getColor());
      holder.size.setText(list.get(holder.getAdapterPosition()).getSize());
      holder.qty.setText(list.get(holder.getAdapterPosition()).getQty());

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
      });
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    protected TextView sku;
    protected TextView color;
    protected TextView size;
    protected TextView qty;

    public ViewHolder(View view) {
      super(view);
      this.sku = view.findViewById(R.id.sku);
      this.color = view.findViewById(R.id.color);
      this.size = view.findViewById(R.id.size);
      this.qty = view.findViewById(R.id.qty);
    }
  }
}
