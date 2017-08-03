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

public class AddPass extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pass);
        Button b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(new OnClickListener() {
 		public void onClick(View arg0) {
 			EditText et1 = (EditText) findViewById(R.id.editText1);
			EditText et2 = (EditText) findViewById(R.id.editText2);

			SQLiteDatabase db1 = openOrCreateDatabase("pass_db", MODE_PRIVATE, null);
			String sql1 = "select * from pass_mgr where uname = ? and app_name =?  ";
			String s[] = new String[2];
			s[0]=PassMgrApp.logedInName;
			s[1]=et1.getText().toString();
			Cursor c2 = db1.rawQuery(sql1, s);
			if(c2.getCount()>0){
				Toast.makeText(AddPass.this, "Application already exist!", 2000).show();
				db1.close();
				return;				
			}
			else {
				db1.close();					
			}
			
			SQLiteDatabase db = openOrCreateDatabase("pass_db", MODE_PRIVATE, null);
			String sql = "insert into pass_mgr(app_name,  pass, uname) values(?, ?, ?)";
			Object ob[] = new Object[3];
			ob[0] =et1.getText().toString();
			ob[1]= PMUtil.enc(et2.getText().toString());
			ob[2]= PassMgrApp.logedInName;
			String err = null;
			if(et1.getText().toString()==null || "".equalsIgnoreCase(et1.getText().toString())){
				err="Application name can not be blank";
			}
			else if(et2.getText().toString()==null || "".equalsIgnoreCase(et2.getText().toString())){
				err="Password can not be blank";
			}
			if(err!=null){
				Toast.makeText(AddPass.this, err, 2000).show();
				return;
			}
			db.execSQL(sql, ob);
			db.close();
			et1.setText("");
			et2.setText("");
			Toast.makeText(AddPass.this, "Saved", 2000).show();
 			AddPass.this.finish();
 		}
        }); 			

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_pass, menu);
        return true;
    }
}
