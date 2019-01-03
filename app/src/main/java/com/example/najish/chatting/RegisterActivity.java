package com.example.najish.chatting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.najish.chatting.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    private static final String TAG = "registerActivity";
    private Toolbar mToolbar;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
       mToolbar=(Toolbar)findViewById(R.id.appbar);
       setSupportActionBar(mToolbar);
       getSupportActionBar().setTitle("Register yourself");
        progressBar= new ProgressDialog(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registration");

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etNAme = binding.etNAme.getText().toString();
                String etEmail = binding.etEmail.getText().toString();
                String etPassword = binding.etPassword.getText().toString();
                if(!TextUtils.isEmpty(etNAme)|| !TextUtils.isEmpty(etEmail) || !TextUtils.isEmpty(etPassword)){
                    progressBar.setTitle("Registration progress");
                    progressBar.setMessage("please wait");
                    progressBar.setCanceledOnTouchOutside(false);
                    progressBar.show();
                    registerUser(etNAme, etEmail, etPassword);
            }
                }
        });
        }

    private void registerUser(final String name, String email, final String password) {
        Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           progressBar.dismiss();
                           FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                           String userId= currentUser.getUid();

                           FirebaseDatabase database = FirebaseDatabase.getInstance();
                           DatabaseReference myRef = database.getReference().child("users").child(userId);

                           HashMap<String ,String> userDAta = new HashMap<>();
                           userDAta.put("name",name);
                           userDAta.put("status","hey their");
                           userDAta.put("image","default");
                           myRef.setValue(userDAta).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       progressBar.dismiss();
                                       startActivity(new Intent(RegisterActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                       finish();
                                   }
                               }
                           });

                            // Sign in success, update UI with the signed-in user's information

                        } else {
                            progressBar.hide();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


}
