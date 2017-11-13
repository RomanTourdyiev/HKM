package com.hkmtuning.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
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
    Child item = list.get(holder.getAdapterPosition());
    holder.sku.setText(item.getSku());
    holder.color.setText(item.getColor());
    holder.size.setText(item.getSize());
    holder.qty.setText(item.getQty());

    if (Integer.parseInt(item.getIs_in_stock()) == 0) {
      holder.availability.setBackgroundTintList(context.getResources().getColorStateList(R.color.red_500));
      holder.availability.setImageResource(R.drawable.ic_not_interested_white_24dp);

    } else if (Integer.parseInt(item.getIs_in_stock()) == 1) {

      if (item.getPrd_available_from()==null && item.getPrd_publication_date()==null){
        holder.availability.setBackgroundTintList(context.getResources().getColorStateList(R.color.teal_500));
        holder.availability.setImageResource(R.drawable.ic_event_available_white_24dp);
        if (item.getPrd_publication_date()!=null) {
          if (((ActivityMain) context).getUtils().whichDateIsGreater(
              ((ActivityMain) context).getUtils().todayToString(),
              item.getPrd_publication_date()) == 2
              ) {
            holder.availability.setBackgroundTintList(context.getResources().getColorStateList(R.color.teal_300));
            holder.availability.setImageResource(R.drawable.ic_access_time_white_24dp);
          } else {
            holder.availability.setBackgroundTintList(context.getResources().getColorStateList(R.color.blue_grey_200));
            holder.availability.setImageResource(R.drawable.ic_access_time_white_24dp);
          }
        }

      }

    }


    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    private TextView sku;
    private TextView color;
    private TextView size;
    private TextView qty;
    private FloatingActionButton availability;

    public ViewHolder(View view) {
      super(view);
      this.sku = view.findViewById(R.id.sku);
      this.color = view.findViewById(R.id.color);
      this.size = view.findViewById(R.id.size);
      this.qty = view.findViewById(R.id.qty);
      this.availability = view.findViewById(R.id.availability);
    }
  }
}
