package skripsi.android.andrian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import skripsi.android.andrian.Adapter.adapterCategoryProduct;
import skripsi.android.andrian.Adapter.model.productCategory;
import skripsi.android.andrian.Adapter.model.products;
import skripsi.android.andrian.Adapter.productAdapter;


public class MainActivity extends AppCompatActivity {
    LottieAnimationView lottie;
    adapterCategoryProduct productCategoryAdapter;
    RecyclerView productCatRecycler, prodItemRecycler;
    skripsi.android.andrian.Adapter.productAdapter productAdapter;
    private TextView textView,textView2,textView3,textView4;
    public TextView textView6, textView5_2;
    Dialog mDialog;
    ImageView imageView,imageView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5_2 = findViewById(R.id.textView5_2);
        textView6 = findViewById(R.id.textView6);
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        lottie = findViewById(R.id.lottie);
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.popup);

        lottie.animate().translationX(2000).setDuration(2000).setStartDelay(2900);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("user-data").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("get-data", "DocumentSnapshot data: " + document.getData());
//                        Toast.makeText(MainActivity.this,"Disini data: " + document.getData().get("balance"),Toast.LENGTH_SHORT).show();
                        textView5_2.setText(document.getData().get("user_name")+ "!");
                        textView6.setText("$ "+ document.getData().get("balance"));
                    } else {
                        Log.d("get-data", "No such document");
                    }
                } else {
                    Log.d("get-data", "get failed with ", task.getException());
                }
            }
        });//get_balance

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Productdetails3.class);
                startActivity(i);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),productdetails4.class);
                startActivity(i);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),productdetails5.class);
                startActivity(i);
            }
        });

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),productdetails6.class);
                startActivity(i);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(),Searchpage.class);
                startActivity(i);
            }
        });

        List<productCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(new productCategory(1, "Most Popular"));
        productCategoryList.add(new productCategory(2, "Trending"));
        productCategoryList.add(new productCategory(3, "Skin Care"));
        productCategoryList.add(new productCategory(4, "Hair Care"));
        productCategoryList.add(new productCategory(5, "All Body Products"));
        productCategoryList.add(new productCategory(6, "Make Up"));
        productCategoryList.add(new productCategory(7, "Fragrance"));

        setProductAdapter(productCategoryList);


            List<products> productsList = new ArrayList<>();
            productsList.add(new products(2, "Japanese Cherry Blossom","250 ml", "$ 15.00",R.drawable.prod2));
            productsList.add(new products(2, "Japanese Cherry Blossom","250 ml", "$ 15.00",R.drawable.prod2));
            productsList.add(new products(2, "Japanese Cherry Blossom","250 ml", "$ 15.00",R.drawable.prod2));
            productsList.add(new products(2, "Japanese Cherry Blossom","250 ml", "$ 15.00",R.drawable.prod2));
        setProdItemRecycler(productsList);


    }


    private void setProductAdapter(List<productCategory> productCategoryList){


        productCatRecycler = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this , RecyclerView.HORIZONTAL, false);
        productCatRecycler.setLayoutManager(layoutManager);
        productCategoryAdapter = new adapterCategoryProduct(this, productCategoryList);
        productCatRecycler.setAdapter(productCategoryAdapter);
    }


    private void setProdItemRecycler(List<products> productsList){


        prodItemRecycler = findViewById(R.id.product_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this , RecyclerView.HORIZONTAL, false);
        prodItemRecycler.setLayoutManager(layoutManager);
        productAdapter = new productAdapter(this, productsList);
        prodItemRecycler.setAdapter(productAdapter);
    }

}
