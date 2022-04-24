package com.abhishek.okhttplogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mTextViewResult = findViewById(R.id.text_view_result);

      /*  MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "{"jsonExample":"value"}");*/

        MediaType JSON
                = MediaType.get("application/json; charset=utf-8");





        RequestBody formBody = new FormBody.Builder()
                .addEncoded("course_id","1")
                .build();

        OkHttpClient client = new OkHttpClient();

        String url = "https://wscubetech.org/android-course/get-data.php";

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(myResponse);
                        System.out.println(jsonObject );
                        String name = jsonObject.getString("stauts");
                        System.out.println(name);

                    } catch (JSONException e) {
                        e.printStackTrace( );
                    }

                 /*   String sname = jsonObject.getString("username");
                        String semail = jsonObject.getString("email");*/

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewResult.setText(myResponse);
                        }
                    });
                }

            }
        });
    }
}