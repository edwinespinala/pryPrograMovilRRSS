package com.example.rrssapp.ui.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rrssapp.Entities.Cargo;
import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.R;
import com.example.rrssapp.databinding.ActivityNewCargoBinding;
import com.example.rrssapp.databinding.ActivityNewEmpleadoBinding;

import java.io.Serializable;

public class NewCargoActivity extends AppCompatActivity {

  private ActivityNewCargoBinding binding;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityNewCargoBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());


    Intent getIntent = getIntent();
    if(getIntent.hasExtra("action")){
      if (getIntent.getStringExtra("action").equals("update")){
        Cargo car = (Cargo) getIntent.getSerializableExtra("Cargo");
        binding.tilNombreCargo.getEditText().setText(car.getNombreCargo());
        binding.tilNombreCargo.getEditText().setEnabled(true);
        binding.tilDescripcionCargo.getEditText().setText(car.getDescripcionCargo());
        binding.spnDepartamentoCargo.setSelection(car.getDepartamento());
        binding.tilSalarioCargo.getEditText().setText(car.getSueldoCargo()+"");
      }
    }

    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.departamento_list, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    binding.spnDepartamentoCargo.setAdapter(adapter);



    binding.btnGuardarCargo.setOnClickListener(v -> {
      int idDepartamento = binding.spnDepartamentoCargo.getSelectedItemPosition();
      double salario = Double.parseDouble(binding.tilSalarioCargo.getEditText().getText().toString());
      Intent replyIntent = new Intent();

      Cargo cargo = new Cargo(binding.tilNombreCargo.getEditText().getText().toString(),binding.tilDescripcionCargo.getEditText().getText().toString(),salario,idDepartamento);
     //Cargo cargo = new Cargo("dsa","dasdds",2131,1);

      replyIntent.putExtra("cargo", cargo);
      setResult(RESULT_OK, replyIntent);
      finish();
    });
  }



}
