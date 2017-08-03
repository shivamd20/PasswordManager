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

public class Register extends Activity {


	HasuraUser user;
	EditText et1;
	EditText et2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button b1 = (Button) findViewById(R.id.button1);

		user=Hasura.getClient().getUser();

        b1.setOnClickListener(new OnClickListener() {
 		public void onClick(View arg0) {
 			//Intent i = new Intent(Register.this, Login.class);
 			//startActivity(i);
 			et1 = (EditText) findViewById(R.id.editText1);//username
			et2 = (EditText) findViewById(R.id.editText2);//password
			EditText et3 = (EditText) findViewById(R.id.editText3);

			user.setUsername(et1.getText().toString());
			user.setPassword(et2.getText().toString());

			if(!((et1.getText().length()>3)||(et2.getText().length()>2)))
			{
				Toast.makeText(Register.this,"error",Toast.LENGTH_SHORT).show();
				return;
			}

			try{
				user.signUp(new SignUpResponseListener() {
					@Override
					public void onSuccessAwaitingVerification(HasuraUser hasuraUser) {

					}

					@Override
					public void onSuccess(HasuraUser hasuraUser) {

						SQLiteDatabase db1 = openOrCreateDatabase("pass_db", MODE_PRIVATE, null);
						String sql1 = "select * from app_users where uname = ? ";
						String s[] = new String[1];
						s[0]=et1.getText().toString();
						Cursor c2 = db1.rawQuery(sql1, s);
						if(c2.getCount()>0){
							Toast.makeText(Register.this, "User already exist!", Toast.LENGTH_SHORT).show();
							db1.close();
							return;
						}
						else {
							db1.close();
						}

						SQLiteDatabase db = openOrCreateDatabase("pass_db", MODE_PRIVATE, null);
						String sql = "insert into app_users(uname, pass) values(?, ?)";
						Object ob[] = new Object[2];
						ob[0] =user.getUsername();
						ob[1]= user.getPassword();
						String err = null;

						if(err!=null){
							Toast.makeText(Register.this, err, Toast.LENGTH_SHORT).show();
							return;
						}
						db.execSQL(sql, ob);
						db.close();
						Toast.makeText(Register.this, "Saved", Toast.LENGTH_SHORT).show();
						Register.this.finish();

					}

					@Override
					public void onFailure(HasuraException e) {

						et1.setText("");
						et2.setText("");

					}
				});

			}catch (Exception e)
			{

			}

 		}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_register, menu);
        return true;
    }
}
