package com.example.votingsystem;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewCategory extends Activity implements OnItemClickListener,JsonResponse {

	ListView l1;
    SharedPreferences sh;
    public static String cids;

    String[] cid,election,category,details;
	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_category);
		
		
		sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1 = (ListView) findViewById(R.id.listView1);
        l1.setOnItemClickListener(this);
        
        
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewCategory.this;
        String q = "/viewcategory/";
        JR.execute(q);
//        Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
        Log.d("notification_log", q);
        
	}

	
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
            String status = jo.getString("status");
            Log.d("result", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = (JSONArray) jo.getJSONArray("data");
                if (ja.length() > 0) {
                	cid = new String[ja.length()];
                	election = new String[ja.length()];
                    category = new String[ja.length()];
                    details = new String[ja.length()];
                    
                    for (int i = 0; i < ja.length(); i++) {
                    	cid[i] = ja.getJSONObject(i).getString("cat_id");
                        election[i] = ja.getJSONObject(i).getString("titile");
                        category[i] = ja.getJSONObject(i).getString("cat_name");
                        details[i] ="Election : "+election[i]+"\nCategory : "+category[i]+"\n";
                    }
                    l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, details));
                }
            } else {
                Toast.makeText(getApplicationContext(), "No Category..!!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Home.class));
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_category, menu);
		return true;
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		
		cids=cid[arg2];
		startActivity(new Intent(getApplicationContext(),ViewCadidates.class));
	}

}
