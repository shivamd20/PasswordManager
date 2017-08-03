package com.ssipmt.passmgr;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PassMgrApp extends Activity {
    public static String logedInName = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_mgr_app);
        Button b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(new OnClickListener() {
 		public void onClick(View arg0) {
 			Intent i = new Intent(PassMgrApp.this, SearchPass.class);
 			startActivity(i);
 		}
        });
        Button b2 = (Button) findViewById(R.id.Button01);
        b2.setOnClickListener(new OnClickListener() {
 		public void onClick(View arg0) {
 			Intent i = new Intent(PassMgrApp.this, AddPass.class);
 			startActivity(i);
 		}
        });
        Button b3 = (Button) findViewById(R.id.Button02);
        b3.setOnClickListener(new OnClickListener() {
 		public void onClick(View arg0) {
 			Intent i = new Intent(PassMgrApp.this, ChangePass.class);
 			startActivity(i);
 		}
     	});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_pass_mgr_app, menu);
        return true;
    }
}
