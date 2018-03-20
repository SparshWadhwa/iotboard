package com.deepanshibansal.iotboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    RecyclerView recyclerView;
    static public String uid = "";
    private DatabaseReference mFirebaseDatabaseRef , boardsDatabaseRef;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFirebaseAuth = FirebaseAuth.getInstance();

        if(mFirebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(this,abcActivity.class));
        }
        else{
            uid = mFirebaseAuth.getCurrentUser().getUid();
        }

        mFirebaseDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        final List<String> lst = new ArrayList<>();

        if(uid.equals("")){
            Toast.makeText(getApplicationContext() , "uid not fetched" , Toast.LENGTH_SHORT).show();
            lst.add("no config Boards yet.");
        }
        else{
            Toast.makeText(getApplicationContext() , "uid  fetched =" + uid , Toast.LENGTH_SHORT).show();
            boardsDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
            boardsDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                        Toast.makeText(getApplicationContext() , "list item : " +dsp.getKey()  , Toast.LENGTH_SHORT).show();
                        lst.add(String.valueOf(dsp.getKey()));

                    }
                    // Toast.makeText(getActivity())
                    mAdapter = new RecyclerViewadapter(getApplicationContext(), lst);
                    recyclerView.setAdapter(mAdapter);
                    Toast.makeText(getApplicationContext() , "list size is " + lst.size()  , Toast.LENGTH_SHORT).show();



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
//            recyclerView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new  Intent (getApplicationContext() , ExtensionBoard_activity.class) );
//                }
//            });


        }
        final Context context = this;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                final View promptsView = li.inflate(R.layout.configpage, null);

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
final MaterialEditText idnum=(MaterialEditText)promptsView.findViewById(R.id.edittext);
                final MaterialEditText boardName=(MaterialEditText)promptsView.findViewById(R.id.editboardName);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Config", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
//                                if (TextUtils.isEmpty(idnum.getText().toString())) {
//                                    YoYo.with(Techniques.Shake)
//                                            .duration(500)
//                                            .repeat(1)
//                                            .playOn(promptsView.findViewById(R.id.edittext));
//
//                                }
//                                else if(TextUtils.isEmpty(boardName.getText().toString())){
//                                    YoYo.with(Techniques.Shake)
//                                            .duration(500)
//                                            .repeat(1)
//                                            .playOn(promptsView.findViewById(R.id.edittext));
//                                }
                                final String boardNamefianl = boardName.getText().toString();
                                Toast.makeText(getApplicationContext() , boardNamefianl , Toast.LENGTH_SHORT).show();

                                final String boardidfianl = idnum.getText().toString();
                                Toast.makeText(getApplicationContext() , boardidfianl , Toast.LENGTH_SHORT).show();
                                mFirebaseDatabaseRef.child(uid).child(boardNamefianl).child("uniqueid").setValue(boardidfianl);
                             //
                                //   startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
