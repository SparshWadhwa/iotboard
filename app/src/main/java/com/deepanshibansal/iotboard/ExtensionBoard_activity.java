package com.deepanshibansal.iotboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExtensionBoard_activity extends AppCompatActivity {
private Switch switch1 , switch2 , switch3 ,switch4 , switch5;
private DatabaseReference switchDatabaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension_board_activity);
        switchDatabaseref = FirebaseDatabase.getInstance().getReference();
        switch1= (Switch) findViewById(R.id.swi1);
        switch2= (Switch) findViewById(R.id.swi2);
        switch3= (Switch) findViewById(R.id.swi3);
        switch4= (Switch) findViewById(R.id.swi4);
        switch5= (Switch) findViewById(R.id.swi5);
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switch1.isChecked()){
                    Toast.makeText(getApplicationContext() , "switch1 on" , Toast.LENGTH_SHORT).show();
                    switchDatabaseref.child("redlight").setValue(1);
                }
                else{
                    Toast.makeText(getApplicationContext() , "switch1 off" , Toast.LENGTH_SHORT).show();
                switchDatabaseref.child("redlight").setValue(0);}
            }
        });
        //==========================>

        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switch1.isChecked()){
                    Toast.makeText(getApplicationContext() , "switch1 on" , Toast.LENGTH_SHORT).show();
                    switchDatabaseref.child("redlight").setValue(1);
                }
                else
                {  Toast.makeText(getApplicationContext() , "switch1 off" , Toast.LENGTH_SHORT).show();
                switchDatabaseref.child("redlight").setValue(0);}
            }
        });
        //==========================>

        switch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switch1.isChecked()){
                    Toast.makeText(getApplicationContext() , "switch1 on" , Toast.LENGTH_SHORT).show();
                    switchDatabaseref.child("redlight").setValue(1);
                }
                else
                {  Toast.makeText(getApplicationContext() , "switch1 off" , Toast.LENGTH_SHORT).show();
                switchDatabaseref.child("redlight").setValue(0);}
            }
        });
        //==========================>

        switch4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switch1.isChecked()){
                    Toast.makeText(getApplicationContext() , "switch1 on" , Toast.LENGTH_SHORT).show();
                    switchDatabaseref.child("redlight").setValue(1);
                }
                else
                { Toast.makeText(getApplicationContext() , "switch1 off" , Toast.LENGTH_SHORT).show();
                switchDatabaseref.child("redlight").setValue(0);}
            }
        });
        //==========================>

        switch5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switch1.isChecked()){
                    Toast.makeText(getApplicationContext() , "switch1 on" , Toast.LENGTH_SHORT).show();
                    switchDatabaseref.child("redlight").setValue(1);
                }
                else
                {  Toast.makeText(getApplicationContext() , "switch1 off" , Toast.LENGTH_SHORT).show();
                switchDatabaseref.child("redlight").setValue(0);}
            }
        });
        //==========================>


    }
}
