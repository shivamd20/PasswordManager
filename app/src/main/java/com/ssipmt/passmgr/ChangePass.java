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

public class ChangePass extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        Button b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(new OnClickListener() {
 		public void onClick(View arg0) {
 			
 			EditText et1 = (EditText) findViewById(R.id.editText4);
			EditText et2 = (EditText) findViewById(R.id.editText2);
			EditText et3 = (EditText) findViewById(R.id.editText3);		
			
			String err = null;
			if(et1.getText().toString()==null || "".equalsIgnoreCase(et1.getText().toString())){
				err="Old password can not be blank";
			}
			else if(et2.getText().toString()==null || "".equalsIgnoreCase(et2.getText().toString())){
				err="New password can not be blank";
			}
			else if(!et3.getText().toString().equalsIgnoreCase(et2.getText().toString())){
				err="Password does not match!";
			}
			if(err!=null){
				Toast.makeText(ChangePass.this, err, 2000).show();
				return;
			}
			
			SQLiteDatabase db1 = openOrCreateDatabase("pass_db", MODE_PRIVATE, null);
			String sql1 = "select * from app_users where uname = ? and pass =? ";
			String s[] = new String[2];
			s[0]=PassMgrApp.logedInName;
			s[1]=et1.getText().toString();
			Cursor c2 = db1.rawQuery(sql1, s);
			if(c2.getCount()==0){
				Toast.makeText(ChangePass.this, "Incorrect old password!", 2000).show();
				db1.close();
				return;				
			}
			else {
				db1.close();					
			}
			
			SQLiteDatabase db = openOrCreateDatabase("pass_db", MODE_PRIVATE, null);
			String sql = "update app_users set pass = ? where uname = ? and pass = ? ";
			Object ob[] = new Object[3];
			ob[0]= et3.getText().toString();
			ob[1] =PassMgrApp.logedInName;
			ob[2]= et1.getText().toString();
			db.execSQL(sql, ob);
			db.close();
			et1.setText("");
			et2.setText("");
			Toast.makeText(ChangePass.this, "Saved", 2000).show();
 			Intent i = new Intent(ChangePass.this, Login.class);
 			startActivity(i);
 			ChangePass.this.finish();
 		}
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_change_pass, menu);
        return true;
    }
}
