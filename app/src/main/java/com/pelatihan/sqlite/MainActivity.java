package com.pelatihan.sqlite;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity{

//    Actually it can use methods/functions for saving resources

//    All Component Except Button
    private DatabaseAdaptor db;
    private Dialog dialog_create;
    private Dialog dialog_read;
    private Dialog dialog_update;
    private Dialog dialog_delete;
    private EditText createUsername;
    private EditText createPassword;
    private EditText readUsername;
    private EditText readPassword;
    private EditText updateUsername;
    private EditText updatePassword;
    private Spinner readId;
    private Spinner updateId;
    private Spinner deleteId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Main Dialog Component
        db = new DatabaseAdaptor(this);
        dialog_create = new Dialog(this);
        dialog_read = new Dialog(this);
        dialog_update = new Dialog(this);
        dialog_delete = new Dialog(this);

//        Main Layout Component
        Button btnCreate = findViewById(R.id.btnCreate);
        Button btnRead = findViewById(R.id.btnRead);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnDelete = findViewById(R.id.btnDelete);
        dialog_create.setContentView(R.layout.dialog_create);
        dialog_read.setContentView(R.layout.dialog_read);
        dialog_update.setContentView(R.layout.dialog_update);
        dialog_delete.setContentView(R.layout.dialog_delete);

//        Create Section
        createUsername = dialog_create.findViewById(R.id.createUsername);
        createPassword = dialog_create.findViewById(R.id.createPassword);
        Button createCreate = dialog_create.findViewById(R.id.createCreate);

//        Read Section
        readId = dialog_read.findViewById(R.id.readId);
        readUsername = dialog_read.findViewById(R.id.readUsername);
        readPassword = dialog_read.findViewById(R.id.readPassword);

//        Update Section
        updateId = dialog_update.findViewById(R.id.updateId);
        updateUsername = dialog_update.findViewById(R.id.updateUsername);
        updatePassword = dialog_update.findViewById(R.id.updatePassword);
        Button updateUpdate = dialog_update.findViewById(R.id.updateUpdate);

//        Delete Section
        deleteId = dialog_delete.findViewById(R.id.deleteId);
        Button deleteDelete = dialog_delete.findViewById(R.id.deleteDelete);

        dialog_create.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_read.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_update.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_delete.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        Button Create Section
        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog_create.show();
            }
        });

        createCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = createUsername.getText().toString();
                String password = createPassword.getText().toString();
                HashMap<String, String> data = new HashMap<>();
                data.put("username", username);
                data.put("password", password);
                long check = db.insert2DB(data);
                if(check<=0) {
                    Toast.makeText(MainActivity.this,"insert2DB Failed",Toast.LENGTH_SHORT).show();
                } else {
                    createUsername.setText("");
                    createPassword.setText("");
                    dialog_create.dismiss();
                    Toast.makeText(MainActivity.this,"insert2DB Success",Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Button Read Section
        btnRead.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ArrayList<HashMap<String, String>> data = db.getData();
                ArrayList<String> listId = new ArrayList<>();
                for(HashMap<String, String> values : data) {
                    listId.add(values.get("id"));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, listId);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                readId.setAdapter(arrayAdapter);

                dialog_read.show();
            }
        });

        readId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id){
                String selected = parent.getSelectedItem().toString();
                ArrayList<HashMap<String, String>> data = db.getData();
                for(HashMap<String, String> values : data) {
                    if(values.get("id").equals(selected)) {
                        readUsername.setText(values.get("username"));
                        readPassword.setText(values.get("password"));
                        break;
                    } else {
                        readUsername.setText("");
                        readPassword.setText("");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                readUsername.setText("");
                readPassword.setText("");
            }
        });

//        Button Update Section
        btnUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ArrayList<HashMap<String, String>> data = db.getData();
                ArrayList<String> listId = new ArrayList<>();
                for(HashMap<String, String> values : data) {
                    listId.add(values.get("id"));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, listId);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                updateId.setAdapter(arrayAdapter);

                dialog_update.show();
            }
        });

        updateUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String selected = updateId.getSelectedItem().toString();
                String username = updateUsername.getText().toString();
                String password = updatePassword.getText().toString();
                HashMap<String, String> data = new HashMap<>();
                data.put("username", username);
                data.put("password", password);
                int check = db.updateData(selected,data);
                if(check<=0) {
                    Toast.makeText(MainActivity.this,"updateDB Failed",Toast.LENGTH_SHORT).show();
                } else {
                    dialog_update.dismiss();
                    Toast.makeText(MainActivity.this,"updateDB Success",Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id){
                String selected = parent.getSelectedItem().toString();
                ArrayList<HashMap<String, String>> data = db.getData();
                for(HashMap<String, String> values : data) {
                    if(values.get("id").equals(selected)) {
                        updateUsername.setText(values.get("username"));
                        updatePassword.setText(values.get("password"));
                        break;
                    } else {
                        updateUsername.setText("");
                        updatePassword.setText("");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                updateUsername.setText("");
                updatePassword.setText("");
            }
        });

//        Button Delete Section
        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ArrayList<HashMap<String, String>> data = db.getData();
                ArrayList<String> listId = new ArrayList<>();
                for(HashMap<String, String> values : data) {
                    listId.add(values.get("id"));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, listId);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                deleteId.setAdapter(arrayAdapter);

                dialog_delete.show();
            }
        });

        deleteDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String selected = deleteId.getSelectedItem().toString();
                int check = db.deleteData(selected);
                if(check<=0) {
                    Toast.makeText(MainActivity.this,"deleteData Failed",Toast.LENGTH_SHORT).show();
                } else {
                    readUsername.setText("");
                    readPassword.setText("");
                    updateUsername.setText("");
                    updatePassword.setText("");
                    dialog_delete.dismiss();
                    Toast.makeText(MainActivity.this,"deleteData Success",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
