package com.example.clientecrudproviderehvm.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.clientecrudproviderehvm.R;
import com.example.clientecrudproviderehvm.provider.MiProveedorContenidoContract;

public class Modificar extends AppCompatActivity {
    Cursor cursor;

    Button btnAceptar;
    Button btnCancelar;
    String idUs;
    TextView txtNombre;
    TextView txtPas;
    TextView txtEmail;
    TextView txtTel;
    private static final String TAG="Prueba";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        Intent intent= getIntent();
        idUs=intent.getStringExtra("id");
        txtNombre=findViewById(R.id.txtNombre);
        txtPas=findViewById(R.id.txtPassword);
        txtEmail=findViewById(R.id.txtEmail);
        txtTel=findViewById(R.id.txtTel);
        btnAceptar=findViewById(R.id.btnGuardar);
        btnCancelar=findViewById(R.id.btnCancelar);
        consultar();
                txtNombre.setText(cursor.getString(cursor.getColumnIndex("nombre")));
                txtEmail.setText(cursor.getString(cursor.getColumnIndex("email")));
                txtPas.setText(cursor.getString(cursor.getColumnIndex("password")));
                txtTel.setText(cursor.getString(cursor.getColumnIndex("tel")));
        ContentResolver cr=getApplicationContext().getContentResolver();
        ContentValues cv=new ContentValues();
        btnAceptar.setOnClickListener(l->{
            cv.put(MiProveedorContenidoContract.Usuarios._ID,cursor.getString(cursor.getColumnIndex("_id")));
            cv.put(MiProveedorContenidoContract.Usuarios.NOMBRE,txtNombre.getText().toString());
            cv.put(MiProveedorContenidoContract.Usuarios.PASS,txtPas.getText().toString());
            cv.put(MiProveedorContenidoContract.Usuarios.EMAIL,txtEmail.getText().toString());
            cv.put(MiProveedorContenidoContract.Usuarios.TELEFONO,txtTel.getText().toString());
            int resultado=cr.update(MiProveedorContenidoContract.Usuarios.CONTENT_URI,cv,null,null);
        Log.d(TAG,resultado+"");
        finish();
        });
        btnCancelar.setOnClickListener(l->{
            finish();
        });
}
        public void consultar(){
            Uri uri=Uri.parse(MiProveedorContenidoContract.Usuarios.CONTENT_URI.toString()+"/"+idUs);
            cursor = getBaseContext().getContentResolver().query(uri,null,null,null,null);
            cursor.moveToFirst();
            Log.d(TAG,cursor.getString(cursor.getColumnIndex("_id")));
        }
}