package com.example.android.lapitchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mInputUsername;
    private TextInputLayout mInputEmail;
    private TextInputLayout mInputPass;

    //Toolbar Set
    private Toolbar mToolbar;

    //Firebase auth
    private FirebaseAuth mAuth;

    //Progress bar
    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        //firebase
        mAuth = FirebaseAuth.getInstance();

        //progress bar
        mRegProgress = new ProgressDialog(this);
        
        //android fields
        mInputUsername = (TextInputLayout) findViewById(R.id.reg_display_username);
        mInputEmail = (TextInputLayout) findViewById(R.id.reg_display_email);
        mInputPass = (TextInputLayout) findViewById(R.id.reg_display_pass);
        Button mCreateBtn = (Button) findViewById(R.id.reg_create_btn);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mInputUsername.getEditText().getText().toString();
                String email = mInputEmail.getEditText().getText().toString();
                String pass = mInputPass.getEditText().getText().toString();

                if(!TextUtils.isEmpty(username) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass)){

                    mRegProgress.setTitle("Register in progress...");
                    mRegProgress.setMessage("Please wait while we create your account.");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    register_user(username, email, pass);

                }

            }
        });

    }

    private void register_user(String username, String email, String pass) {

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    mRegProgress.dismiss();
                    Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();

                }else{

                    mRegProgress.hide();
                    Toast.makeText(RegisterActivity.this, "Please check the form and try again.", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}
