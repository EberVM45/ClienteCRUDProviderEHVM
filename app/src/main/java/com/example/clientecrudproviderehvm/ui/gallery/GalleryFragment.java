package com.example.clientecrudproviderehvm.ui.gallery;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.clientecrudproviderehvm.R;
import com.example.clientecrudproviderehvm.provider.MiProveedorContenidoContract;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    Button btnAceptar;
    TextView txtNombre;
    TextView txtPass;
    TextView txtEmail;
    TextView txtTel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        btnAceptar=root.findViewById(R.id.btnGuardar);
        txtNombre = (TextView) root.findViewById(R.id.txtNombre);
        txtPass = (TextView) root.findViewById(R.id.txtPassword);
        txtEmail = (TextView) root.findViewById(R.id.txtEmail);
        txtTel = (TextView) root.findViewById(R.id.txtTel);

        ContentResolver cr=getContext().getContentResolver();
        ContentValues cv=new ContentValues();
        btnAceptar.setOnClickListener(l->{
            cv.put(MiProveedorContenidoContract.Usuarios.NOMBRE,txtNombre.getText().toString());
            cv.put(MiProveedorContenidoContract.Usuarios.PASS,txtPass.getText().toString());
            cv.put(MiProveedorContenidoContract.Usuarios.EMAIL,txtEmail.getText().toString());
            cv.put(MiProveedorContenidoContract.Usuarios.TELEFONO,txtTel.getText().toString());
            Uri resultado=cr.insert(MiProveedorContenidoContract.Usuarios.CONTENT_URI,cv);


        });
        return root;
    }
}