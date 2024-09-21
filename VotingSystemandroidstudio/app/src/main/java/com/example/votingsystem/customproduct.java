package com.example.votingsystem;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class customproduct extends ArrayAdapter<String> { 
	//needs to extend ArrayAdapter
    private String[] image;  
    private String[] name;  
    //for custom view photo items
    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    public customproduct(Activity context, String[] name,String[] image) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.custom_products, name);
        this.context = context;
        
        this.image = image;
        this.name=name;
        
        
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
                 //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.custom_products, null, true);
		//cust_list_view is xml file of layout created in step no.2

        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
        TextView tv = (TextView) listViewItem.findViewById(R.id.textView1);
        tv.setText(name[position]);
        
        sh=PreferenceManager.getDefaultSharedPreferences(getContext());
        
       String pth = "http://"+IPsettings.ip+image[position];
       pth = pth.replace("~", "");
//        Toast.makeText(getContext(), "dsgfs"+pth, Toast.LENGTH_LONG).show();
        Log.d("-------------", pth);
        Picasso.with(context)
                .load(pth)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher).into(im);
        
        return  listViewItem;
    }

	private TextView setText(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
