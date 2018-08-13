package com.example.aviasa100.myandroidproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aviasa100.myandroidproject.utils.Prefs;
import com.example.aviasa100.myandroidproject.utils.ProductDetails;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.aviasa100.myandroidproject.utils.Prefs.addProductName;


public class MyShoppingCartListActivity extends AppCompatActivity {

    MyShoppingCartListActivity self = this;
    private ListView lvProduct;
    private ProductListAdapter adapter;
    private  static List<ProductDetails> mProductList;

    String [] productsNames = {};//empty by default
    Button placeOrder;
    TextView totalPrice;
    ImageView productImage;
    public SharedPreferences sharedPrefs;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shopping_cart_list);
        lvProduct = (ListView)findViewById(R.id.list_view_product);
        placeOrder = (Button) findViewById(R.id.placeOrder);
        totalPrice = (TextView)findViewById(R.id.total);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(self);
        mProductList = new ArrayList<ProductDetails>();
        /*mProductList.add(new ProductDetails("banana", 5, 6));*/
        productImage = (ImageView)findViewById(R.id.productImage);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent finish = new Intent(self,FinishShoppingActivity.class);
                startActivity(finish);
                finish();
            }
        });



        //init adapter

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), "Clicked product id = " + view.getTag(), Toast.LENGTH_SHORT.show());
            }
        });

        //placeOrder.setOnClickListener(setContentView(self,FinishShoppingActivity.class));


    }

//    private View.OnClickListener setContentView(MyShoppingCartListActivity myShoppingCartListActivity, Class<FinishShoppingActivity> finishShoppingActivityClass) {
//        return null;
//    }

    @Override
    protected void onStart() {
        super.onStart();

        refresh();
        System.out.println("******************AVIA");
    }

    private void refresh(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
       // String desiredPreference =  sharedPreferences.getString("com.aviasa100.CHOSEN_AMOUNT", "DefaultValue");
        getIntent();
        Set<String> names = Prefs.getProductNames(this);

        System.out.print("**************NAMES**********"+names); // return empty array

        //mProductList = new ArrayList();

        for(String name : names){

            int count = Prefs.getProductCount(self, name);
            long price = Prefs.getProductPrice(self,name);

            mProductList.add(new ProductDetails(name, price, count));
            totalPrice.setText("Total: "+price);
        }
        //totalPrice = Prefs.setProductPrice(self,name,);
        adapter = new ProductListAdapter(self, mProductList);
        lvProduct.setAdapter(adapter);

    }


}
