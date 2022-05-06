package skripsi.android.andrian;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class PopUp_Creator extends AppCompatActivity {
    TextView btnToHome, helloUserText, userBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_profile);
        btnToHome = findViewById(R.id.btnToHome);
        userBalance = findViewById(R.id.userBalance);
        helloUserText = findViewById(R.id.helloUserText);

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
                        helloUserText.setText("Hello, "+document.getData().get("user_name")+ "!");
                        userBalance.setText("$ "+ document.getData().get("balance"));
                    } else {
                        Log.d("get-data", "No such document");
                    }
                } else {
                    Log.d("get-data", "get failed with ", task.getException());
                }
            }
        });//get_balance


        btnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }//oncreate
}
