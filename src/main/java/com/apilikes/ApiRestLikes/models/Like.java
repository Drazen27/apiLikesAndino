package com.apilikes.ApiRestLikes.models;
// import com.google.cloud.Timestamp;

public class Like extends FirebaseDocument{
    
    private String cancion;
    // private Timestamp fecha;
    private String id_usuario;
    
    // Getters and setters


    public String getCancion() {
        return cancion;
    }

    public void setCancion(String cancion) {
        this.cancion = cancion;
    }

    // public Timestamp getFecha() {
    //     return fecha;
    // }

    // public void setFecha(Timestamp fecha) {
    //     this.fecha = fecha;
    // }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

   
}