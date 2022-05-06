package skripsi.android.andrian;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class productdetails5 extends AppCompatActivity {
    private Button add;
    ImageView imageView4,imageView9,imageView10,imageView6;
    TextView textView14;
    int count = 1;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prod5);
        add = findViewById(R.id.add);
        imageView4 = findViewById(R.id.imageView4);
        imageView9 = findViewById(R.id.imageView9);
        imageView10 = findViewById(R.id.imageView10);
        textView14 = findViewById(R.id.textView14);
        imageView6 = findViewById(R.id.imageView);

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
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

}

