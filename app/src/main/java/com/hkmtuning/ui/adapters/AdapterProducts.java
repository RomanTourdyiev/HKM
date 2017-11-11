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
import com.hkmtuning.api.items.product.Products;
import com.hkmtuning.ui.activities.ActivityMain;
import com.hkmtuning.ui.fragments.FragmentProducts;
import com.hkmtuning.util.Utils;

import java.io.File;
import java.util.List;

import static android.view.View.GONE;

/**
 * Created by Cantador on 08.11.17.
 */

public class AdapterProducts
    extends RecyclerView.Adapter<AdapterProducts.ViewHolder> {

  private Context context;
  private List<Products> list;
  private FragmentProducts fragment;
  private String categoryName;

  public final int VIEW_TYPE_ITEM = 0;
  public final int VIEW_TYPE_LOADING = 1;
  private boolean initLoad = true;
  private boolean hideLoadingItem = false;

  private OnScrollListener onScrollListener = new OnScrollListener() {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
      super.onScrollStateChanged(recyclerView, newState);
      if (newState == RecyclerView.SCROLL_STATE_IDLE){
        GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
        if (getItemViewType(manager.findLastVisibleItemPosition()) == VIEW_TYPE_LOADING) {
          fragment.loadMore();
          Toast.makeText(context, "load more!", Toast.LENGTH_SHORT).show();
        }
      }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      super.onScrolled(recyclerView, dx, dy);

    }
  };

  public AdapterProducts(
      Context context,
      FragmentProducts fragment,
      RecyclerView parentRecycler,
      List<Products> list,
      String categoryName) {
    this.context = context;
    this.fragment = fragment;
    this.list = list;
    this.categoryName = categoryName;
    parentRecycler.addOnScrollListener(onScrollListener);
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

      Glide.with(context)
          .load(
              Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/hkm/" +
                  list.get(holder.getAdapterPosition()).getPath()
                  + "/" +
                  list.get(holder.getAdapterPosition()).getImage()))
          )
          .into(holder.image);

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
