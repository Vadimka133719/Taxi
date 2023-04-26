package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
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

public class DriverRegLoginActivity extends AppCompatActivity {

    TextView driverStatus, question;
    Button signInBtn, signUpBtn;
    EditText emailET, passwordET;

    FirebaseAuth mAuth;

    ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driever_reg_login);

        driverStatus = (TextView)findViewById(R.id.statusDriver);
        question = (TextView)findViewById(R.id.accountCreate);
        signInBtn = (Button)findViewById(R.id.signInDriver);
        signUpBtn = (Button)findViewById(R.id.singUpDriver);
        emailET = (EditText)findViewById(R.id.driverEmail);
        passwordET = (EditText)findViewById(R.id.driverPassword);

        mAuth = FirebaseAuth.getInstance();
        LoadingBar = new ProgressDialog(this);

        signUpBtn.setVisibility(View.INVISIBLE);
        signUpBtn.setEnabled(false);

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInBtn.setVisibility(View.INVISIBLE);
                question.setVisibility(View.INVISIBLE);
                signUpBtn.setVisibility(View.VISIBLE);
                signUpBtn.setEnabled(true);
                driverStatus.setText("Регистрация для водителей");
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                RegisterDriver(email, password);
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                SignInDriver(email, password);
            }
        });
    }

    private void SignInDriver(String email, String password)
    {
        LoadingBar.setTitle("Вход водителя");
        LoadingBar.setMessage("Пожалуйста, дождитесь загрузки");
        LoadingBar.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(DriverRegLoginActivity.this, "Вход произошёл успешно", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                }
                else
                {
                    Toast.makeText(DriverRegLoginActivity.this, "Произошла ошибка, попробуйте снова", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                }
            }
        });
    }

    private void RegisterDriver(String email, String password)
    {
        LoadingBar.setTitle("Регистрция водителя");
        LoadingBar.setMessage("Пожалуйста, дождитесь загрузки");
        LoadingBar.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(DriverRegLoginActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();

                }
                else 
                    {
                        Toast.makeText(DriverRegLoginActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                        LoadingBar.dismiss();
                }
            }
        });
    }
}