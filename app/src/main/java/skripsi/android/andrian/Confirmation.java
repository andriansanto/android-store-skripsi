package skripsi.android.andrian;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;


public class Confirmation extends AppCompatActivity {

    Button okey;
    LottieAnimationView lottie,lottie2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation);

        okey = findViewById(R.id.btn_okey);
        lottie = findViewById(R.id.lottie);
        lottie2 = findViewById(R.id.lottie2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lottie.animate().translationX(2000).setDuration(2000).setStartDelay(2900);




        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), totp_confirm.class);
                startActivityForResult(i,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Payment Success!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), PopUp_Creator.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Payment Failed, please try again!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu1) {
        getMenuInflater().inflate(R.menu.mon_menu,menu1);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.profil){
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }
        else if(id == R.id.logout)
        {
           Intent i = new Intent(getApplicationContext(),Formulaire.class);
           startActivity(i);

        }
        return true;
    }

}



