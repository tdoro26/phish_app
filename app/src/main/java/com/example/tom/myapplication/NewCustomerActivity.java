package com.example.tom.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewCustomerActivity extends AppCompatActivity {

    private EditText fName;
    private EditText sName;
    private static final String TAG = "NewCustomerActivity";
    private final String TEXT_CONTENTS = "TextContents";
    private String url_create_customer = "http://192.168.1.146/android_connect/create_customer.php";
    private HttpURLConnection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        Log.d(TAG, "onCreate: in");
        fName = (EditText) findViewById(R.id.fName_txtbx);
        sName = (EditText) findViewById(R.id.sName_txtbx);
        Button button = (Button) findViewById(R.id.addButton);
        // add customer click event
        View.OnClickListener ourOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName_result = fName.getText().toString().trim(); // getText() returns an Editable therefore need toString()
                String sName_result = sName.getText().toString().trim();
                fName.setText("");
                sName.setText("");
                /** Don't want to start new intent, want to create new customer and
                 * present a pop up saying so
                 */
                new postToServer().execute(fName_result, sName_result);
            }
        };

        button.setOnClickListener(ourOnClickListener);
        Log.d(TAG, "onCreate: out");
    }

    class postToServer extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... args) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(url_create_customer);
                con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                //con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestMethod("POST");

                con.setRequestProperty("Content-Language", "en-US");

                JSONObject custy = new JSONObject();
                custy.put("forename", args[0]);
                custy.put("surname", args[1]);

                // Send POST output
                OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
                os.write(custy.toString());
                os.close();

                /** Alternate way with OutputStream **/
                //OutputStream os = con.getOutputStream();
                //os.write(custy.toString().getBytes("UTF-8"));  // .getBytes("UTF-8"));
                //os.flush();
                //os.close();

                //display what returns the POST request
                int HttpResult = con.getResponseCode();
                System.out.println(HttpResult);
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(con.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println("" + sb.toString());
                } else {
                    System.out.println(con.getResponseMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
            }
            return sb.toString();
        }
    }
}
