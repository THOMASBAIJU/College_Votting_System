package com.example.votingsystem;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Login extends Activity implements JsonResponse{
	EditText e1;
	ImageButton ib1;
	Button b1;
	String uname,pass;
	SharedPreferences sh;
	public static String logid;
	
	public String encodedImage = "", path = "";
	public static String labels,pre;

	private final int GALLERY_CODE = 201;
	private Uri mImageCaptureUri;

	String fln, ftype, fname, upLoadServerUri;

	byte[] byteArray;

	File f = null;

	private String imagename = "";
	public static Bitmap image;

	private static final int CAMERA_CODE = 101,  CROPING_CODE = 301,READ_EXTERNAL_STORAGE_PERMISSION=1,CAMERA=2;
	  


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		e1=(EditText)findViewById(R.id.etusername);
//		e2=(EditText)findViewById(R.id.etpassword);
		b1=(Button)findViewById(R.id.btlogin);
		ib1=(ImageButton)findViewById(R.id.imageButton1);
		
		
		ib1.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

		        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
		        builder.setTitle("Add Photo!");
		        builder.setItems(items, new DialogInterface.OnClickListener() 
		        {
		            @Override
		            public void onClick(DialogInterface dialog, int item) {

		                if (items[item].equals("Capture Photo")) 
		                {
		                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		                    Date date = new Date();
		                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy-mm-ss");
		                    imagename = df.format(date) + ".jpg";
		                    f = new File(android.os.Environment.getExternalStorageDirectory(), imagename);
		                    mImageCaptureUri = Uri.fromFile(f);
		                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
		                    startActivityForResult(intent, CAMERA_CODE);

		                } else if (items[item].equals("Choose from Gallery")) {
		                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		                    startActivityForResult(i, GALLERY_CODE);

		                }  else if (items[item].equals("Cancel")) {
		                    dialog.dismiss();
		                }
		            }
		        });
		        builder.show();
			//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				//startActivityForResult(i, GALLERY_CODE);
			}
			
		});

		
		
		
		
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(e1.getText().toString().equals(""))
				{
					e1.setError("Enter a Username");
					e1.setFocusable(true);
				}
