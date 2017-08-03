package com.ssipmt.passmgr;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class PassMgr extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_mgr);
        Animation a1 =  AnimationUtils.loadAnimation(PassMgr.this, R.anim.anim);
        ImageView iv1 = (ImageView) findViewById(R.id.imageView1);
        iv1.startAnimation(a1);
        Timer t1 = new Timer();
        TimerTask tt = new TimerTask() {
			public void run() {
				Intent i1 = new Intent(PassMgr.this, Login.class);
				startActivity(i1);
				PassMgr.this.finish();
			}
		};
		t1.schedule(tt, 3800);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_pass_mgr, menu);
        return true;
    }
}
