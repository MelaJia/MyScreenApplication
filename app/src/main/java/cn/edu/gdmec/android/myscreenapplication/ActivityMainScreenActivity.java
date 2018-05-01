package cn.edu.gdmec.android.myscreenapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityMainScreenActivity extends Activity implements View.OnClickListener {


    private Button btnViewProduct;
    private Button btnCreateProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        initView();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnViewProduct:
               Intent i=new Intent(getApplicationContext(),AllProductsActivity.class);
               startActivity(i);
                break;
            case R.id.btnCreateProduct:
                Intent ii=new Intent(getApplicationContext(),NewProductsActivity.class);
                startActivity(ii);
                break;

        }
    }

    private void initView() {
        btnViewProduct = (Button) findViewById(R.id.btnViewProduct);
        btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

        btnViewProduct.setOnClickListener(this);
        btnCreateProduct.setOnClickListener(this);
    }
}
