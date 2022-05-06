package skripsi.android.andrian;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class loginTabFragement extends Fragment {
    private TextView email,pass,slideView;
    private FirebaseAuth mAuth;
    private Button login;
    float v = 0;

    private String user,pw, user_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragement, container, false);

    mAuth = FirebaseAuth.getInstance();
    email = root.findViewById(R.id.Email);
    pass = root.findViewById(R.id.pass);
    login = root.findViewById(R.id.login);
    slideView = root.findViewById(R.id.slideView);
    FirebaseFirestore db = FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                checkLogin();

            }

            private boolean checkLogin() {
                user = email.getText().toString();
                pw = pass.getText().toString();

                if(user.isEmpty() || pw.isEmpty()){
                    Toast.makeText(getContext(),"Please enter your email and password first!",Toast.LENGTH_SHORT).show();
                    return false;
                }
                mAuth.signInWithEmailAndPassword(user,pw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
//                                    Toast.makeText( getContext(),"Login Success!",Toast.LENGTH_SHORT).show();
                                    user_id = task.getResult().getUser().getUid();
                                    Toast.makeText( getContext(),"Login Success!",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getActivity(), MainActivity.class);
                                    startActivity(i);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getContext(),"Login Failed, Please check your email or password!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                return false;
            }//checklogin

        });//login onclick listener


        email.setTranslationY(800);
        pass.setTranslationY(800);
        login.setTranslationY(800);
        slideView.setTranslationY(800);


        email.setAlpha(v);
        pass.setAlpha(v);
        login.setAlpha(v);
        slideView.setAlpha(v);

        slideView.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(250).start();
        email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();



    return root;
}
}
