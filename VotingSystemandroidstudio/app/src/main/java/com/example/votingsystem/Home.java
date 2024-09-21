package com.example.votingsystem;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;


public class Home extends Activity {

    Button b1,b3,b4,b5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        b1 = (Button) findViewById(R.id.bt_category);
//        b2 = (Button) findViewById(R.id.bt_candidate);
        b3 = (Button) findViewById(R.id.bt_result);
        b4 = (Button) findViewById(R.id.bt_Vote);
        b5 = (Button) findViewById(R.id.bt_logout);

       

       

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewCategory.class));
            }
        });

//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), ViewCadidates.class));
//            }
//        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewResult.class));
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Viewvotingstatus.class));
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("uid", "0");
                ed.commit();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Home.class));
    }
}
