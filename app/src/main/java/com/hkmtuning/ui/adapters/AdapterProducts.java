package com.hkmtuning.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hkmtuning.R;
import com.hkmtuning.api.items.product.Products;
import com.hkmtuning.ui.activities.ActivityMain;
import com.hkmtuning.ui.fragments.FragmentProducts;
import com.hkmtuning.util.Utils;

import java.util.List;

import static android.view.View.GONE;

/**
 * Created by Cantador on 08.11.17.
 */

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ViewHolder> {

  private Context context;
  private List<Products> list;
  private FragmentProducts fragment;
  private String categoryName;

  public final int VIEW_TYPE_ITEM = 0;
  public final int VIEW_TYPE_LOADING = 1;
  private boolean initLoad = true;
  private boolean hideLoadingItem = false;

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
  public void onViewAttachedToWindow(AdapterProducts.ViewHolder holder) {
    super.onViewAttachedToWindow(holder);
    if (getItemViewType(holder.getAdapterPosition()) == VIEW_TYPE_LOADING) {
      if (initLoad) {
//        Toast.makeText(context, "" + getItemViewType(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
        initLoad = !initLoad;
      } else {
        initLoad = !initLoad;
        fragment.loadMore();
      }
    }

  }

  @Override
  public AdapterProducts.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    if (viewType == VIEW_TYPE_ITEM) {
      View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);
      return new AdapterProducts.ViewHolder(view);
    } else if (viewType == VIEW_TYPE_LOADING) {
      View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_loading_product, viewGroup, false);
      return new AdapterProducts.ViewHolder(view);
    }
    return null;
  }

  //  public int getItemCount() {
//    return list.size();
//  }
  public int getItemCount() {
    return (null != list ? list.size() + 1 : 1);
  }

  @Override
  public int getItemViewType(int position) {
    return position == list.size() ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
  }

  public void hideLoadingItem() {
    hideLoadingItem = true;
  }

  @Override
  public void onBindViewHolder(final AdapterProducts.ViewHolder holder, int i) {
    final View itemView = holder.itemView;

    if (getItemViewType(i) == VIEW_TYPE_ITEM) {
      holder.title.setText(list.get(holder.getAdapterPosition()).getName_translate_en());
      holder.price.setText(list.get(holder.getAdapterPosition()).getPrice());
      Glide.with(context).load(
          Uri.parse("file:///android_asset"+
          list.get(holder.getAdapterPosition()).getPath()+
              list.get(holder.getAdapterPosition()).getImage())).into(holder.image);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          ((ActivityMain) context).productCard(
              list.get(holder.getAdapterPosition()),
              categoryName);
        }
      });

    } else {
      if (hideLoadingItem) {
        itemView.setVisibility(View.GONE);
      } else {
        itemView.setVisibility(View.VISIBLE);
      }

//      fragment.loadMore();
    }
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
