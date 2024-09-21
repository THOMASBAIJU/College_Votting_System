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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewResult extends Activity implements JsonResponse {

	ListView l1;
    SharedPreferences sh;
    public static String cids;

    String[] vote,candidate,category,details;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_result);
		
		sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1 = (ListView) findViewById(R.id.listView1);
        
        
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewResult.this;
        String q = "/viewresult/";
        JR.execute(q);
//        Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
        Log.d("notification_log", q);
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_result, menu);
		return true;
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
                	candidate = new String[ja.length()];
                	category = new String[ja.length()];
                    vote = new String[ja.length()];
                    details = new String[ja.length()];
                    
                    for (int i = 0; i < ja.length(); i++) {
                    	candidate[i] = ja.getJSONObject(i).getString("name");
                        category[i] = ja.getJSONObject(i).getString("cat_name");
                        vote[i] = ja.getJSONObject(i).getString("no_votes");
                        details[i] ="Candidate : "+candidate[i]+"\nCategory : "+category[i]+"\nNo Of Votes : "+vote[i]+"\n";
                    }
                    l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, details));
                }
            } else {
                Toast.makeText(getApplicationContext(), "No Result..!!", Toast.LENGTH_LONG).show();
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
