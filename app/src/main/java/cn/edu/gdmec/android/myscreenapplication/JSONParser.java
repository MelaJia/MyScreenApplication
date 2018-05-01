package cn.edu.gdmec.android.myscreenapplication;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.util.List;

/**
 * Created by HP on 2018/4/26.
 */

public class JSONParser {
    private static InputStream is=null;
    private static JSONObject jObj=null;
    private static String json = "";
    public  JSONParser(){

    }

    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params){
      try {
          if (method == "POST") {
              HttpClient httpClient = new DefaultHttpClient();
              HttpPost httpPost = new HttpPost(url);
              httpPost.setEntity(new UrlEncodedFormEntity(params));
              HttpResponse httpResponse=httpClient.execute(httpPost);
              HttpEntity httpEntity=httpResponse.getEntity();
              is=httpEntity.getContent();

          }else if(method=="GET"){
              HttpClient httpClient=new DefaultHttpClient();
              String paramsString = URLEncodedUtils.format(params,"utf-8");
              url+="?"+paramsString;
              HttpGet httpGet=new HttpGet(url);
              HttpResponse httpResponse=httpClient.execute(httpGet);
              HttpEntity httpEntity=httpResponse.getEntity();
              is=httpEntity.getContent();


          }
      }catch (Exception e){
          e.printStackTrace();
      }
        try {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is,"utf-8"),8);
            StringBuilder sb=new StringBuilder();
            String line=null;
            while ((line=bufferedReader.readLine())!=null){
                sb.append(line+"\n");
            }
            is.close();
            json=sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            jObj=new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error pasing data"+e.toString() );
        }


        return jObj;
    }

}
