package cn.edu.gdmec.android.myscreenapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2018/4/26.
 */

public class NewProductsActivity extends Activity {
    private EditText inputName;
    private EditText inputPrice;
    private EditText inputDesc;
    private Button btnCreateProduct;
    private ProgressDialog pDialog;
    JSONParser jsonParser=new JSONParser();
    private static String url_create_product="http://api.androidhive.info/android_connect/create_product.php";
    private static final String TAG_SUCCESS="success";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        initView();

    }

    private void initView() {
        inputName = (EditText) findViewById(R.id.inputName);
        inputPrice = (EditText) findViewById(R.id.inputPrice);
        inputDesc = (EditText) findViewById(R.id.inputDesc);
        btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

        btnCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CreateNewProduct().execute();

            }
        });
    }
    class CreateNewProduct extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(NewProductsActivity.this);
            pDialog.setMessage("Create PrODUCT..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String name=inputName.getText().toString();
            String price=inputPrice.getText().toString();
            String description = inputDesc.getText().toString();

            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name",name));
            params.add(new BasicNameValuePair("price",price));
            params.add(new BasicNameValuePair("description",description));
            JSONObject json=jsonParser.makeHttpRequest(url_create_product,"POST",params);
            Log.d("Create Respond", json.toString());
            try {
                int success=json.getInt(TAG_SUCCESS);
                if (success==1){
                    Intent i = new Intent(getApplicationContext(),AllProductsActivity.class);
                    startActivity(i);
                    finish();;
                }else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            super.onPostExecute(s);
        }
    }



}
