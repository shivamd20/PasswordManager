package com.ssipmt.passmgr;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePass extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);
        String sid = getIntent().getStringExtra("sid");
		EditText et1 = (EditText) findViewById(R.id.editText1);
		EditText et2 = (EditText) findViewById(R.id.editText2);

		et1.setEnabled(false);
		SQLiteDatabase db1 = openOrCreateDatabase("pass_db", MODE_PRIVATE, null);
		String sql1 = "select * from pass_mgr where id =?  ";
		String s[] = new String[1];
		s[0]=sid;
		Cursor c2 = db1.rawQuery(sql1, s);
		int in1 = c2.getColumnIndex("app_name");
		int in2 = c2.getColumnIndex("pass");
		if(c2.moveToFirst()){
			String app_name = c2.getString(in1);
			String pass = PMUtil.dec(c2.getString(in2));
			et1.setText(app_name);
			et2.setText(pass);
		}		
		db1.close();	
		
		Button b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(new OnClickListener() {
 		public void onClick(View arg0) {
			EditText et2 = (EditText) findViewById(R.id.editText2);
			SQLiteDatabase db = openOrCreateDatabase("pass_db", MODE_PRIVATE, null);
			String sql = "update pass_mgr set pass= ? where id = ? ";
			Object ob[] = new Object[2];
			ob[0]= PMUtil.enc(et2.getText().toString());
			ob[1]= getIntent().getStringExtra("sid");
			String err = null;
			if(et2.getText().toString()==null || "".equalsIgnoreCase(et2.getText().toString())){
				err="Password can not be blank";
			}
			if(err!=null){
				Toast.makeText(UpdatePass.this, err, 2000).show();
				return;
			}
			db.execSQL(sql, ob);
			db.close();
			Toast.makeText(UpdatePass.this, "Saved", 2000).show();
			UpdatePass.this.finish();
 		}
        }); 	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_update_pass, menu);
        return true;
    }
}
