package com.ivan.sqlite_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update extends AppCompatActivity {

    protected String ID;
    protected EditText euName, euTel, euDescription;
    protected Button btnUpdate, btnDelete;

    protected void CloseThisActivity(){
        finishActivity(200);
        Intent i = new Intent(Update.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        euName=findViewById(R.id.euName);
        euTel=findViewById(R.id.euTel);
        euDescription=findViewById(R.id.euDescription);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);

        Bundle b=getIntent().getExtras();
        if(b!=null){
            ID=b.getString("ID");
            euName.setText(b.getString("name"));
            euTel.setText(b.getString("tel"));
            euDescription.setText(b.getString("description"));
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=null;
                try {
                    db=SQLiteDatabase.openOrCreateDatabase(
                            getFilesDir().getPath()+"/"+"kontakti.db",
                            null
                    );
                    String name=euName.getText().toString();
                    String tel=euTel.getText().toString();
                    String description=euDescription.getText().toString();
                    String q="UPDATE KONTAKTI SET name=?, tel=?, description=? ";
                    q+="WHERE ID=?; ";

                    db.execSQL(q, new Object[]{name, tel, description, ID});
                    Toast.makeText(getApplicationContext(),
                            "Update Successful", Toast.LENGTH_LONG)
                            .show();


                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                            Toast.LENGTH_LONG). show();

                }finally {
                    if(db!=null){
                        db.close();
                        db=null;
                    }
                }
                CloseThisActivity();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=null;
                try {
                    db=SQLiteDatabase.openOrCreateDatabase(
                            getFilesDir().getPath()+"/"+"kontakti.db",
                            null
                    );

                    String q="DELETE FROM KONTAKTI WHERE ";
                    q+="ID=? ";

                    db.execSQL(q, new Object[]{ID});
                    Toast.makeText(getApplicationContext(),
                            "Delete Successful", Toast.LENGTH_LONG)
                            .show();


                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                            Toast.LENGTH_LONG). show();

                }finally {
                    if(db!=null){
                        db.close();
                        db=null;
                    }
                }
                CloseThisActivity();
            }
        });


    }

}