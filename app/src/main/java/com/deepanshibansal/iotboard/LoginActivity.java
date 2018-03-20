package com.deepanshibansal.iotboard;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

        import com.deepanshibansal.iotboard.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

public class LoginActivity extends AppCompatActivity {
    private EditText etxtPhone;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText etxtPhoneCode,nameField,emailField;
    private String mVerificationId;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mNameDatabaseReference;
    private DatabaseReference mPhnNo_databaseREference;

    public static String student_name="",student_phnNo="",user_email="";
    public  static String Uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        //mNameDatabaseReference=mFirebaseDatabase.getReference().child("users").child("schoolname").child(sclname).child(student_name);
        mPhnNo_databaseREference = mFirebaseDatabase.getReference();

        etxtPhone = (EditText) findViewById(R.id.phoneField);
        etxtPhoneCode = (EditText) findViewById(R.id.codeField);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    //  Toast.makeText(StudentInfo.this, getString(R.string.now_logged_in) + firebaseAuth.getCurrentUser().getProviderId(), Toast.LENGTH_SHORT).show();
                    // isStudent = true;
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    public void requestCode(View view) {
        String phoneNumber = etxtPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber))
            return;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, 60, TimeUnit.SECONDS, LoginActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        //Called if it is not needed to enter verification code
                        signInWithCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        //incorrect phone number, verification code, emulator, etc.
                        Toast.makeText(LoginActivity.this, "onVerificationFailed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        //now the code has been sent, save the verificationId we may need it
                        super.onCodeSent(verificationId, forceResendingToken);

                        mVerificationId = verificationId;
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String verificationId) {
                        //called after timeout if onVerificationCompleted has not been called
                        super.onCodeAutoRetrievalTimeOut(verificationId);
                        Toast.makeText(LoginActivity.this, "onCodeAutoRetrievalTimeOut :" + verificationId, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void signInWithCredential(final PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            student_phnNo = etxtPhone.getText().toString();

                            FirebaseUser user = task.getResult().getUser();
                            Uid= user.getUid();

                            mPhnNo_databaseREference.child("users").child(Uid).child("phone no:").setValue(student_phnNo);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));



//                            if (status == 1){
//                                mPhnNo_databaseREference.child("users").child(Uid).child("status").setValue("student");




                        } else {
                            Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signIn(View view) {
        String code = etxtPhoneCode.getText().toString();
        if (TextUtils.isEmpty(code))
            return;

        signInWithCredential(PhoneAuthProvider.getCredential(mVerificationId, code));
    }

}
