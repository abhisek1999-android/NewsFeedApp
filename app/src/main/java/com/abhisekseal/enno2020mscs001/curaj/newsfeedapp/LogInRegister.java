package com.abhisekseal.enno2020mscs001.curaj.newsfeedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;
import org.ocpsoft.prettytime.PrettyTime;

public class LogInRegister extends AppCompatActivity {
    CustomProgressDialogue customProgressDialogue;
    RadioGroup loginSignUpGroup;
    RadioButton radioButton;
    String selectedLabel = "";
    RadioButton login,reg;
    CardView registerLayout,loginLayout;
    Button actionButton;
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    CheckedTextView terms;
    ImageButton googleSignIn;
    ImageButton facebookSignIn;
    TextView regText,loginText,forgotPassText;
    TextInputLayout personName,emailSignup,emailLogin,passwordSignup,passwordLogin,userPhone;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    PrettyTime p = new PrettyTime();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_register);
        customProgressDialogue=new CustomProgressDialogue(LogInRegister.this);
        registerLayout=findViewById(R.id.registerLayout);
        loginLayout=findViewById(R.id.loginLayout);
        actionButton=findViewById(R.id.actionButton);
        googleSignIn=findViewById(R.id.googleLogin);
        facebookSignIn=findViewById(R.id.faceBookLogin);
        terms=findViewById(R.id.termsAndCondition);
        forgotPassText=findViewById(R.id.forgotPass);

        personName=findViewById(R.id.personName);
        emailLogin=findViewById(R.id.emailLogin);
        emailSignup=findViewById(R.id.emailSignup);
        passwordLogin=findViewById(R.id.passwordLogin);
        passwordSignup=findViewById(R.id.passwordSignup);
        userPhone=findViewById(R.id.userPhone);

        mAuth=FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();

        regText=findViewById(R.id.regNowText);
        loginText=findViewById(R.id.loginNowText);


        login=findViewById(R.id.logIn);
        reg=findViewById(R.id.reg);
        login.setChecked(true);

        loginSignUpGroup=findViewById(R.id.loginRegGroup);


        regText.setOnClickListener(v -> {

            changeLayout("Sign up");
            reg.setChecked(true);

        });

       loginText.setOnClickListener(v -> {

            changeLayout("Login");
            login.setChecked(true);

        });
        terms.setOnClickListener(v->{
            terms.setChecked(!terms.isChecked());
        });

       loginSignUpGroup.setOnCheckedChangeListener((group, checkedId) -> {
            radioButton = (RadioButton) findViewById(checkedId);
            selectedLabel=radioButton.getText().toString();

           changeLayout(selectedLabel);

        });

       actionButton.setOnClickListener(v->{

           if (actionButton.getText().toString().equals("Login")){
               login();
           }else{

               createAccount();
           }

       });


       if (mCurrentUser!=null){
           startActivity(new Intent(getApplicationContext(),MainActivity.class));
       }

       googleSignIn.setOnClickListener(v->{
           startActivity(new Intent(getApplicationContext(),GoogleSignInActivity.class));


       });
        facebookSignIn.setOnClickListener(v->{
            Intent intent=new Intent(getApplicationContext(),FacebookAuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);


        });
        
        forgotPassText.setOnClickListener(v->{
            forgotPass();
        });
    }
    //THIS METHOD IS USED FOR FORGOT PASSWORD

    private void forgotPass(){

        if(!emailLogin.getEditText().getText().toString().equals("")&& emailLogin.getEditText().getText().toString().matches(emailPattern)){
            String mail=emailLogin.getEditText().getText().toString();
            mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(LogInRegister.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(LogInRegister.this, "Reset Link Is Not Sent", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "Not a valid email id", Toast.LENGTH_SHORT).show();
        }
    }

    //THIS METHOD IS USED TO SWITCH BETWEEN SIGNUP AND LOGIN.................................
    private void changeLayout(String selectedLabel) {

        if (selectedLabel.equals("Login")){
            registerLayout.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);
            actionButton.setText("Login");
        }
        if (selectedLabel.equals("Sign up")){
            registerLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
            actionButton.setText("Sign up");
        }

    }
// METHOD FOR LOGIN USER..............................................
    private void login(){

        if (!emailLogin.getEditText().getText().toString().equals("") && emailLogin.getEditText().getText().toString().matches(emailPattern))
            emailLogin.setError(null);

        if (!passwordLogin.getEditText().getText().toString().equals(""))
            passwordLogin.setError(null);

        if (emailLogin.getEditText().getText().toString().equals("")){

            emailLogin.setError("User Id Required");
            return;
        }
        else if (passwordLogin.getEditText().getText().toString().equals("")){

            passwordLogin.setError("Password required");
            return;
        }

        if (!emailLogin.getEditText().getText().toString().matches(emailPattern)){
            emailLogin.setError("Invalid Email");
            return;
        }


        else if (!emailLogin.getEditText().getText().toString().equals("") && !passwordLogin.getEditText().getText().toString().equals("")){

         customProgressDialogue.show();
            mAuth.signInWithEmailAndPassword(emailLogin.getEditText().getText().toString(),passwordLogin.getEditText().getText().toString()).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        customProgressDialogue.dismiss();

                    } else {
                     customProgressDialogue.dismiss();
                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Error While Processing Request", Snackbar.LENGTH_SHORT);
                        View snakBarView = snackbar.getView();
                        snakBarView.setBackgroundColor(Color.parseColor("#ef5350"));
                        snackbar.show();

                    }
                }
            });
        }
    }
    // METHOD FOR CREATING USER..............................................
    private void createAccount() {

      customProgressDialogue.show();
        if (!emailSignup.getEditText().getText().toString().matches(emailPattern)){
            emailSignup.setError("Invalid Email");
            return;
        }


        if (emailSignup.getEditText().getText().toString().equals("") && passwordSignup.getEditText().getText().toString().equals("") && personName.getEditText().getText().toString().equals("")) {


         customProgressDialogue.dismiss();
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "AllInputs Must be given", Snackbar.LENGTH_SHORT);
            View snakBarView = snackbar.getView();
            snakBarView.setBackgroundColor(Color.parseColor("#ef5350"));
            snackbar.show();

        } else {

            mAuth.createUserWithEmailAndPassword(emailSignup.getEditText().getText().toString(), passwordSignup.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        customProgressDialogue.dismiss();
                        Toast.makeText(getApplicationContext(), "Registered Successfully. You can Login now!!!", Toast.LENGTH_SHORT).show();
                        changeLayout("Login");
                        login.setChecked(true);

                    } else {

                        customProgressDialogue.dismiss();
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), task.getException().toString(), Snackbar.LENGTH_SHORT);
                            View snakBarView = snackbar.getView();
                            snakBarView.setBackgroundColor(Color.parseColor("#ef5350"));
                            snackbar.show();

                    }

                }
            });

        }


    }
}