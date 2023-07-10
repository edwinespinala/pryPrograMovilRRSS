package com.example.rrssapp.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "empleado_table")
public class Empleado implements Serializable{
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "empleado_dni")
    @NonNull
    private String dni;

    @NonNull
    @ColumnInfo(name = "empleado_nombre")
    private String nombre;

    @NonNull
    @ColumnInfo(name = "departamento_id")
    private int departamento;

    @NonNull
    @ColumnInfo(name = "cargo_id")
    private int cargo;

    @NonNull
    @ColumnInfo(name = "empleado_sexo")
    private String sexo;

    @NonNull
    @ColumnInfo(name = "empleado_salario")
    private double salario;



    @NonNull
    @ColumnInfo(name = "empleado_estado")
    private String estado;
    public Empleado(@NonNull String dni, @NonNull String nombre,@NonNull int departamento,@NonNull int cargo, @NonNull String sexo,@NonNull double salario,@NonNull String estado) {
        this.dni = dni;
        this.nombre = nombre;
        this.departamento = departamento;
        this.cargo = cargo;
        this.sexo = sexo;
        this.salario = salario;
        this.estado = estado;
    }

    @NonNull
    public String getDni() {
        return dni;
    }

    public void setDni(@NonNull String dni) {
        this.dni = dni;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }
    public int getCargo() {
        return cargo;
    }

    @NonNull
    public String getSexo() {
        return sexo;
    }

    public void setSexo(@NonNull String sexo) {
        this.sexo = sexo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @NonNull
    public String getEstado() {
        return estado;
    }

    public void setCargo(@NonNull int cargo) {
        this.cargo = cargo;
    }

    public void setEstado(@NonNull String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", departamento=" + departamento +
                ", cargo=" + cargo +
                ", sexo='" + sexo + '\'' +
                ", salario=" + salario +
                ", estado='" + estado + '\'' +
                '}';
    }
}
