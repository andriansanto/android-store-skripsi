package skripsi.android.andrian;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;


public class introductionActivity extends AppCompatActivity {

    LottieAnimationView lottie;
    TextView appName;


    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        lottie = findViewById(R.id.lottie);
        appName = findViewById(R.id.appName);

        appName.animate().translationY(-1600).setDuration(2700).setStartDelay(0);
        lottie.animate().translationX(2000).setDuration(2000).setStartDelay(2900);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),Formulaire.class);
                startActivity(i);

            }
        },5000);

    }
    }



