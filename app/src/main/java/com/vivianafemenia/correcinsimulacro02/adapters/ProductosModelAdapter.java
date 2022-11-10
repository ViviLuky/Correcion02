package com.vivianafemenia.correcinsimulacro02.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vivianafemenia.correcinsimulacro02.MainActivity;
import com.vivianafemenia.correcinsimulacro02.R;
import com.vivianafemenia.correcinsimulacro02.modelos.ProductoModel;

import java.text.NumberFormat;
import java.util.List;

public class ProductosModelAdapter extends RecyclerView.Adapter<ProductosModelAdapter.ProductoVH> {


    private List<ProductoModel> objets;
    private  int resourse;
    private Context context;

    private NumberFormat nf;

    private MainActivity main;

    public ProductosModelAdapter(List<ProductoModel> objets, int resourse, Context context) {
        this.objets = objets;
        this.resourse = resourse;
        this.context = context;
        nf=NumberFormat.getCurrencyInstance();
        main=(MainActivity) context;
    }

    @NonNull
    @Override
    public ProductoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productoView = LayoutInflater.from(context).inflate(resourse,null);
        productoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        return new ProductoVH(productoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVH holder, int position) {

        ProductoModel p = objets.get(position);
        holder.lblNombre.setText(p.getNombre());
        holder.lblCantidad.setText(String.valueOf(p.getCantidad()));
        holder.lblPrecio.setText(nf.format(p.getPrecio()));

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete(p, holder.getAdapterPosition()).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProducto(p,holder.getAdapterPosition()).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return objets.size();
    }
    private AlertDialog editProducto(ProductoModel p,int position){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(p.getNombre());

        View productoView=LayoutInflater.from(context).inflate(R.layout.activity_add_producto,null);

        EditText txtNombre =productoView.findViewById(R.id.txtNombreProductoAdd);
        txtNombre.setVisibility(View.GONE);

        Button btn=productoView.findViewById(R.id.btnAgregarProductoAdd);
        btn.setVisibility(View.GONE);

        EditText txtCantidad = productoView.findViewById(R.id.txtCantidadProductoAdd);
        txtCantidad.setText(String.valueOf(p.getCantidad()));

        EditText txtPrecio = productoView.findViewById(R.id.txtPreciProductoAdd);
        txtPrecio.setText(String.valueOf(p.getPrecio()));

        builder.setView(productoView);

        builder.setNegativeButton("CANCELAR",null);
        builder.setNegativeButton("MODIFICAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!txtCantidad.getText().toString().isEmpty() && !txtPrecio.getText().toString().isEmpty()){

                    p.setCantidad(Integer.parseInt(txtCantidad.getText().toString()));
                    p.setPrecio(Float.parseFloat(txtPrecio.getText().toString()));
                    notifyItemChanged(position);
                    main.calculaValores();
                }

            }
        });
        return builder.create();
    }

    private AlertDialog confirmDelete(ProductoModel p,int position){
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Estas Seguro!!??");
        builder.setCancelable(false);

        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                objets.remove(p);
                notifyItemRemoved(position);
                main.calculaValores();
            }
        });
        return builder.create();
    }


    public class ProductoVH  extends RecyclerView.ViewHolder {

        TextView lblNombre,lblCantidad,lblPrecio;
        ImageButton btnEliminar;


        public ProductoVH(@NonNull View itemView) {
            super(itemView);
            lblNombre=itemView.findViewById(R.id.lblNombreProductoCard);
            lblCantidad=itemView.findViewById(R.id.lblCantidadProductoCard);
            lblPrecio=itemView.findViewById(R.id.lblPrecioProductoCard);
            btnEliminar=itemView.findViewById(R.id.btnEliminarProductoCard);
        }
    }

}
