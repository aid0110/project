package com.example.ui_02;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class FragSearch extends Fragment {
    private View view;
    private static String TAG = "phpquerytest";
    private static final String TAG_JSON="webnautes";
    private static final String TAG_CODE = "n_code";
    private static final String TAG_BRAND = "n_brand";
    private static final String TAG_NAME ="n_name";
    private static final String TAG_INGREDIENT = "n_ingredient";
    private static final String TAG_NUM = "n_num";
    private static final String TAG_METHOD ="n_method";
//이건 복용관리
    private ArrayList<DoseInfo> myarrayList;
    private DoseAdapter DoseAdapter;
    RecyclerView mRecyclerView;
    private String myJsonString;

    //이건 검색
    ListView mListViewList;
    ArrayList<HashMap<String, String>> mArrayList;
    EditText mEditTextSearchKeyword1, mEditTextSearchKeyword2;
    String mJsonString;
    String serverURL = "http://192.168.0.61/query.php";
    private static final String IP_ADDRESS ="192.168.0.61";
    //상태 저장(어댑터와 맞물려서 통신해주는 역할)
    public static FragSearch newInstance(){
        FragSearch fragSearch = new FragSearch();
        return fragSearch;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_search,container,false);
        //검색
        mListViewList = view.findViewById(R.id.listView_main_list);
        mEditTextSearchKeyword1 = view.findViewById(R.id.editText_main_searchKeyword1);
        mEditTextSearchKeyword2 = view.findViewById(R.id.editText_main_searchKeyword2);

        Button btn_search = view.findViewById(R.id.button_main_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//이건 getdate
                mArrayList.clear();
                GetData task = new GetData();
                task.execute(mEditTextSearchKeyword1.getText().toString(),mEditTextSearchKeyword2.getText().toString());
            }
        });
        mArrayList = new ArrayList<>();

        //검색 누른후 클릭한 영양제 값 넣기
        /***
         * 넣는 값
         * userID 고객 번호
         * d_date 복용시작 날짜
         * n_code 상품 코드
         */
        final String userID = UserInfo.getUserID();
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("YYYY-MM-dd");
        final String d_date = simpleDate.format(mDate);

        mListViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //클릭한 아이템의 문자열을 가져옴
                String i = "2"; //영양제 결정 코드
                InsertData insertData = new InsertData();
                insertData.execute("http://" + IP_ADDRESS + "/insertdose.php", userID, d_date, i);
            }
        });

       /* //검색 클릭시 찜으로 넘어가는부분
        mListViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ListAdapter likeadapter = new SimpleAdapter(
                        getActivity(), mArrayList, R.layout.likelist,
                        new String[]{TAG_BRAND,TAG_NAME,TAG_INGREDIENT,TAG_NUM, TAG_METHOD},
                        new int[]{ R.id.textView_brand, R.id.textView_name,R.id.textView_ingredient,R.id.textView_num,R.id.textView_method}
                );
               // likelistView.setAdapter(likeadapter);

                Object vo = (Object)adapterView.getAdapter().getItem(i);

            }
        });*/

        //복용관리
        mRecyclerView = view.findViewById(R.id.Re_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myarrayList = new ArrayList<>();
        DoseAdapter = new DoseAdapter(getActivity(),myarrayList);
        mRecyclerView.setAdapter(DoseAdapter);
        myarrayList.clear();
        DoseAdapter.notifyDataSetChanged();
        ShowData task = new ShowData();
        task.execute( "http://" + IP_ADDRESS + "/testquery.php", userID);
        return view;
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "response - " + result);

            if (result == null){
            }
            else {
                mJsonString = result;
                showResult();
            }
        }
        @Override
        protected String doInBackground(String... params) {

            String searchKeyword1 = params[0];
            String searchKeyword2 = params[1];

            String postParameters = "n_brand=" + searchKeyword1 + "&n_name=" + searchKeyword2;

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

                Log.d(TAG, "Data: Error ", e);
                errorString = e.toString();

                return null;
            }
        }
    }
    private void showResult(){
        try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for(int i=0;i<jsonArray.length();i++){

                    JSONObject item = jsonArray.getJSONObject(i);

                    String n_code = item.getString(TAG_CODE);
                    String n_brand = item.getString(TAG_BRAND);
                    String n_name = item.getString(TAG_NAME);
                    String n_ingredient = item.getString(TAG_INGREDIENT);
                    String n_num = item.getString(TAG_NUM);
                    String n_method = item.getString(TAG_METHOD);
                    Log.d(TAG, "mJsonString - " + mJsonString);
                    HashMap<String,String> hashMap = new HashMap<>();

                    hashMap.put(TAG_CODE, n_code);
                    hashMap.put(TAG_BRAND, n_brand);
                    hashMap.put(TAG_NAME, n_name);
                    hashMap.put(TAG_INGREDIENT, n_ingredient);
                    hashMap.put(TAG_NUM, n_num);
                    hashMap.put(TAG_METHOD, n_method);

                    mArrayList.add(hashMap);
                }
                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), mArrayList, R.layout.item_list,
                        new String[]{TAG_CODE,TAG_BRAND,TAG_NAME,TAG_INGREDIENT,TAG_NUM, TAG_METHOD},
                        new int[]{R.id.textView_list_code, R.id.textView_list_brand, R.id.textView_list_name,R.id.textView_list_ingredient,R.id.textView_list_num,R.id.textView_list_method}
                );
                mListViewList.setAdapter(adapter);
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "Please Wait",null,true,true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            Log.d(TAG,"POST response - "+result);
        }

        @Override
        protected String doInBackground(String... params) {
            /***
             * 넣는 값
             * userID 고객 번호
             * d_date 복용시작 날짜
             * n_code 상품 코드
             */

            String userID = params[1];
            String d_date = params[2];
            String n_code = params[3];

            String serverURL = params[0];
            String postParameters = "userID=" + userID + "&d_date=" + d_date + "&n_code=" + n_code;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

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
                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString();

            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }


        }
    }

    private class ShowData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String showresult) {
            super.onPostExecute(showresult);

            progressDialog.dismiss();
            Log.d(TAG, "show- response - " + showresult);

            if (showresult == null){ }
            else {
                myJsonString = showresult;
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
                Log.d(TAG, "show-response code - " + responseStatusCode);

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