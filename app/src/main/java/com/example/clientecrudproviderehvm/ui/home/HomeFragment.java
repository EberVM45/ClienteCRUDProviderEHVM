package com.example.clientecrudproviderehvm.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientecrudproviderehvm.R;
import com.example.clientecrudproviderehvm.data.AdapdadorRecyclerCursor;
import com.example.clientecrudproviderehvm.provider.MiProveedorContenidoContract;
import com.example.clientecrudproviderehvm.ui.Modificar;
import com.example.clientecrudproviderehvm.ui.SeleccionarUno;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Cursor cursor;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AdapdadorRecyclerCursor adapdadorRecyclerCursor;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        layoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView = root.findViewById(R.id.recyclerUsuarios);

        recyclerView.setLayoutManager(layoutManager);

        Cursor cursor = getContext().getContentResolver().
                query(MiProveedorContenidoContract.Usuarios.CONTENT_URI,
                        null,null,null,null);

        adapdadorRecyclerCursor=new AdapdadorRecyclerCursor(
                        getContext(),
                        cursor
                );

        adapdadorRecyclerCursor.setOnClickListener(l-> {
            Intent intent= new Intent(getActivity(), SeleccionarUno.class);
            intent.putExtra("id",((TextView)(l.findViewById(android.R.id.text1))).getText());
            startActivity(intent);
        } );
        adapdadorRecyclerCursor.setOnLongClickListener(l-> {
            AlertDialog.Builder cuadroDialogo = new AlertDialog.Builder(getContext());
            cuadroDialogo.setTitle("Opciones");
            //cuadroDialogo.setMessage("Este es un cuadro de texto");
            cuadroDialogo.setItems(new String[]{"Editar", "Eliminar"}, (dialogInterface, i) -> {
                //Toast.makeText(getActivity(), "Opción selecionada " + i, Toast.LENGTH_LONG).show();
                switch (i){
                    case 0:
                        Intent intent= new Intent(getActivity(), Modificar.class);
                        intent.putExtra("id",((TextView)(l.findViewById(android.R.id.text1))).getText());
                        startActivity(intent);
                        break;
                    case 1:
                        AlertDialog.Builder cuadroDialogo2 = new AlertDialog.Builder(getContext());
                        cuadroDialogo2.setTitle("¿Estás seguro de que desea eliminar la nota?");
                        cuadroDialogo2.setItems(new String[]{"Aceptar", "Cancelar"}, (dialogInterface2, j) -> {
                            switch (j){
                                case 0:
                                    Uri uri=Uri.parse(MiProveedorContenidoContract.Usuarios.CONTENT_URI+"/"+((TextView)(l.findViewById(android.R.id.text1))).getText());
                                    //Log.d(TAG,uri.toString());
                                    int result=getContext().getContentResolver().delete(uri,null,null);
                                    if (result==0){
                                        Toast.makeText(getContext(), "No se puede eliminar", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                                        //consultar();
                                        adapdadorRecyclerCursor.notifyDataSetChanged();
                                    }
                                    break;
                                case 1:

                                    break;
                            }
                        });
                        cuadroDialogo2.show();
                        break;
                }
            });
            //cuadroDialogo.setPositiveButton("Ok", (vcd, i) -> {});
            cuadroDialogo.show();
            return false;
        } );
        recyclerView.setAdapter(adapdadorRecyclerCursor);

        return root;
    }
    public void consultar(){
        cursor = getContext().getContentResolver().query(MiProveedorContenidoContract.Usuarios.CONTENT_URI,null,null,null,null);
    }
}