package com.hkmtuning;


import com.hkmtuning.R;
import com.hkmtuning.dagger.component.DaggerNetworkComponent;
import com.hkmtuning.dagger.component.NetworkComponent;
import com.hkmtuning.dagger.module.ApplicationModule;
import com.hkmtuning.dagger.module.NetworkModule;
/**
 * Created by Cantador on 07.11.17.
 */

public class App extends android.app.Application {

  private NetworkComponent networkComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    networkComponent = DaggerNetworkComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .networkModule(new NetworkModule(getResources().getString(R.string.base_url)))
        .build();

  }

  public NetworkComponent getNetworkComponent() {
    return networkComponent;
  }
}