package com.example.user.newsapi;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.newsapi.Controllers.APIHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Controller extends AppCompatActivity  {

    EditText editText;
    Button but_get;


    APIcontroller apIcontroller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        editText=(EditText)findViewById(R.id.editText);
        but_get=(Button)findViewById(R.id.button);


but_get.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try {

            String mov=new APIcontroller().execute("https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=4e878f5b065e4592be3503001cb494b9").get();
            editText.setText(mov);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
});


    }
    class APIcontroller extends AsyncTask<String,Void,String> {

        EditText editText;
        String newsTitle="haaaaaaai";
        HashMap newsMap =new HashMap();
        ArrayList<HashMap> newslist=new ArrayList<>();
       g

        protected void onpreexecute(){
            super.onPreExecute();

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... params) {




            String reqUrl=params[0];
            APIHandler reqCon=new APIHandler();
            Log.e("Test_inBack",reqUrl);

            Log.e("InBack","before jsonget");
            try {
                String jsonstring=reqCon.httpreq(reqUrl);
                Log.e("InBack  afterurl",jsonstring);

                //open object  {
                JSONObject jsonObject=new JSONObject(jsonstring);
//                //get title     " "
                newsTitle=jsonObject.getString("source");

                Log.e("InBacksssssssss",newsTitle);

            JSONArray jsonArticleArray=jsonObject.getJSONArray("articles");
//
//            // Article array entry loop :
//
                Log.e("InJsonf  Loop","inside artical");

            for(int a=0;a<=jsonArticleArray.length();a++){

                Log.e("InJsonf  Loop",""+a+jsonArticleArray.length());
                // article Objects

                JSONObject articleObject=jsonArticleArray.getJSONObject(a);
                Log.e("InJsonf  Loop","inside artical"+articleObject.getString("author"));
                Log.e("InJsonf  Loop","inside artical"+articleObject.getString("title"));
                Log.e("InJsonf  Loop","inside artical"+articleObject.getString("description"));
                Log.e("InJsonf  Loop","inside artical"+articleObject.getString("publishedAt"));
                Log.d("......................","---------------------------");

                   newsMap.put("author",articleObject.getString("author"));
                    newsMap.put("title",articleObject.getString("title"));
                   newsMap.put("description",articleObject.getString("description"));
                    newsMap.put("publishedAt",articleObject.getString("publishedAt"));

                newslist.add(newsMap);
            }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }





            return newsTitle;
        }
        protected String onpostexecute(String result){
            super.onPostExecute(result);

            Log.e("OnpostExe","processing");

            return result;
        }
    }
}


