package skripsi.android.andrian;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class SignupTabFragment extends Fragment {
private TextView userName, Email, num, pass, confirm, slideView;
private Button signup;
private FirebaseAuth mAuth;
private String  user_name, second_pass, username, number, pw, pw2, user_id;

float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragement, container, false);
        mAuth = FirebaseAuth.getInstance();
        userName = root.findViewById(R.id.userName);
        Email = root.findViewById(R.id.Email);
        num = root.findViewById(R.id.num);
        pass = root.findViewById(R.id.pass);
        confirm = root.findViewById(R.id.confirm);
        signup = root.findViewById(R.id.signup);
        slideView = root.findViewById(R.id.slideView);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {

        private String getRandomString(int n)
        {

            // length is bounded by 256 Character
            byte[] array = new byte[256];
            new Random().nextBytes(array);

            String randomString = new String(array, Charset.forName("UTF-8"));

            // Create a StringBuffer to store the result
            StringBuffer r = new StringBuffer();

            // remove all spacial char
            String AlphaNumericString = randomString.replaceAll("[^A-Z0-9]", "");

            // Append first 20 alphanumeric characters
            // from the generated random String into the result
            for (int k = 0; k < AlphaNumericString.length(); k++) {

                if (Character.isLetter(AlphaNumericString.charAt(k))
                        && (n > 0)
                        || Character.isDigit(AlphaNumericString.charAt(k))
                        && (n > 0)) {
                    r.append(AlphaNumericString.charAt(k));
                    n--;
                }
            }
            // return the resultant string
            return r.toString();
        }//random string

            @Override
            public void onClick(View view) {
                checkDataEntered();
            }


            boolean checkDataEntered(){
                String userInput = userName.getEditableText().toString().trim();
                String emailInput = Email.getEditableText().toString().trim();
                String passInput = pass.getEditableText().toString().trim();
                String numInput = num.getEditableText().toString().trim();
                String confirmInput = confirm.getEditableText().toString().trim();

                if(userInput.isEmpty()){
                    Toast.makeText(getContext(),"Please enter your username!",Toast.LENGTH_SHORT).show();
                    return false;
                }//username
                if(emailInput.isEmpty()) {
                    Toast.makeText(getContext(),"You must enter email to register!",Toast.LENGTH_SHORT).show();
                    return false;
                }//email
                if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
                    Toast.makeText(getContext(),"You must enter a valid email address!",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(numInput.isEmpty()){
                    Toast.makeText(getContext(),"You must enter your mobile number to register!",Toast.LENGTH_SHORT).show();
                    return false;
                }//num
                if(passInput.isEmpty()){
                    Toast.makeText(getContext(),"You must enter password to register!",Toast.LENGTH_SHORT).show();
                    return false;
                }//pass
                if(confirmInput.isEmpty()){
                    Toast.makeText(getContext(),"password confirmation is required!",Toast.LENGTH_SHORT).show();
                    return false;
                }//confirm
                if(!passInput.equals(confirmInput)) {
                    Toast.makeText(getContext(), "password do not match!", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else{
                    RegisterUser();
                }
                return false;
            }//check



            private void RegisterUser() {
                user_name = userName.getText().toString();
                username = Email.getText().toString();
                number = num.getText().toString();
                pw = pass.getText().toString();
                pw2 = confirm.getText().toString();

                second_pass = getRandomString(6);

                saveEmail();

            }//RegisterUser


            private void saveEmail(){
                mAuth.createUserWithEmailAndPassword(username, pw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
//                                    Toast.makeText( getContext(),"Register Success!",Toast.LENGTH_SHORT).show();

                                    user_id = task.getResult().getUser().getUid();
                                    int balance = 100;
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("user_name", user_name);
                                    user.put("email", username);
                                    user.put("mobile_number", number);
                                    user.put("balance", balance);

                                    Map<String, Object> second_data = new HashMap<>();
                                    second_data.put("email", username);

                                    db.collection("user-second")
                                            .document(second_pass)
                                            .set(second_data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.w("save user-second", "Error writing document:");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("Else", "Error writing document:", e);
                                                    Toast.makeText(getContext(),"Register Failed, please check your register form!",Toast.LENGTH_SHORT).show();
                                                }
                                            });//dbcol


                                    db.collection("user-data")
                                            .document(user_id)
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getContext(),"Register Success!",Toast.LENGTH_SHORT).show();
//                                                    Intent i = new Intent(getActivity(), MainActivity.class);
                                                    Intent i = new Intent(getActivity(), secretPassword.class);
                                                    startActivity(i);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("Else", "Error writing document:", e);
                                                    Toast.makeText(getContext(),"Register Failed, please check your register form!",Toast.LENGTH_SHORT).show();
                                                }
                                            });//dbcol user-data

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getContext(),"Register Failed, please check your register form!",Toast.LENGTH_SHORT).show();
                                }
                            }//onComplete Task
                        });
            }//save email

        });


        Email.setTranslationY(800);
        num.setTranslationY(800);
        pass.setTranslationY(800);
        confirm.setTranslationY(800);
        signup.setTranslationY(800);
        slideView.setTranslationY(800);


        Email.setAlpha(v);
        num.setAlpha(v);
        pass.setAlpha(v);
        confirm.setAlpha(v);
        signup.setAlpha(v);
        slideView.setAlpha(v);


        slideView.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(250).start();
        Email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        num.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        confirm.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        signup.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();



        return root;
    }
}
