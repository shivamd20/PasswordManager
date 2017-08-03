package com.ssipmt.passmgr;

import java.util.HashMap;
import java.util.LinkedList;


import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

public class SearchPass extends Activity {

	int itemId;	
	HashMap m1 = new HashMap();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pass);
        
        Button b1 = (Button) findViewById(R.id.button1);
        Log.d("afr", "1");
        b1.setOnClickListener(new OnClickListener() {
 		public void onClick(View arg0) {
 	        Log.d("afr", "2");
 			SQLiteDatabase db = openOrCreateDatabase("pass_db", MODE_PRIVATE, null);
 	        String sql = "select * from pass_mgr where uname = ? and app_name like ? ";
 	        
			EditText et1 = (EditText) findViewById(R.id.editText1);

			String s[] = new String[2];
			s[0]=PassMgrApp.logedInName;
			s[1]="%"+et1.getText().toString()+"%";
 	        Cursor c1 = db.rawQuery(sql, s);
 	        int in1 = c1.getColumnIndex("app_name");
 	        int in2 = c1.getColumnIndex("pass");
 	        int in3 = c1.getColumnIndex("id");
 	        LinkedList<String> sa = new LinkedList<String>();
 	        m1.clear();
 	        int i =0;
 	        while(c1.moveToNext()){
 	        	String name = c1.getString(in1);
 	        	String pass = c1.getString(in2);
 	        	String id = c1.getString(in3);
 	        	sa.add(name + " : " + pass);
 	        	//sa.add(name);
 	        	m1.put(i++, id);
 	 	        Log.d("afr", ""+id);
 	        }
 	        db.close();        
 	        ArrayAdapter<String> aa = new ArrayAdapter<String>(SearchPass.this, android.R.layout.simple_list_item_1, sa);
 	        ListView lv1 = (ListView) findViewById(R.id.listView1);
 	        lv1.setAdapter(aa); 
 	        lv1.setOnItemLongClickListener(new OnItemLongClickListener() {
 				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
 						int arg2, long arg3) {
 					itemId = arg2;
 					return false;
 				}
 			});
 	        registerForContextMenu(lv1);
 		}
        });
    }
	
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	if(item.getItemId() == R.id.item3){
            SQLiteDatabase db = openOrCreateDatabase("pass_db", MODE_PRIVATE, null);
            String sid = (String)m1.get(itemId);
            String sql = "delete from pass_mgr where id = " + sid;
            db.execSQL(sql);
            db.close();
    		Toast.makeText(SearchPass.this,"Deleted!!", 2000).show();
    		startActivity(getIntent());
    		SearchPass.this.finish();
    	}
    	else if(item.getItemId() == R.id.item1){
			SQLiteDatabase db1 = openOrCreateDatabase("pass_db", MODE_PRIVATE, null);
			String sql1 = "select * from pass_mgr where id = ? ";
			String s[] = new String[1];
            String sid = (String)m1.get(itemId);
			s[0]=sid;
			Cursor c2 = db1.rawQuery(sql1, s);
			int in1 = c2.getColumnIndex("pass");
			if(c2.moveToFirst()){
				String pass = PMUtil.dec(c2.getString(in1));
				Toast t1 = Toast.makeText(SearchPass.this, pass, 2000);
				t1.setGravity(Gravity.CENTER, 0, -250);
				t1.show();
			}
			db1.close();
    	}
    	else if(item.getItemId() == R.id.item2){
            String sid = (String)m1.get(itemId);
            Intent i1 = new Intent(SearchPass.this, UpdatePass.class);
            i1.putExtra("sid", sid);
            startActivity(i1);
            SearchPass.this.finish();
    	}

    	return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search_pass, menu);
        return true;
    }
    
    
}
