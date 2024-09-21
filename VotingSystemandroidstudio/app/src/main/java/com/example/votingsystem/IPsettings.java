package com.example.votingsystem;



import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IPsettings extends Activity {
	EditText etip;
	Button btip;
	
	SharedPreferences sh;
	public static String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipsettings);
        etip=(EditText)findViewById(R.id.etip);
        
        btip=(Button)findViewById(R.id.btsubmit);
        
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		etip.setText(sh.getString("ip", ""));
        
        btip.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				ip=etip.getText().toString();
				 if(etip.getText().toString().equals(""))
				 {
					 etip.setError("Enter IP Address");
					 etip.setFocusable(true);
				 }
				 else
				 {
					 Editor e=sh.edit();
					 e.putString("ip", ip);
					 e.commit();
					 startActivity(new Intent(getApplicationContext(),Login.class));
				 }
				
			}
		});

    }


    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit  :")
                .setMessage("Exit ...?")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        // TODO Auto-generated method stub
                        Intent i=new Intent(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("No",null).show();

    }
 
}
