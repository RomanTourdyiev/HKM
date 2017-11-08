package com.hkmtuning.dagger.component;

import javax.inject.Singleton;

import com.hkmtuning.dagger.module.ApplicationModule;
import com.hkmtuning.dagger.module.NetworkModule;
import com.hkmtuning.ui.activities.ActivityMain;
import dagger.Component;

/**
 * Created by Cantador on 10.09.17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface NetworkComponent {
  void inject(ActivityMain activityMain);
}