package com.mobilesiri.okhttp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by MurtazaAli on 1/30/2016.
 */
public class PostActivity extends Activity{

    TextView output;
    Button bsend;
    EditText etName, etPass;
    final String url = "http://serviceapi.skholingua.com/open-feeds/display_received_params.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        output = (TextView) findViewById(R.id.tvpost);
        etName = (EditText) findViewById(R.id.etName);
        etPass = (EditText) findViewById(R.id.etPass);
        bsend = (Button) findViewById(R.id.bSend);
        bsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etName.getText().toString();
                String pass = etPass.getText().toString();
                OkHttp  handler = new OkHttp(userName, pass);
                String result = null;
                try{
                    result = handler.execute(url).get();
                }catch (ExecutionException e){
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                output.append(result + "\n");
            }
        });
    }
    private class OkHttp extends AsyncTask<String, Void, String>{

        OkHttpClient client = new OkHttpClient();
        String userName, pass;
        public OkHttp(String userName, String pass){
            this.userName = userName;
            this.pass = pass;
        }
        @Override
        protected String doInBackground(String... params) {

            RequestBody body = new FormEncodingBuilder()
                    .add("name", userName)
                    .add("pass", pass).build();
            Request request = new Request.Builder()
                    .url(params[0]).post(body).build();
            try{
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code" + response.toString());
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
