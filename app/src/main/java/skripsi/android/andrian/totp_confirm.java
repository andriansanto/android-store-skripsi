package skripsi.android.andrian;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class totp_confirm extends AppCompatActivity {
    EditText et_code;
    TextView viewTimer1, textViewNote;
    LinearLayout not_get;
    Button button, btnSendAgain, btnOpen;
    private String otpCode, secretID, RandomNumb;

    private int expired_date;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String user_email = user.getEmail();
    private CountDownTimer TimerWatch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.totp_confirm);
        viewTimer1 = findViewById(R.id.viewTimer1);
        textViewNote = findViewById(R.id.textViewNote);
        et_code = findViewById(R.id.et_code);
        btnSendAgain = findViewById(R.id.btnSendAgain);
        button = findViewById(R.id.button);
        btnOpen = findViewById(R.id.btnOpen);
        not_get = findViewById(R.id.not_get);



        db.collection("user-second")
                .whereEqualTo("email", user_email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("get-2nd", document.getId() + " => " + document.getData());
                                secretID = document.getId();
//                                Toast.makeText(totp_confirm.this,"get Secret ID:" + secretID,Toast.LENGTH_SHORT).show();
                                fetchData();
                            }
                        } else {
                            Log.d("get-2nd", "Error getting documents: ", task.getException());
                        }
                    }
                });//get_secret_id

        if(TimerWatch == null){
            viewTimer1.setText("Sending OTP Code...");
        }

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent A = new Intent(Intent.ACTION_VIEW, Uri.parse("https://web-skripsi.vercel.app/"));
                startActivity(A);
            }
        });

        btnSendAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TimerWatch != null){
                    TimerWatch.cancel();
                }
                not_get.setVisibility(View.GONE);
                fetchData();
            }// on click
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();
            }// on click void

            boolean checkDataEntered(){
                String userInput = et_code.getEditableText().toString().trim();

                if(userInput.isEmpty()) {
                    Toast.makeText(totp_confirm.this, "Please enter otp code!", Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    verifyOTP();
                }
                return false;
            }//check

            private void verifyOTP() {
                String number = et_code.getText().toString();
                int current_time = getCurrentTime();

                if(number.equals(otpCode) && current_time < expired_date)
                {
//                    Toast.makeText(totp_confirm.this,"Verify Success",Toast.LENGTH_SHORT).show();
                    String uid = user.getUid();
                    DocumentReference newRef = db.collection("user-data").document(uid);
                    newRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d("get-data", "DocumentSnapshot data: " + document.getData());
//                        Toast.makeText(MainActivity.this,"Disini data: " + document.getData().get("balance"),Toast.LENGTH_SHORT).show();
                                    int getBalance = document.getLong("balance").intValue();
                                    int updatedBalance = getBalance - 15;

                                    db.collection("user-data")
                                            .document(uid)
                                            .update("balance", updatedBalance)

                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.w("save user-second", "Error writing document:");
                                                    Intent intent = new Intent();
                                                    setResult(RESULT_OK, intent);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("Else", "Error writing document:", e);
                                                }
                                            });//save otp to db

                                } else {
                                    Log.d("get-data", "No such document");
                                }
                            } else {
                                Log.d("get-data", "get failed with ", task.getException());
                            }
                        }
                    });//get_balance

                }else{
                    Toast.makeText(totp_confirm.this,"Wrong OTP Code, please try again!",Toast.LENGTH_SHORT).show();
                }
//                finish();
            }//verify otp

        });//button on click listener

    }//on create

    private static int getCurrentTime(){
        Calendar cal = Calendar.getInstance();

        // get day, minute, second and convert into integer
        int curTime = cal.get(Calendar.HOUR_OF_DAY) * 60 * 60 + cal.get(Calendar.MINUTE) * 60 + cal.get(Calendar.SECOND);

        return curTime;
    }//get current time

    private void startTimer(){
        TimerWatch = new CountDownTimer(90000, 1000){

            @Override
            public void onTick(long l){
                viewTimer1.setText(""+l/1000+"s");
                textViewNote.setVisibility(View.GONE);
            }//ontick

            @Override
            public void onFinish(){
                //finish hide text view
//                viewTimer1.setVisibility(View.GONE);
                viewTimer1.setText("Code Expired");
                textViewNote.setText("code expired, please send new otp code");
                not_get.setVisibility(View.VISIBLE);
//                Toast.makeText(totp_confirm.this,"code expired, please send new otp code!",Toast.LENGTH_SHORT).show();
            }//onfinish

        }.start();//coundowntimer

    }//start timer


    private void fetchData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<ObjectResponse>> call = api.getObjectResponse();

        call.enqueue(new Callback<List<ObjectResponse>>() {
            @Override
            public void onResponse(Call<List<ObjectResponse>> call, Response<List<ObjectResponse>> response) {
                List<ObjectResponse> heroes = response.body();
//                Toast.makeText(getApplicationContext(), heroes.get(0).getRandomNumber(), Toast.LENGTH_LONG).show();
                if (response.body() == null){
                    if(TimerWatch == null){
                        viewTimer1.setText("Code Limited");
                        textViewNote.setText("Please try send new otp code later!");
                    }
                    Toast.makeText(getApplicationContext(), "Your OTP request has exceed the limit, please try again in 15 minute", Toast.LENGTH_SHORT).show();
                }else{
                    RandomNumb = heroes.get(0).getRandomNumber();
                    SaveOTP();
                }
            }

            @Override
            public void onFailure(Call<List<ObjectResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fetch Failed, please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }// fetch data


    private void SaveOTP() {
//        Toast.makeText(totp_confirm.this,"Secret ID:" + secretID,Toast.LENGTH_SHORT).show();
        otpCode = RandomNumb;
        expired_date = getCurrentTime() + 91;

            Map<String, Object> otp_data = new HashMap<>();
            otp_data.put("otp_code", otpCode);
            otp_data.put("expired_date", expired_date);
            otp_data.put("email", user_email);

            db.collection("user-second")
                    .document(secretID)
                    .set(otp_data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.w("save user-second", "Error writing document:");
                            startTimer();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Else", "Error writing document:", e);
                        }
                    });//save otp to db
    }//save otp

}
