package com.example.rrssapp.ui.cargo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rrssapp.Entities.Cargo;
import com.example.rrssapp.OnItemClickListener;
import com.example.rrssapp.databinding.CargoItemBinding;

import java.util.List;

public class CargoAdapter extends RecyclerView.Adapter<CargoAdapter.ViewHolder> {
    private List<Cargo> dataset;
    private OnItemClickListener<Cargo> manejadorEventoClick;

    public CargoAdapter(List<Cargo> dataset, OnItemClickListener<Cargo> manejadorEventoClick) {
        this.dataset = dataset;
        this.manejadorEventoClick = manejadorEventoClick;
    }

    @NonNull
    @Override
    public CargoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CargoItemBinding binding = CargoItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CargoAdapter.ViewHolder holder, int position) {
        Cargo cargoMostrar = dataset.get(position);
        int depa = cargoMostrar.getDepartamento();
        String departamento;
        switch(depa){
            case 0:
                departamento = "Administracion";
                break;
            case 1:
                departamento = "Gerencia";
                break;
            case 2:
                departamento = "IT";
                break;
            case 3:
                departamento = "Caja";
                break;
            case 4:
                departamento = "Bodega";
                break;
            case 5:
                departamento = "Limpieza";
                break;
            case 6:
                departamento = "Contabilidad";
                break;
            case 7:
                departamento = "Venta";
                break;
            default:
                departamento = "";
        }




        holder.binding.tvCargo.setText(cargoMostrar.getNombreCargo());
        //holder.binding.tvDepartamento.setText(cargoMostrar.getDepartamento());
        holder.binding.tvDepartamento.setText(departamento);
        holder.setOnClickListener(cargoMostrar,manejadorEventoClick);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public void setItems(List<Cargo> cargos){
        this.dataset = cargos;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        CargoItemBinding binding;
        public ViewHolder(@NonNull CargoItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void setOnClickListener(Cargo cargoMostrar, OnItemClickListener<Cargo> listener) {
          //this.binding.imgEliminar.setOnClickListener(v -> listener.onItemClick(cargoMostrar,"mostrar"));
            binding.imgEliminar.setOnClickListener(v ->{
                manejadorEventoClick.onItemClick(cargoMostrar,"delete");
            });


            binding.cardCargo.setOnClickListener(v ->{
                manejadorEventoClick.onItemClick(cargoMostrar,"card");
            });


        }
    }
}
