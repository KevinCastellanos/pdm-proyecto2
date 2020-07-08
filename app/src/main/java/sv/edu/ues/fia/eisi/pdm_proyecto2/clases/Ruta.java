package sv.edu.ues.fia.eisi.pdm_proyecto2.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Ruta {
    @SerializedName("IDRUTAS")
    @Expose
    private Integer IDRUTAS;

    @SerializedName("NOMBRE")
    @Expose
    private String NOMBRE;

    @SerializedName("DESCRIPCION")
    @Expose
    private String DESCRIPCION;

    @SerializedName("CANTIDAD")
    @Expose
    private Integer CANTIDAD;

    public Ruta(){}

    public Ruta(Integer IDRUTAS, String NOMBRE, String DESCRIPCION, Integer CANTIDAD) {
        this.IDRUTAS = IDRUTAS;
        this.NOMBRE = NOMBRE;
        this.DESCRIPCION = DESCRIPCION;
        this.CANTIDAD = CANTIDAD;
    }

    public Integer getIDRUTAS() {
        return IDRUTAS;
    }

    public void setIDRUTAS(Integer IDRUTAS) {
        this.IDRUTAS = IDRUTAS;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public Integer getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(Integer CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }
}
