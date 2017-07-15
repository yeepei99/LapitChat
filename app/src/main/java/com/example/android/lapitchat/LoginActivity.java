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

import static com.example.android.lapitchat.R.styleable.MenuItem;

public class LoginActivity extends AppCompatActivity {

    //firebase
    private FirebaseAuth mAuth;

    //toolbar
    private Toolbar mToolbar;

    //android views
    private TextInputLayout mloginEmail;
    private TextInputLayout mloginPass;

    //progress bar
    private ProgressDialog mlogProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //firebase
        mAuth = FirebaseAuth.getInstance();

        //toolbar
        mToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //progress bar
        mlogProgress = new ProgressDialog(this);

        //android fields
        mloginEmail = (TextInputLayout) findViewById(R.id.login_email);
        mloginPass = (TextInputLayout) findViewById(R.id.login_pass);
        Button mloginBtn = (Button) findViewById(R.id.login_btn);

        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mloginEmail.getEditText().getText().toString();
                String pass = mloginPass.getEditText().getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass)){

                    mlogProgress.setTitle("Logging in");
                    mlogProgress.setMessage("Please wait for the process");
                    mlogProgress.setCanceledOnTouchOutside(false);
                    mlogProgress.show();
                    login_user(email,pass);
                }

            }
        });

    }

    private void login_user(String email, String pass) {

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    mlogProgress.dismiss();
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();

                }else{

                    mlogProgress.hide();
                    Toast.makeText(LoginActivity.this, "Please check the form and try again.", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}
