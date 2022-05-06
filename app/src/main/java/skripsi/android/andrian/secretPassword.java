package skripsi.android.andrian;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class secretPassword extends AppCompatActivity {

    TextView viewSecret;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secret_password);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        viewSecret = findViewById(R.id.view_secretID);
        button = findViewById(R.id.btn_Home);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String user_email = user.getEmail();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("user-second")
                .whereEqualTo("email", user_email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("get-2nd", document.getId() + " => " + document.getData());
                                viewSecret.setText(document.getId());
                            }
                        } else {
                            Log.d("get-2nd", "Error getting documents: ", task.getException());
                        }
                    }
                });//get_2nd

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });//button

    }

}
