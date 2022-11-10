package com.vivianafemenia.correcinsimulacro02;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;


import com.vivianafemenia.correcinsimulacro02.adapters.ProductosModelAdapter;
import com.vivianafemenia.correcinsimulacro02.databinding.ActivityMainBinding;
import com.vivianafemenia.correcinsimulacro02.modelos.ProductoModel;

import java.text.NumberFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;

    // lista
    private ArrayList<ProductoModel> productoModelsList;

    //Laucher
    private ActivityResultLauncher<Intent> launcherAddProducto;

    //Recycler
    private ProductosModelAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    NumberFormat nf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        nf=NumberFormat.getCurrencyInstance();


        setSupportActionBar(binding.toolbar);
        productoModelsList = new ArrayList<>();

        calculaValores();
        adapter = new ProductosModelAdapter(productoModelsList, R.layout.producto_view_holder, this);

        int columnas =getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE ? 2:1;

        layoutManager = new GridLayoutManager(this, columnas);

        binding.contentMain.contenedor.setLayoutManager(layoutManager);
        binding.contentMain.contenedor.setAdapter(adapter);

        inicializaLauncher();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcherAddProducto.launch(new Intent(MainActivity.this, AddProductoActivity.class));
            }
        });
    }

    private void inicializaLauncher() {
        launcherAddProducto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null && result.getData().getExtras() != null) {
                                ProductoModel p = (ProductoModel) result.getData().getExtras().getSerializable("PROD");
                                productoModelsList.add(p);
                                adapter.notifyItemInserted(productoModelsList.size() - 1);
                                //Toast.makeText(MainActivity.this,p.toString(), Toast.LENGTH_SHORT).show();

                                calculaValores();
                            }
                        }
                    }
                });
    }
 public void calculaValores(){
        int cantidad=0;
        float precio=0;

     for (ProductoModel p:productoModelsList) {
         cantidad+= p.getCantidad();
         precio+=p.getPrecio() * p.getCantidad();
     }

     binding.contentMain.lblCantidadTotalMain.setText(String.valueOf(cantidad));
     binding.contentMain.lblPrecioProductoMain.setText(nf.format(precio));
 }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("LISTA",productoModelsList);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ArrayList<ProductoModel> kk= (ArrayList<ProductoModel>) savedInstanceState.getSerializable("LISTA");

        productoModelsList.addAll(kk);
        adapter.notifyItemRangeInserted(0,productoModelsList.size());
        calculaValores();
    }
}