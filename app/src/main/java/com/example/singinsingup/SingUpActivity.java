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

public class SingUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText singUpEmailEditext,singUppassEditText,firstNameEditText,lastNameEditText;
    private Button singupbutton;
    private TextView singintextview;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        this.setTitle("SingUp Page");

        mAuth = FirebaseAuth.getInstance();

        progressBar=findViewById(R.id.progressBarId);
        singUpEmailEditext=findViewById(R.id.singUpEmailEditTextId);
        singUppassEditText=findViewById(R.id.singUpPassEditTextId);
        firstNameEditText=findViewById(R.id.firstNameEditTextId);
        lastNameEditText=findViewById(R.id.lastNameEditTextId);

        singupbutton=findViewById(R.id.singUpButtonId);
        singintextview=findViewById(R.id.singInTextViewId);

        singupbutton.setOnClickListener(this);
        singintextview.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.singUpButtonId:
                userRegister();

                break;

            case R.id.singInTextViewId:
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;

        }

    }

    private void userRegister() {

        String firstName=firstNameEditText.getText().toString().trim();
        String lastName=lastNameEditText.getText().toString().trim();
        String email=singUpEmailEditext.getText().toString().trim();
        String password=singUppassEditText.getText().toString().trim();

        if (email.isEmpty())
            {
                singUpEmailEditext.setError("Enter Email Please !");
                singUpEmailEditext.requestFocus();
                return;
            }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                singUpEmailEditext.setError("Enter Email Please !");
                singUpEmailEditext.requestFocus();
                return;
            }

        if (password.isEmpty())
        {
            singUppassEditText.setError("Enter password Please !");
            singUppassEditText.requestFocus();
            return;
        }

        if (password.length()<6)
            {
                singUppassEditText.setError("Minimum password length 6");
                singUppassEditText.requestFocus();
                return;
            }

        if (firstName.isEmpty())
        {
            firstNameEditText.setError("Please Enter first Name");
            firstNameEditText.requestFocus();
            return;
        }
        if (firstName.length()<3)
        {
            firstNameEditText.setError("Minimum length 3");
            firstNameEditText.requestFocus();
            return;
        }
        if (lastName.isEmpty())
        {
            lastNameEditText.setError("Please Enter last Name");
            lastNameEditText.requestFocus();
            return;
        }
        if (lastName.length()<3)
        {
            lastNameEditText.setError("Minimum length 3");
            lastNameEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        //clear data
                        singUpEmailEditext.setText("");
                        singUppassEditText.setText("");
                        firstNameEditText.setText("");
                        lastNameEditText.setText("");

                        if (task.isSuccessful())
                            {

                                Toast.makeText(getApplicationContext(),"Register is Successful",Toast.LENGTH_SHORT).show();


                            }

                        else
                            {
                                if (task.getException()instanceof FirebaseAuthUserCollisionException)
                                {
                                    Toast.makeText(getApplicationContext(),"User already register",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }

                            }


                    }
                });

    }
}
