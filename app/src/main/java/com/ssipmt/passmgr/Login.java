package com.ssipmt.passmgr;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.hasura.sdk.Hasura;
import io.hasura.sdk.HasuraUser;
import io.hasura.sdk.exception.HasuraException;
import io.hasura.sdk.responseListener.SignUpResponseListener;

public class Login extends Activity {

	HasuraUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //deleteDatabase("pass_db");

		user= Hasura.getClient().getUser();

        SQLiteDatabase db = openOrCreateDatabase("pass_db", MODE_PRIVATE, null);
        String sql = "create table if not exists pass_mgr(" +
        		" id  INTEGER PRIMARY KEY, " +
        		" app_name varchar(45), " +
           		" pass varchar(45), " +
         	    " uname varchar(45)" +
        		")";
        db.execSQL(sql);
        sql = "create table if not exists app_users(" +
        		" id  INTEGER PRIMARY KEY, " +
        		" uname varchar(45), " +
        		" pass varchar(45)" +
        		")";
        db.execSQL(sql);
        db.close();        
        
        Button b1 = (Button) findViewById(R.id.login);
        b1.setOnClickListener(new OnClickListener() {
 		public void onClick(View arg0) {
 			EditText et1 = (EditText) findViewById(R.id.editText1);
			EditText et2 = (EditText) findViewById(R.id.editText2);

			user.setUsername(et1.getText().toString());
			user.setPassword(et2.getText().toString());

			user.signUp(new SignUpResponseListener() {
				@Override
				public void onSuccessAwaitingVerification(HasuraUser hasuraUser) {

				}

				@Override
				public void onSuccess(HasuraUser hasuraUser) {


					SQLiteDatabase db1 = openOrCreateDatabase("pass_db", MODE_PRIVATE, null);
					String sql1 = "select * from app_users where uname = ? and pass = ? ";
					String s[] = new String[2];
					s[0]=user.getUsername();
					s[1]=user.getPassword()+"";

					Cursor c2 = db1.rawQuery(sql1, s);
					if(c2.getCount()>0){
						db1.close();
						PassMgrApp.logedInName=user.getUsername();
						Intent i = new Intent(Login.this, PassMgrApp.class);
						startActivity(i);
						Login.this.finish();
					}
					else {
						Toast.makeText(Login.this, "Invalid Information!", Toast.LENGTH_SHORT).show();
						db1.close();
					}

				}

				@Override
				public void onFailure(HasuraException e) {
				Toast.makeText(Login.this,"ERROR",Toast.LENGTH_SHORT).show();
				}
			});


 		}
        });
        Button b2 = (Button) findViewById(R.id.register);
        b2.setOnClickListener(new OnClickListener() {
 		public void onClick(View arg0) {
 			Intent i = new Intent(Login.this, Register.class);
 			startActivity(i);
 			//Login.this.finish();
 		}
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
}
