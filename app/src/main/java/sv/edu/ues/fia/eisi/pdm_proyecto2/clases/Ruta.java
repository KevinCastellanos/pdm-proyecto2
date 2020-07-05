package sv.edu.ues.fia.eisi.pdm_proyecto2.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



import java.io.StringReader;

public class Ruta {
    @SerializedName("NOMBRE")
    @Expose
    private String NOMBRE;

    @SerializedName("DESCRIPCION")
    @Expose
    private String DESCRIPCION;

    public Ruta() {
    }

    public Ruta(String NOMBRE, String DESCRIPCION) {
        this.NOMBRE = NOMBRE;
        this.DESCRIPCION = DESCRIPCION;
    }

    public String getNOMBRE() { return NOMBRE; }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }
}
