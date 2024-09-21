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
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Viewvotingstatus extends Activity implements JsonResponse, OnItemClickListener {

	ListView l1;
    SharedPreferences sh;
    public static String eids,statusss,elections;

    String[] eid,title,date,statuss,details;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewvotingstatus);
		
		sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1 = (ListView) findViewById(R.id.listView1);
        l1.setOnItemClickListener(this);
        
        
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Viewvotingstatus.this;
        String q = "/viewelection/";
        JR.execute(q);
//        Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
        Log.d("notification_log", q);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewvotingstatus, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		eids=eid[arg2];
		elections=title[arg2];
		statusss=statuss[arg2];
		
		if(statusss.equalsIgnoreCase("start"))
		{
			startActivity(new Intent(getApplicationContext(),MakeVote.class));
		}
		else{
			Toast.makeText(getApplicationContext(), "Election Finished", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		try {
            String status = jo.getString("status");
            Log.d("result", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = (JSONArray) jo.getJSONArray("data");
                if (ja.length() > 0) {
                	eid = new String[ja.length()];
                	title = new String[ja.length()];
                	date = new String[ja.length()];
                    statuss = new String[ja.length()];
                    details = new String[ja.length()];
                    
                    for (int i = 0; i < ja.length(); i++) {
                    	eid[i] = ja.getJSONObject(i).getString("election_id");
                    	title[i] = ja.getJSONObject(i).getString("titile");
                        date[i] = ja.getJSONObject(i).getString("ele_date");
                        statuss[i] = ja.getJSONObject(i).getString("vot_status");
                        details[i] ="Election : "+title[i]+"\nDate : "+date[i]+"\nstatus : "+statuss[i]+"\n";
                    }
                    l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, details));
                }
            } else {
                Toast.makeText(getApplicationContext(), "No Election..!!", Toast.LENGTH_LONG).show();
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
}
