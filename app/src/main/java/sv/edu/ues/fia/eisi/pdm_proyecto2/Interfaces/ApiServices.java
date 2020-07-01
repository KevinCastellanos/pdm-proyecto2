package sv.edu.ues.fia.eisi.pdm_proyecto2.Interfaces;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sv.edu.ues.fia.eisi.pdm_proyecto2.Clases.Usuario;

import sv.edu.ues.fia.eisi.pdm_proyecto2.Clases.Ruta;

public interface ApiServices {
    //Registrar Ruta
    @POST("crear-ruta")
    Call<Ruta> agregarRuta(@Query("NOMBRE") String nombre,
                                @Query("DESCRIPCION") String descripcion);
    //Login
    @POST("Login2")
    Call<Usuario> login(@Query("USUARIO") String usuario,
                        @Query("PWD") String pass);
}
