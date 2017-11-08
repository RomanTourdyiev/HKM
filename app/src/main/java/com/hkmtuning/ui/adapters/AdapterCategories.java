package com.hkmtuning.ui.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hkmtuning.R;
import com.hkmtuning.api.items.category.Category;
import com.hkmtuning.ui.activities.ActivityMain;
import com.hkmtuning.ui.fragments.FragmentCategories;

import java.util.List;

/**
 * Created by Cantador on 07.11.17.
 */

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.ViewHolder> {

  private Context context;
  private List<Category> list;
  private FragmentCategories fragmentCategories;
  private LinearLayoutManager layoutManager;
  private int selection = -1;
  private int color = -1;


  public AdapterCategories(
      Context context,
      FragmentCategories fragmentCategories,
      LinearLayoutManager layoutManager,
      List<Category> list) {
    this.context = context;
    this.fragmentCategories = fragmentCategories;
    this.list = list;
    this.layoutManager = layoutManager;
  }

  @Override
  public AdapterCategories.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_category, viewGroup, false);
    return new AdapterCategories.ViewHolder(view);
  }

  public int getItemCount() {
    return list.size();
  }

  @Override
  public void onBindViewHolder(final AdapterCategories.ViewHolder holder, int i) {
    final View itemView = holder.itemView;

    final Category category = list.get(holder.getAdapterPosition());

    holder.categoryName.setText(category.getTranslate_en() + "(" + category.getProducts() + ")");

    if (holder.getAdapterPosition() == selection) {
      color = R.color.colorPrimaryDark;
    } else {
      color = R.color.colorPrimary;
    }

    itemView.setBackgroundColor(context.getResources().getColor(color));
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        layoutManager.scrollToPosition(holder.getAdapterPosition());
        ((ActivityMain) context).getFragmentProducts().getFromDB(category.getId(),category.getTranslate_en());
        if (selection!=-1) {
          notifyItemChanged(selection);
        }
        selection = holder.getAdapterPosition();
        notifyItemChanged(selection);
      }
    });

  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    protected TextView categoryName;

    public ViewHolder(View view) {
      super(view);
      this.categoryName = view.findViewById(R.id.category_name);
    }
  }

}