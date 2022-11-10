package com.vivianafemenia.correcinsimulacro02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vivianafemenia.correcinsimulacro02.databinding.ActivityAddProductoBinding;
import com.vivianafemenia.correcinsimulacro02.modelos.ProductoModel;

public class AddProductoActivity extends AppCompatActivity {

    private ActivityAddProductoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddProductoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnAgregarProductoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre=binding.txtNombreProductoAdd.getText().toString();
                String cantidad=binding.txtCantidadProductoAdd.getText().toString();
                String precios=binding.txtPreciProductoAdd.getText().toString();
                if ((!nombre.isEmpty() && !cantidad.isEmpty()&& !precios.isEmpty())){
                    ProductoModel p = new ProductoModel(nombre,Integer.parseInt(cantidad),Float.parseFloat(precios));

                    Bundle bundle=new Bundle();
                    bundle.putSerializable("PROD",p);
                    Intent intent= new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);

                    finish();
                }
            }
        });
    }
}