package com.example.rrssapp.ui.empleado;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.OnItemClickListener;
import com.example.rrssapp.R;
import com.example.rrssapp.databinding.FragmentEmpleadoBinding;

import java.util.ArrayList;
import java.util.List;

public class EmpleadoFragment extends Fragment implements OnItemClickListener<Empleado> {

    private FragmentEmpleadoBinding binding;
    private EmpleadoAdapter empleadoAdapter;
    private ActivityResultLauncher<Intent> launcher;
    private EmpleadoViewModel empleadoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        empleadoViewModel =
                new ViewModelProvider(this).get(EmpleadoViewModel.class);

        binding = FragmentEmpleadoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        empleadoAdapter = new EmpleadoAdapter(new ArrayList<>(),this);

        empleadoViewModel.getDataset().observe(getViewLifecycleOwner(), empleados -> {
            empleadoAdapter.setItems(empleados);
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.ver_estados_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnVerEstados.setAdapter(adapter);

        binding.spnVerEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acciones a realizar cuando no se selecciona ningún elemento del Spinner
            }
        });

        setupRecyrcleView();

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Empleado empleado = (Empleado) data.getSerializableExtra("empleado");
                        empleadoViewModel.insert(empleado);
                        setItems();
                    } else {
                        Toast.makeText(this.getContext(),"Operación cancelada",Toast.LENGTH_LONG).show();
                    }
                }
        );

        binding.fabEmpleado.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), NewEmpleadoActivity.class);
            intent.putExtra("action","new");
            launcher.launch(intent);
        });

        return root;
    }
    private void setupRecyrcleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.rvEmpleados.setLayoutManager(linearLayoutManager);
        binding.rvEmpleados.setAdapter(empleadoAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setItems(){
        int pos = binding.spnVerEstados.getSelectedItemPosition();
        if (pos == 0) {
            empleadoViewModel.getDataset().observe(getViewLifecycleOwner(), empleados -> {
                empleadoAdapter.setItems(empleados);
            });
        } else if (pos == 1){
            empleadoViewModel.getDatasetEstado("Activo").observe(getViewLifecycleOwner(), empleados -> {
                empleadoAdapter.setItems(empleados);
            });
        }else{
            empleadoViewModel.getDatasetEstado("Inactivo").observe(getViewLifecycleOwner(), empleados -> {
                empleadoAdapter.setItems(empleados);
            });
        }
    }

    @Override
    public void onItemClick(Empleado data,String click) {
        if (click.equals("delete")){
            modalDelete(data);
        }else {
            modalCard(data);
        }
    }

    public void modalDelete(Empleado empleado){
        String accion = "DESHABILITAR";
        if(empleado.getEstado().equals("Inactivo"))accion = "HABILITAR";
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(empleado.getNombre()+"\n"+empleado.getDni());
        builder.setMessage("Si desea "+accion.toLowerCase()+" este empleado haz click en \""+accion+"\"/nSi desea eliminarlo completamente has click en \"ELIMINAR\"");
        builder.setPositiveButton(accion, (dialog, which) -> {
            if(empleado.getEstado().equals("Inactivo")){
                empleado.setEstado("Activo");
            }else{
                empleado.setEstado("Inactivo");
            }
            empleadoViewModel.update(empleado);
            setItems();

        });
        builder.setNegativeButton("ELIMINAR", (dialog, which) -> {
            modalConfimarEliminar(empleado);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void modalConfimarEliminar(Empleado empleado){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(empleado.getNombre()+"\n"+empleado.getDni());
        builder.setMessage("¿Está seguro que desea eliminar este empleado?\nSi lo elimina no volverá a recuperar información acerca de este empleado");
        builder.setPositiveButton("Si, deseo eliminar", (dialog, which) -> {
            empleadoViewModel.delete(empleado);
            setItems();
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> {

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void modalCard(Empleado empleado){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(empleado.getNombre()+"\n"+empleado.getDni());
        builder.setMessage("Departamento:"+empleado.getDepartamento()+"\nCargo:"+
                empleado.getCargo()+"\nSexo: "+empleado.getSexo()+"\nSalario: "+empleado.getSalario()+"\nEstado: "+empleado.getEstado());
        builder.setPositiveButton("Modificar", (dialog, which) -> {
            Intent intent = new Intent(requireContext(), NewEmpleadoActivity.class);
            intent.putExtra("action","update");
            intent.putExtra("empleado",empleado);
            launcher.launch(intent);
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> {

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}