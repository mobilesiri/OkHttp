package com.mobilesiri.okhttp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class MainActivity extends Activity {

    Button bstart, bpost;
    ImageView imageView;
    TextView tv;
    private final String url = "http://serviceapi.skholingua.com/images/skholingua_image.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.Iview);
        tv = (TextView) findViewById(R.id.tvBytes);
        bstart =(Button) findViewById(R.id.bStart);

        bstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               OkHttpHandler handler = new OkHttpHandler();

                byte[] image = new byte[0];
                try{
                    image = handler.execute(url).get();
                    if (image != null && image.length > 0){
                        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                        imageView.setImageBitmap(bitmap);
                        tv.setText("Total btytes download: "+ image.length);
                    }
                }catch (Exception e){
                    tv.setText("sorry, something went wrong!");
                }
            }
        });
        bpost = (Button) findViewById(R.id.bpost);
        bpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PostActivity.class);
                startActivity(i);
            }
        });

    }
    public class OkHttpHandler extends AsyncTask<String, Void, byte[]> {

        OkHttpClient client =  new OkHttpClient();

        @Override
        protected byte[] doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().bytes();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

}
