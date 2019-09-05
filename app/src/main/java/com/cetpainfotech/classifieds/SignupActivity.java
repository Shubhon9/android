package com.cetpainfotech.classifieds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {

    EditText input_id;
    EditText input_password;
    EditText input_contact;
    Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();

        // find all widgets
        input_id = (EditText) findViewById(R.id.input_id);
        input_password = (EditText) findViewById(R.id.input_password);
        input_contact = (EditText) findViewById(R.id.input_phone);
        btn_signup = (Button) findViewById(R.id.btn_signup);

        // event handler of signupbutton
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });


    }

    // signup function
    public void signup() {

        // if not validate
        if (!validate()) {
            // signu failed snackbar
            onSignupFailed("Validation Failed");
            return;
        }


        // signup buttont true
        btn_signup.setEnabled(false);



        // get all fields information
        String id = input_id.getText().toString();
        String password = input_password.getText().toString();
        String contact = input_contact.getText().toString();

        new SignupAttempt().execute("http://192.168.43.25/classifieds/sign_up.php?user_id=" + id + "&pass=" + password + "&phone=" + contact);


    }

    class SignupAttempt extends AsyncTask<String,Void,String>
    {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SignupActivity.this);
            pd.setMessage("Please Wait..");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String content = null;

                while((content=br.readLine()) != null)
                {
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

            if(s!=null)
            {
                try {
                    JSONObject jo = new JSONObject(s);
                    int success = jo.getInt("success");
                    if(success == 1)
                    {
                        String message = jo.getString("message");
                        Toast.makeText(SignupActivity.this,message,Toast.LENGTH_SHORT).show();
                        finish();

                    }
                    else
                    {
                        String message = jo.getString("message");
                        onSignupFailed(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pd.dismiss();

            }

            super.onPostExecute(s);
        }
    }


    // signupd failed
    public void onSignupFailed(String message) {

        // failed snackbar
        Snackbar.make(findViewById(R.id.btn_signup), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        // sign button enabled
        btn_signup.setEnabled(true);
    }


    // validate
    public boolean validate() {
        boolean valid = true;


        // get fields information

        String id = input_id.getText().toString();
        String phone = input_contact.getText().toString();
        String password = input_password.getText().toString();

        if (id.isEmpty() || id.length()<3) {
            input_id.setError("enter a valid id");
            valid = false;
        } else {
            input_id.setError(null);
        }

        if (phone.isEmpty() || !phone.matches("\\d{10}")) {
            input_contact.setError("enter a phone number");
            valid = false;
        } else {
            input_contact.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            input_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }  else {
            input_password.setError(null);
        }

        return valid;
    }
}
