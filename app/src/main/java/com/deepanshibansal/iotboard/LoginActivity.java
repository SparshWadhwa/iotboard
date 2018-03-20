package com.deepanshibansal.iotboard;
import android.content.DialogInterface;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import com.deepanshibansal.iotboard.Model.User;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.rengwuxian.materialedittext.MaterialEditText;

        import java.util.zip.Inflater;

public class LoginActivity extends AppCompatActivity {
    MaterialEditText username,password,email;
    MaterialEditText EnteredUser,EnteredPass;
    Button login,signup;
    FirebaseDatabase database;
    DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.loginBtn);
        signup=findViewById(R.id.signupBtn);
        EnteredUser=findViewById(R.id.Tusername);
        EnteredPass=findViewById(R.id.Tpassword);
        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupDialog();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(EnteredPass.getText().toString(),EnteredUser.getText().toString());
            }
        });
    }

    private void login(final String pass,final String username) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()){
                    if(!username.isEmpty()){
                        User user= dataSnapshot.child(username).getValue(User.class);
                        if(user.getPassword().equals(pass)){
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Enter Correct Password", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Enter some valid username", Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Please Sign Up first", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void signupDialog() {
        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setTitle("Sign Up");
        alertDialog.setMessage("Fill the details correctly");
        LayoutInflater inflater= this.getLayoutInflater();
        View sign_up_layout=inflater.inflate(R.layout.sign_up_layout,null);
        username=(MaterialEditText) sign_up_layout.findViewById(R.id.username);
        password= sign_up_layout.findViewById(R.id.password);
        email= sign_up_layout.findViewById(R.id.email);
        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp); //drawable  right click -->vector asset-->choose desired icon
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final User user = new User(username.getText().toString(), password.getText().toString(), email.getText().toString());
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(user.getUsername()).exists()) {
                            Toast.makeText(LoginActivity.this, "UserName already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            users.child(user.getUsername()).setValue(user);
                            Toast.makeText(LoginActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
