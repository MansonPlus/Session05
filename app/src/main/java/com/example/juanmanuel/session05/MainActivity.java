package com.example.juanmanuel.session05;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity {

    private TextView textResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = (TextView)findViewById(R.id.result);

        Button btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadWebPageTask().execute("http://cultura.ding.mx/estados/getCiudades/34");
            }
        });


    }

    //TODO: add progress dialog at the beggining and close it at the end.

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {

        //Main Thread
        @Override
        protected void onPreExecute() {

        }

        //Main Thread
        @Override
        protected void onProgressUpdate(Void... values) {

        }

        //Another thread
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        //Main Thread
        @Override
        protected void onPostExecute(String result) {
            textResult.setText(result);
        }
    }
}
