package skripsi.android.andrian;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



public class Productdetails extends AppCompatActivity {
    Button add;
    ImageView imageView4, imageView9, imageView10, imageView6;
    TextView textView14;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        add = findViewById(R.id.add);
        imageView4 = findViewById(R.id.imageView4);
        imageView9 = findViewById(R.id.imageView9);
        imageView10 = findViewById(R.id.imageView10);
        textView14 = findViewById(R.id.textView14);
        imageView6 = findViewById(R.id.imageView6);
        registerForContextMenu(imageView6);


        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                textView14.setText("" + count);
            }
        });

        imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                textView14.setText("" + count);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getApplicationContext(), Confirmation.class);
                startActivity(j);
            }
        });

    }

    @Override
    public  void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.add(0, v.getId(),0,"Langue");
        menu.add(1, v.getId(),1,"Quitter");

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
    {
            if(item.getTitle()=="Langue")
            {
                Intent i = new Intent (Settings.ACTION_LOCALE_SETTINGS);
                startActivity(i);
            }
            else
            {
                Intent j = new Intent();

                finish();

        }
        return super.onContextItemSelected(item);
    }
}


