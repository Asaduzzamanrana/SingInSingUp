package com.example.singinsingup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ueserNameEditText,passEditText;
    private Button loginButton;
    private TextView singUpTextView;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("LogIn Page");

        mAuth = FirebaseAuth.getInstance();

        singUpTextView=findViewById(R.id.singUpTextViewId);
        ueserNameEditText=findViewById(R.id.userNameEditTextId);
        passEditText=findViewById(R.id.passwordEditTextId);
        loginButton=findViewById(R.id.logInButtonId);
        progressBar=findViewById(R.id.progressbarid);

        loginButton.setOnClickListener(this);
        singUpTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.logInButtonId:
                    userLogin();
                break;


                case R.id.singUpTextViewId:
                    Intent intent=new Intent(getApplicationContext(),SingUpActivity.class);
                    startActivity(intent);

                break;

        }
    }

    private void userLogin() {
        String email=ueserNameEditText.getText().toString().trim();
        String password=passEditText.getText().toString().trim();

        if (email.isEmpty())
        {
            ueserNameEditText.setError("Enter Email Please !");
            ueserNameEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            ueserNameEditText.setError("Enter Email Please !");
            ueserNameEditText.requestFocus();
            return;
        }

        if (password.isEmpty())
        {
            passEditText.setError("Enter password Please !");
            passEditText.requestFocus();
            return;
        }

        if (password.length()<6)
        {
            passEditText.setError("Minimum password length 6");
            passEditText.requestFocus();
            return;
        }
     progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                    finish();
                    Intent intent = new Intent(getApplicationContext(),NewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Login Not Successful",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
