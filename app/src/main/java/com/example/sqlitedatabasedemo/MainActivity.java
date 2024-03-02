package com.example.sqlitedatabasedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDataBaseHelper myDataBaseHelper;
    private TextInputEditText IdEditText, nameEd, ageEd, genderEd;
    private Button insertData, showData, updateData, deleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IdEditText = findViewById(R.id.IdEditText);
        nameEd = findViewById(R.id.nameId);
        ageEd = findViewById(R.id.ageId);
        genderEd = findViewById(R.id.genderId);
        insertData = findViewById(R.id.insertData);
        showData = findViewById(R.id.showData);
        updateData = findViewById(R.id.updateData);
        deleteData = findViewById(R.id.deleteData);


        myDataBaseHelper = new MyDataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getWritableDatabase();

        insertData.setOnClickListener(this);
        showData.setOnClickListener(this);
        updateData.setOnClickListener(this);
        deleteData.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        //
        String id = IdEditText.getText().toString().trim();
        String name = nameEd.getText().toString().trim();
        String age = ageEd.getText().toString().trim();
        String gender = genderEd.getText().toString().trim();
        IdEditText.setText("");
        nameEd.setText("");
        ageEd.setText("");
        genderEd.setText("");

        //DATA INSERT >>>>
        if (v.getId() == R.id.insertData) {

            long rowId = myDataBaseHelper.insertData(name, age, gender);
            if (rowId > 0)
                Toast.makeText(MainActivity.this, rowId + " Row insert Successfully ", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(MainActivity.this, "Data insert failed!", Toast.LENGTH_SHORT).show();
        }

        //DATA SHOW >>>>
        else if (v.getId() == R.id.showData) {

            Cursor cursor = myDataBaseHelper.displayData();
            if (cursor.getCount() == 0)
                Toast.makeText(MainActivity.this, "There is no data", Toast.LENGTH_SHORT).show();
            else {
                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    stringBuffer.append("ID : " + cursor.getString(0) + "\n");
                    stringBuffer.append("NAME : " + cursor.getString(1) + "\n");
                    stringBuffer.append("AGE : " + cursor.getString(2) + "\n");
                    stringBuffer.append("GENDER : " + cursor.getString(3) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("ResultSet");
                builder.setMessage(stringBuffer.toString());
                builder.setCancelable(true);
                builder.show();
            }
        }

        //DATA UPDATE >>>>
        else if (v.getId() == R.id.updateData) {

            boolean b = myDataBaseHelper.updateData(id, name, age, gender);
            if (b) Toast.makeText(MainActivity.this, "Data update successful", Toast.LENGTH_SHORT).show();
            else Toast.makeText(MainActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
        }

        //DATA DELETE >>>>
        else if (v.getId()==R.id.deleteData) {

            if (id.isEmpty()) Toast.makeText(this, "Please insert a ID", Toast.LENGTH_SHORT).show();
            else {

                int value = myDataBaseHelper.deleteData(id);
                if (value>0) Toast.makeText(this, id+" Row is deleted!", Toast.LENGTH_SHORT).show();
                else Toast.makeText(this, "Data is not deleted", Toast.LENGTH_SHORT).show();
            }
        }

    }


}