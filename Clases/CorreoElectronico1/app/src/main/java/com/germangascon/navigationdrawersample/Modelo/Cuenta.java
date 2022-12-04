package com.germangascon.navigationdrawersample.Modelo;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Cuenta implements Serializable {
    @SerializedName("id")
    private final int id;
    @SerializedName("name")
    private final String nombre;
    @SerializedName("surname")
    private final String apellido;
    @SerializedName("email")
    private final String email;
    @SerializedName("contacts")
    private  List<Contacto> contactoList;
    @SerializedName("mails")
    private  List<Email> correos;

    public Cuenta(int id, String nombre, String apellido, String email, List<Contacto>contactoList, List<Email> emails) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contactoList = contactoList;
        this.correos = emails;
    }

    public int getId() {
        return id;
    }

    public List<Email> getCorreos() {
        return correos;
    }

    public List<Contacto> getContactoList() {
        return contactoList;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", contactoList=" + contactoList +
                ", emails=" + correos +
                '}';
    }
}
