package skripsi.android.andrian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class secretConfirm extends AppCompatActivity {
    Button btnCancelSave, btnConfirmSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secret_confirm_note);
        btnCancelSave = findViewById(R.id.btnCancelSave);
        btnConfirmSaved = findViewById(R.id.btnConfirmSaved);

        btnConfirmSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }// on click
        });//confirm button

        btnCancelSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }// on click
        });//cancel button

    }//on create
}
