package cn.edu.gdmec.android.myscreenapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2018/4/26.
 */

public class EditProductActivity extends Activity implements View.OnClickListener {
    private EditText inputName;
    private EditText inputPrice;
    private EditText inputDesc;
    private Button btnSave;
    private Button btnDelete;
    String pid;

    private ProgressDialog progressDialog;
    JSONParser jsonParser=new JSONParser();

    private static final String url_product_details="http://api.androidhive.info/android_connect/get_product_details.php";
    private static final String url_update_product="http://api.androidhive.info/android_connect/update_product.php";
    private static final String url_delete_product="http://api.androidhive.info/android_connect/delete_product.php";


    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE="price";
    private static final String TAG_DESCRIPTION="description";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        initView();
        Intent i=getIntent();
        pid=i.getStringExtra(TAG_PID);
        new GetProductDetails().execute();
    }

    private void initView() {

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                new SaveProductDetails().execute();

                break;
            case R.id.btnDelete:
                new DeleteProduct().execute();

                break;
        }
    }
class GetProductDetails extends AsyncTask<String, String, String>{
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(EditProductActivity.this);
        progressDialog.setMessage("Loading product details.Please wait...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }


    @Override
    protected String doInBackground(String... strings) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int success;
                try {
                    List<NameValuePair> pairs=new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("pid",pid));
                    JSONObject json=jsonParser.makeHttpRequest(url_product_details,"GET",pairs);
                    Log.d("Single Product Details", json.toString());
                    success=json.getInt(TAG_SUCCESS);
                    if (success==1){
                        JSONArray productObj=json.getJSONArray(TAG_PRODUCTS);
                        JSONObject product=productObj.getJSONObject(0);
                        inputName = (EditText) findViewById(R.id.inputName);
                        inputPrice = (EditText) findViewById(R.id.inputPrice);
                        inputDesc = (EditText) findViewById(R.id.inputDesc);
                        inputName.setText(product.getString(TAG_NAME));
                        inputPrice.setText(product.getString(TAG_PRICE));
                        inputDesc.setText(product.getString(TAG_DESCRIPTION));

                    }else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(String file_url) {
        progressDialog.dismiss();
    }
}

     class SaveProductDetails extends AsyncTask<String, String, String>{
         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             progressDialog=new ProgressDialog(EditProductActivity.this);
             progressDialog.setMessage("Saving product details.Please wait...");
             progressDialog.setIndeterminate(false);
             progressDialog.setCancelable(true);
             progressDialog.show();
         }

         @Override
        protected String doInBackground(String... args) {
             String name=inputName.getText().toString();
             String price=inputPrice.getText().toString();
             String description = inputDesc.getText().toString();

             List<NameValuePair> params=new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair(TAG_PID,pid));
             params.add(new BasicNameValuePair(TAG_NAME,name));
             params.add(new BasicNameValuePair(TAG_PRICE,price));
             params.add(new BasicNameValuePair(TAG_DESCRIPTION,description));
             JSONObject json=jsonParser.makeHttpRequest(url_update_product,"POST",params);

             try {
                 int success=json.getInt(TAG_SUCCESS);
                 if (success==1){
                     Intent i = getIntent();
                     finish();;
                 }else {
                     Toast.makeText(EditProductActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
             }

             return null;
         }

         @Override
         protected void onPostExecute(String file_url) {
             progressDialog.dismiss();
         }
     }

     class DeleteProduct extends AsyncTask<String, String, String>{
         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             progressDialog=new ProgressDialog(EditProductActivity.this);
             progressDialog.setMessage("Deleting Product.Please wait...");
             progressDialog.setIndeterminate(false);
             progressDialog.setCancelable(true);
             progressDialog.show();
         }

         @Override
         protected String doInBackground(String... args) {
             int success;
                     try {
                         List<NameValuePair> params=new ArrayList<NameValuePair>();
                         params.add(new BasicNameValuePair("pid",pid));
                         JSONObject json=jsonParser.makeHttpRequest(url_delete_product,"POST",params);
                         Log.d("Delete Product Details", json.toString());
                         success=json.getInt(TAG_SUCCESS);
                         if (success==1){
                             Intent i=getIntent();
                             setResult(100,i);
                             finish();

                         }else {

                         }
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
             return null;
         }

         @Override
         protected void onPostExecute(String s) {
             progressDialog.dismiss();
         }
     }

}
