package com.apilikes.ApiRestLikes.dataContract.requests;


public class LikeRequest {
    private String cancion;
    private String fecha;
    private String id_usuario;

    // Getters and setters
    public String getCancion() {
        return cancion;
    }

    public void setCancion(String cancion) {
        this.cancion = cancion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
