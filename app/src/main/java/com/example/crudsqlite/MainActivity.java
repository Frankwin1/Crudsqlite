package com.example.crudsqlite;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static java.sql.Types.VARCHAR;


public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {
    SQLiteDatabase db;
    EditText editsearchname,editempname,editempmail,editempsalary;
    Button Add, Delete, Modify, View,search ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create database,EmployeeDB database name
        db=openOrCreateDatabase("EmployeeDB", Context.MODE_PRIVATE, null);
        //create table Employee
        db.execSQL("CREATE TABLE IF NOT EXISTS Employee(EmpId INTEGER PRIMARY KEY AUTOINCREMENT,EmpName VARCHAR,EmpMail VARCHAR,EmpSalary VARCHAR);");
        editsearchname = (EditText) findViewById(R.id.edtemployeename);
        editempname = (EditText) findViewById(R.id.editText);
        editempmail = (EditText) findViewById(R.id.editText2);
        editempsalary = (EditText) findViewById(R.id.editText3);
        Add = (Button) findViewById(R.id.btnsave);
        Delete= (Button) findViewById(R.id.btndel);
        Modify= (Button) findViewById(R.id.btnupdate);
        View= (Button) findViewById(R.id. btnselect);
        search=(Button) findViewById(R.id. btnselectperticular);
        Add.setOnClickListener(this);
        Delete.setOnClickListener(this);
        Modify.setOnClickListener(this);
        View.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    public void msg(Context context,String str)
    {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnsave)
        {
            // code for save data
            if(editempname.getText().toString().trim().length()==0||
                    editempmail.getText().toString().trim().length()==0||
                    editempsalary.getText().toString().trim().length()==0)
            {
                msg(this, "Por Favor, llena los espacios en blanco");
                return;
            }
            db.execSQL("INSERT INTO Employee(EmpName,EmpMail,EmpSalary)VALUES('"+ editempname.getText()+"','"+ editempmail.getText()+ "','"+    editempsalary.getText()+"');");
            msg(this, "Agregado y grabado");
        }

        else if(v.getId()==R.id.btnupdate)
        {
            //code for update data
            if(editsearchname.getText().toString().trim().length()==0)
            {
                msg(this, "Ingresa nombre del empleado");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM Employee WHERE EmpName='"+ editsearchname.getText()+"'", null);
            if(c.moveToFirst()) {
                db.execSQL("UPDATE Employee  SET EmpName ='"+ editempname.getText()+"', EmpMail='"+ editempmail.getText()+"',EmpSalary='"+      editempsalary.getText()+"' WHERE EmpName ='"+editsearchname.getText()+"'");
                msg(this, "Se modificó");
            }
            else
            {
                msg(this, "Nombre inválido");
            }
        }
        else if(v.getId()==R.id.btndel)
        {
            //code for delete data
            if(editsearchname.getText().toString().trim().length()==0)
            {
                msg(this, " Por favor , ingrese el nombre ");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM Employee WHERE EmpName ='"+ editsearchname.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM Employee WHERE EmpName ='"+ editsearchname.getText()+"'");
                msg(this, "Se borro del registro");
            }
            else
            {
                msg(this, "Nombre inválido ");
            }
        }
        else if (v.getId() == R.id.btnselect)
        {
            //code for select all data
            Cursor c=db.rawQuery("SELECT * FROM Employee", null);
            if(c.getCount()==0)
            {
                msg(this, "No encontramos el nombre");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                buffer.append("Employee Name: "+c.getString(1)+"\n");
                buffer.append("Employee Mail: "+c.getString(2)+"\n\n");
                buffer.append("Employee Salary: "+c.getString(3)+"\n\n");
            }
            msg(this, buffer.toString());
        }
        else if(v.getId()==R.id.btnselectperticular)
        {
            //code for select particular data
            if(editsearchname.getText().toString().trim().length()==0)
            {
                msg(this, "Ingrese el nombre");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM Employee WHERE EmpName='"+editsearchname.getText()+"'", null);
            if(c.moveToFirst())
            {
                editempname.setText(c.getString(1));
                editempmail.setText(c.getString(2));
                editempsalary.setText(c.getString(3));
            }
            else
            {
                msg(this, "Nombre inválido");
            }
        }
    }
}