//				else if(e2.getText().toString().equals(""))
//				{
//					e2.setError("Enter a Password");
//					e2.setFocusable(true);
//				}
				else
				{
					uname=e1.getText().toString();
					sendAttach();
//					pass=e2.getText().toString();
					
//					JsonReq JR= new JsonReq(getApplicationContext());
//					JR.json_response=(JsonResponse)Login.this;
//					String q="login?username="+uname+"&password="+pass;
//					q=q.replace(" ","%20");
//					JR.execute(q);
//					Toast.makeText(getApplicationContext(), uname, Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}


    private void sendAttach() {
        // TODO Auto-generated method stub

        try {
//            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            String uid = sh.getString("uid", "");

            String q = "http://" +IPsettings.ip+"/api/login/?";

//   Toast.makeText(getApplicationContext(), "Byte Array:"+byteArray.length, Toast.LENGTH_LONG).show();
//   Toast.makeText(getApplicationContext(), "User:"+uname, Toast.LENGTH_LONG).show();
            
            Map<String, byte[]> aa = new HashMap<String, byte[]>();
           
            aa.put("image",byteArray);
            aa.put("username",uname.getBytes());
            Log.d(q,"");
            FileUploadAsync fua = new FileUploadAsync(q);
            fua.json_response = (JsonResponse) Login.this;
            fua.execute(aa);
//            String data = fua.getResponse();
//            stopService(new Intent(getApplicationContext(),Capture.class));
//            Log.d("response=======", data);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
        }
    }
    
	
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		try
		{
			
			Toast.makeText(getApplicationContext(), uname, Toast.LENGTH_LONG).show();
			
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("login"))
			{
				String status=jo.getString("status");
				Log.d("result", status);
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					logid=ja.getJSONObject(0).getString("log_id");
					
					Editor e=sh.edit();
					e.putString("logid", logid);
					e.commit();
					
						startActivity(new Intent(getApplicationContext(),Home.class));
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Login Failed.\nTry Again.", Toast.LENGTH_LONG).show();
				}
						
			}
			
					
			
		}catch(Exception e)
		{
			
			Toast.makeText(getApplicationContext(), "Exception "+e+" Occured.", Toast.LENGTH_SHORT).show();
			
		}
		
	}
	 public void onBackPressed() 
		{
			// TODO Auto-generated method stub
			super.onBackPressed();
			Intent b=new Intent(getApplicationContext(),IPsettings.class);			
			startActivity(b);
		}
	 
	 
	 private String getRealPathFromURI(Uri contentURI)
		{
			String path;
			Cursor cursor = getContentResolver()
					.query(contentURI, null, null, null, null);
			if (cursor == null)
				path = contentURI.getPath();

			else {
				cursor.moveToFirst();
				int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
				path = cursor.getString(idx);

			}
			if (cursor != null)
				cursor.close();
			return path;
		}

		protected void onActivityResult(int requestCode, int resultCode, Intent data) 
		{
	        super.onActivityResult(requestCode, resultCode, data);
	        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

	            mImageCaptureUri = data.getData();
	            System.out.println("Gallery Image URI : " + mImageCaptureUri);
	            //   CropingIMG();

	            Uri uri = data.getData();
	            Log.d("File Uri", "File Uri: " + uri.toString());
	            // Get the path
	            //String path = null;
	            try {
	                path = FileUtils.getPath(this, uri);
	                Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

	                //tv1.setText(path.substring(path.lastIndexOf("/") + 1));

	                File fl = new File(path);
	                int ln = (int) fl.length();

	                InputStream inputStream = new FileInputStream(fl);
	                ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                byte[] b = new byte[ln];
	                int bytesRead = 0;

	                while ((bytesRead = inputStream.read(b)) != -1) {
	                    bos.write(b, 0, bytesRead);
	                }
	                inputStream.close();
	                byteArray = bos.toByteArray();

	                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
	                ib1.setImageBitmap(bit);
	                
	                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
	                encodedImage = str;
//	                pb.setVisibility(View.VISIBLE);
//	                sendAttach();
	            } catch (Exception e) {
	                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	            }
	        } else if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {
	            System.out.println("Camera Image URI : " + mImageCaptureUri);
	            //  CropingIMG();

	            path = f.getAbsolutePath();

	            // image = BitmapFactory.decodeFile(path);
	            image = decodeFile(path);
	            
	            try {

	                int ln = (int) f.length();
	                byteArray = null;

	                InputStream inputStream = new FileInputStream(f);
	                ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                byte[] b = new byte[ln];
	                int bytesRead = 0;

	                while ((bytesRead = inputStream.read(b)) != -1) {
	                    bos.write(b, 0, bytesRead);
	                }
	                inputStream.close();
	                byteArray = bos.toByteArray();

	                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
	                ib1.setImageBitmap(bit);
	                
	                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
	                encodedImage = str;
//	                pb.setVisibility(View.VISIBLE);
//	                  sendAttach();
	            } catch (Exception e) {

	            }

	        } 
	    }
		
		public Bitmap decodeFile(String path) {//you can provide file path here
	        int orientation;
	        try {
	            if (path == null) {
	                return null;
	            }
	            // decode image size
	            //    BitmapFactory.Options o = new BitmapFactory.Options();
	            BitmapFactory.Options o = new BitmapFactory.Options();
	            o.inPreferredConfig = Bitmap.Config.RGB_565;
	            o.inJustDecodeBounds = true;
	            // Find the correct scale value. It should be the power of 2.
	            final int REQUIRED_SIZE = 70;
	            int width_tmp = o.outWidth, height_tmp = o.outHeight;
	            int scale = 0;
	            while (true) {
	                if (width_tmp / 2 < REQUIRED_SIZE
	                        || height_tmp / 2 < REQUIRED_SIZE)
	                    break;
	                width_tmp /= 2;
	                height_tmp /= 2;
	                scale++;
	            }
	            // decode with inSampleSize
	            //  BitmapFactory.Options o2 = new BitmapFactory.Options();
	            BitmapFactory.Options o2 = new BitmapFactory.Options();
	            o2.inPreferredConfig = Bitmap.Config.RGB_565;
	            o2.inSampleSize = scale;
	            Bitmap bm = BitmapFactory.decodeFile(path, o2);
	            Bitmap bitmap = bm;

	            ExifInterface exif = new ExifInterface(path);

	            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

	            Log.e("ExifInteface .........", "rotation =" + orientation);

	            //exif.setAttribute(ExifInterface.ORIENTATION_ROTATE_90, 90);

	            Log.e("orientation", "" + orientation);
	            Matrix m = new Matrix();

	            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
	                m.postRotate(180);
	                //m.postScale((float) bm.getWidth(), (float) bm.getHeight());
	                // if(m.preRotate(90)){
	                Log.e("in orientation", "" + orientation);
	                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
	                return bitmap;
	            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
	                m.postRotate(90);
	                Log.e("in orientation", "" + orientation);
	                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
	                return bitmap;
	            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
	                m.postRotate(270);
	                Log.e("in orientation", "" + orientation);
	                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
	                return bitmap;
	            }
	            return bitmap;
	        } catch (Exception e) 
	        {
	            return null;
	        }
	    }

		public String getPath(Context context, Uri uri) throws URISyntaxException 
	    {
	        if ("content".equalsIgnoreCase(uri.getScheme())) 
	        {
	            String[] projection = { "_data" };
	            Cursor cursor = null;

	            try 
	            {
	                cursor = context.getContentResolver().query(uri, projection, null, null, null);
	                int column_index = cursor.getColumnIndexOrThrow("_data");
	                if (cursor.moveToFirst()) 
	                {
	                    return cursor.getString(column_index);
	                }
	            } catch (Exception e)
	            {
	                // Eat it
	            }
	        }
	        
	        else if ("file".equalsIgnoreCase(uri.getScheme()))
	        {
	            return uri.getPath();
	        }
	        return null;
	        
	    }
}
