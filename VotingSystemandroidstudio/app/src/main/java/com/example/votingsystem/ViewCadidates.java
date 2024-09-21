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

public class ViewCadidates extends Activity implements JsonResponse {
	ListView l1;
    SharedPreferences sh;
    public static String cids;

    String[] candidate,image,details;
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_cadidates);
		
		sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1 = (ListView) findViewById(R.id.listView1);
//        l1.setOnItemClickListener(this);
        
        
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewCadidates.this;
        String q = "/viewcandidate/?vcatid="+ViewCategory.cids;
        JR.execute(q);
//        Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
        Log.d("notification_log", q);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_cadidates, menu);
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
                	image = new String[ja.length()];
//                    details = new String[ja.length()];
                    
                    for (int i = 0; i < ja.length(); i++) {
                    	candidate[i] = ja.getJSONObject(i).getString("name");
                        image[i] = ja.getJSONObject(i).getString("photo");
//                        details[i] ="Election : "+election[i]+"\nCategory : "+category[i]+"\n";
                    }
//                    l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, details));
                    l1.setAdapter(new customproduct(this, candidate,image));
                }
            } else {
                Toast.makeText(getApplicationContext(), "No Candidates..!!", Toast.LENGTH_LONG).show();
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
