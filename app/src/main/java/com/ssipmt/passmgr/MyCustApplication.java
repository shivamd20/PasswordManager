package com.ssipmt.passmgr;

import android.app.Application;

import io.hasura.sdk.Hasura;
import io.hasura.sdk.ProjectConfig;

/**
 * Created by Singhvi on 03-08-2017.
 */
public class MyCustApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        try{
            Hasura.setProjectConfig(new ProjectConfig.Builder().setProjectName("cover77")
                    .enableOverHttp()
                    .setDefaultRole("user").build()
            );

        }catch (Exception e)
        {

        }
    }
}
