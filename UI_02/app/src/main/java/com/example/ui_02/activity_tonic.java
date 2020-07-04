package com.example.ui_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class activity_tonic extends AppCompatActivity {
    private static String IP_ADDRESS = "192.168.0.61";
    private static String TAG = "phptest";
    private TextView today;
    private TextView mTextViewResult;
    private ArrayList<DoseInfo> myarrayList;
    private DoseAdapter DoseAdapter;
    private RecyclerView mRecyclerView;
    private String myJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tonic);

        mTextViewResult = findViewById(R.id.textView_main_result); //디버그 확인용
        today = findViewById(R.id.today);//오늘 날짜 표시
        mRecyclerView = (RecyclerView) findViewById(R.id.Re_main_list);//db에 있는거 불러와주는곳
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        myarrayList = new ArrayList<>();
        DoseAdapter = new DoseAdapter(this,myarrayList);

        mRecyclerView.setAdapter(DoseAdapter);

        String userID = UserInfo.getUserID();
        myarrayList.clear();
        DoseAdapter.notifyDataSetChanged();
        ShowData task = new ShowData();
        task.execute( "http://" + IP_ADDRESS + "/testquery.php", userID);

        //오늘 날짜를 알려줌
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("MM-dd hh:mm");
        String getTime = simpleDate.format(mDate);
        today.setText(getTime);

    }

    private class ShowData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(activity_tonic.this,
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null){
                mTextViewResult.setText(errorString);
            }
            else {
                myJsonString = result;
                ShowResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = "http://"+ IP_ADDRESS +"/testquery.php";
            String postParameters = "userID=" + params[1];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }
    private void ShowResult(){

        String TAG_JSON="webnautes";
      //  String TAG_CODE = "D.d_code";
        String TAG_USER = "D.userID";
        String TAG_DATE ="D.d_date";
     //   String TAG_NCODE ="D.n_code";
        String TAG_NAME = "N.n_name";

        try {
            JSONObject jsonObject = new JSONObject(myJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

              //  String d_code = item.getString(TAG_CODE);
                String userID = item.getString(TAG_USER);
                String d_date = item.getString(TAG_DATE);
             //   String n_code = item.getString(TAG_NCODE);
                String n_name = item.getString(TAG_NAME);

                DoseInfo Doseinfo = new DoseInfo();

              //  Doseinfo.setD_code(d_code);
                Doseinfo.setUserID(userID);
                Doseinfo.setD_date(d_date);
             //   Doseinfo.setN_code(n_code);
                Doseinfo.setN_name(n_name);

                myarrayList.add(Doseinfo);
                DoseAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }

    }
}
