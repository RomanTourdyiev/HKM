package com.hkmtuning.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hkmtuning.R;
import com.hkmtuning.api.items.category.Category;
import com.hkmtuning.ui.activities.ActivityMain;
import com.hkmtuning.ui.adapters.AdapterCategories;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cantador on 07.11.17.
 */

public class FragmentCategories extends Fragment {

  private RecyclerView categoriesRecycler;
  private ProgressBar progressBar;
  private TextView noData;

  private AdapterCategories adapter;

  private List<Category> categories;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
    initViews(rootView);
    noData.setVisibility(View.VISIBLE);
    getFromDB();
    return rootView;
  }

  private void initViews(View rootView) {
    categoriesRecycler = rootView.findViewById(R.id.categories_recycler);
    progressBar = rootView.findViewById(R.id.progress_bar);
    noData = rootView.findViewById(R.id.no_data);
  }

  public void getFromDB() {
    ((ActivityMain) getActivity()).startCountTime();
    AsyncTaskCategories asyncTaskCategories = new AsyncTaskCategories();
    asyncTaskCategories.execute();
  }

  private class AsyncTaskCategories extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      categories = new ArrayList<>();
      progressBar.setVisibility(View.VISIBLE);
      noData.setVisibility(View.GONE);
    }

    @Override
    protected Void doInBackground(Void... params) {
      categories = ((ActivityMain) getActivity())
          .getUtils()
          .getFromDB(getResources().getString(R.string.categories));
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      if (categories.size() == 0) {
        noData.setVisibility(View.VISIBLE);
      } else {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new AdapterCategories(getActivity(), FragmentCategories.this, linearLayoutManager, categories);
        categoriesRecycler.setLayoutManager(linearLayoutManager);
        categoriesRecycler.setAdapter(adapter);
      }
      progressBar.setVisibility(View.GONE);
      ((ActivityMain) getActivity()).stopCountTime(getResources().getString(R.string.categories) +
          " " +
          getResources().getString(R.string.from_db_to_list));
    }
  }

}
