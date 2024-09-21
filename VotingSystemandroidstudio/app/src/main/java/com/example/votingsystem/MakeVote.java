package com.example.votingsystem;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MakeVote extends Activity implements JsonResponse, OnItemSelectedListener {

	TextView t1;
	Spinner s1,s2;
	Button b1;
	String[] cid,category,cadid,candidate;
	public static String catid,candid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_vote);
		
		t1=(TextView)findViewById(R.id.textView1);
		s1=(Spinner)findViewById(R.id.spinner1);
		s1.setOnItemSelectedListener(this);
		s2=(Spinner)findViewById(R.id.spinner2);
		s2.setOnItemSelectedListener(this);
		b1=(Button)findViewById(R.id.button1);
		
		t1.setText(Viewvotingstatus.elections);
		
		 JsonReq JR=new JsonReq();
	        JR.json_response=(JsonResponse) MakeVote.this;
	        String q = "/viewcategorys/?eleid="+Viewvotingstatus.eids;
	        JR.execute(q);
//	        Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
	        Log.d("notification_log", q);
	        
	        b1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					JsonReq JR=new JsonReq();
			        JR.json_response=(JsonResponse) MakeVote.this;
			        String q = "/vote/?catid="+catid+"&candid="+candid+"&lid="+Login.logid;
			        JR.execute(q);
//			        Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
			        Log.d("notification_log", q);
				}
			});
	        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.make_vote, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
		if(arg0==s1)
		{
			catid=cid[arg2];
			JsonReq JR=new JsonReq();
	        JR.json_response=(JsonResponse) MakeVote.this;
	        String q = "/viewcandidates/?catid="+cid[arg2];
	        JR.execute(q);
//	        Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
	        Log.d("notification_log", q);
		}
		else if(arg0==s2)
		{
			candid=cadid[arg2];
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		try {
            String method = jo.getString("method");
            Log.d("method", method);
            if (method.equalsIgnoreCase("vote")) {


                try {
                    String status = jo.getString("status");
                    Log.d("result", status);

                    if (status.equalsIgnoreCase("success")) {
                        Toast.makeText(getApplicationContext(), "Success.!!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Home.class));
                    }
                    if (status.equalsIgnoreCase("voted")) {
                    	Toast.makeText(getApplicationContext(), "You are already voted in this category", Toast.LENGTH_LONG).show();
                    }
                    	else {
                    
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
            if (method.equalsIgnoreCase("viewcategorys")) {
            	
            	try {
                    String status = jo.getString("status");
                    Log.d("result", status);

                    if (status.equalsIgnoreCase("success")) {
                        JSONArray ja = (JSONArray) jo.getJSONArray("data");
                        if (ja.length() > 0) {
                        	cid = new String[ja.length()];
                            category = new String[ja.length()];
                            
                            for (int i = 0; i < ja.length(); i++) {
                            	cid[i] = ja.getJSONObject(i).getString("cat_id");
                                category[i] = ja.getJSONObject(i).getString("cat_name");
                            }
                            s1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, category));
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
            if (method.equalsIgnoreCase("viewcandidates")) {
            	
            	try {
                    String status = jo.getString("status");
                    Log.d("result", status);

                    if (status.equalsIgnoreCase("success")) {
                        JSONArray ja = (JSONArray) jo.getJSONArray("data");
                        if (ja.length() > 0) {
                        	cadid=new String[ja.length()];
                        	candidate = new String[ja.length()];
                        	
//                            details = new String[ja.length()];
                            
                            for (int i = 0; i < ja.length(); i++) {
                            	candidate[i] = ja.getJSONObject(i).getString("name");
                                cadid[i] = ja.getJSONObject(i).getString("candid_id");
//                                details[i] ="Election : "+election[i]+"\nCategory : "+category[i]+"\n";
                            }
                           s2.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, candidate));
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
		}
		catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
	}

}
