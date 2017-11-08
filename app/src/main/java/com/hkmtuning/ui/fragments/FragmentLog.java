package com.hkmtuning.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkmtuning.R;
import com.hkmtuning.ui.activities.ActivityMain;

/**
 * Created by Cantador on 08.11.17.
 */

public class FragmentLog
    extends
    android.support.v4.app.Fragment
    implements View.OnClickListener {

  private ImageView back;
  private TextView log;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_log, container, false);
    initViews(rootView);
    log();

    back.setOnClickListener(this);
    return rootView;
  }

  private void log() {
    if (((ActivityMain)getActivity()).getLog().length()!=0) {
      log.setText(((ActivityMain) getActivity()).getLog());
    } else {
      log.setText(getResources().getString(R.string.no_data));
    }
  }

  private void initViews(View rootView) {
    log = rootView.findViewById(R.id.log);
    back = rootView.findViewById(R.id.back);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.back:
        ((ActivityMain) getActivity()).hideLog();
        break;
    }
  }
}
