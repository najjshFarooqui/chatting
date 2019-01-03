package com.example.najish.chatting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActvity extends AppCompatActivity {
  private EditText userNAme;
  private EditText userPassword;
  private Button login;
  Toolbar toolbar;
  private FirebaseAuth mAuth;
  private static final String TAG="login success";
  private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actvity);
        toolbar=(Toolbar)findViewById(R.id.appbar);
        progress= new ProgressDialog(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login page");

        mAuth= FirebaseAuth.getInstance();

        userNAme=(EditText)findViewById(R.id.userName);
        userPassword=(EditText)findViewById(R.id.userPassword);
        login=(Button)findViewById(R.id.signInButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName= userNAme.getText().toString();
                String password= userPassword.getText().toString();
                if(!TextUtils.isEmpty(userName)|| !TextUtils.isEmpty(password)){
                    progress.setTitle("login in progress");
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();
                    loginUser(userName,password);
                }
            }
        });


    }
    private void  loginUser(String userName, final String password){
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        Intent intent =   new Intent(LoginActvity.this, MainActivity.class);
                        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK |intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        progress.dismiss();
                        finish();
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            progress.hide();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActvity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
