package com.cetpainfotech.classifieds;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.HandlerThread;
import java.net.URLEncoder;
import java.util.Map;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.util.HashMap;
import java.io.OutputStreamWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.UnsupportedEncodingException;

public class Post_Ads extends AppCompatActivity {

    EditText etTitle, etDescription,etPrice;
    ImageView image;
    Button btpost;
    //String ImageName = "image_data" ;
        int RC;
        StringBuilder stringBuilder;
        boolean check = true;
        private  int PICK_IMAGE_REQUEST=1;
        private  Uri uri;
        private  Bitmap bitmap;
        private  URL url;
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;

        public  static  final  String UPLOAD_URL="http://192.168.43.25/classifieds/upload2.php";
        public  static  final  String UPLOAD_KEY="post_image";



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_post__ads);

            etTitle = (EditText)findViewById(R.id.gettitle);
        etDescription = (EditText)findViewById(R.id.etdescription);
        etPrice=(EditText)findViewById(R.id.etprice);

        image =(ImageView)findViewById(R.id.imageView);
        image.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View v) {
                Intent i10 = new Intent();
                i10.setType("image/*");
                i10.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i10,"select pictures"), PICK_IMAGE_REQUEST);

            }
        })

        ;



            btpost = (Button) findViewById(R.id.btpost);

        btpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ptitle = etTitle.getText().toString();
                String pdesc = etDescription.getText().toString();
                String pprice = etPrice.getText().toString();

                new PostAttempt().execute("http://192.168.43.25/classifieds/post.php?post_title=" + ptitle + "&post_description=" + pdesc + "&post_price=" + pprice);
                new uploadImage().execute();
                Intent intent = new Intent(Post_Ads.this,HomeActivity.class);
                startActivity(intent);
            }
        });


    }

//    @Override
//    public void onBackPressed() {
//        new AlertDialog.Builder(Post_Ads.this)
//                .setMessage("want to leave this page")
//                .setTitle("Classified")
//                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent i = new Intent(Post_Ads.this, HomeActivity.class);
//                            startActivity(i);
//                    }
//                }).setNegativeButton("cancel", null);
//    }
//
    public String getStringImage (Bitmap bitmap)
    {
     ByteArrayOutputStream baos= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imgagebytes= baos.toByteArray();
        String encodededimage= Base64.encodeToString(imgagebytes, Base64.DEFAULT);
        return encodededimage;

    }
     @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
          if(requestCode==PICK_IMAGE_REQUEST&& resultCode==RESULT_OK && data!= null && data.getData() != null)
          {
             //image
              uri= data.getData();
              try {
                  Bitmap bitmap= MediaStore.Images.Media.getBitmap(
                          getContentResolver(), uri);


                    ImageView iv = findViewById(R.id.imageView);

                    iv.setImageBitmap(bitmap);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }

    }
    class uploadImage extends AsyncTask<Void,Void,String> {
        ProgressDialog loading;
      //  RequestHandler rh=new Handler();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(Post_Ads.this, "Uploading Image", "Please wait...", true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Void... params) {
            impclass impclass= new impclass();
            HashMap<String, String> data = new HashMap<String, String>();
            data.put(UPLOAD_KEY, getStringImage(bitmap));
            String finaldata= impclass.ImageHttpRequest(UPLOAD_URL,data);

            return finaldata;



        }

    }
    public class impclass{
        public  String ImageHttpRequest(String requestURL, HashMap<String,String>PData)
        {
            StringBuilder stringBuilder=new StringBuilder();
            try{
                url=new URL(requestURL);
                httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(20000);
                httpURLConnection.setConnectTimeout(20000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);
                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(bufferedWriterDataFN(PData));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();
                if (RC == HttpURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReader.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }
        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilder.toString();


    }
        }



    class PostAttempt extends AsyncTask<String,Void,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Post_Ads.this);
            pd.setMessage("Please Wait..");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String content = null;

                while ((content = br.readLine()) != null) {
                    sb.append(content);
                }

                return sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (s != null) {
                try {
                    JSONObject jo = new JSONObject(s);
                    int success = jo.getInt("success");
                    if (success == 1) {

                        String message = jo.getString("message");
                        Toast.makeText(Post_Ads.this, message, Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        String message = jo.getString("message");
                        Toast.makeText(Post_Ads.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pd.dismiss();

            }

            super.onPostExecute(s);
        }

    }


}
