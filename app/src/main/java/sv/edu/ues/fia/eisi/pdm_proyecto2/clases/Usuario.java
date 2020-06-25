package sv.edu.ues.fia.eisi.pdm_proyecto2.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("IDUSUARIO")
    @Expose
    private Integer iDUSUARIO;
    @SerializedName("IDVEHICULO")
    @Expose
    private Object iDVEHICULO;
    @SerializedName("IDCOORDENADAUSUARIO")
    @Expose
    private Integer iDCOORDENADAUSUARIO;
    @SerializedName("NOMBRE")
    @Expose
    private String nOMBRE;
    @SerializedName("USUARIO")
    @Expose
    private String uSUARIO;
    @SerializedName("PWD")
    @Expose
    private String pWD;

    public Integer getIDUSUARIO() {
        return iDUSUARIO;
    }

    public void setIDUSUARIO(Integer iDUSUARIO) {
        this.iDUSUARIO = iDUSUARIO;
    }

    public Object getIDVEHICULO() {
        return iDVEHICULO;
    }

    public void setIDVEHICULO(Object iDVEHICULO) {
        this.iDVEHICULO = iDVEHICULO;
    }

    public Integer getIDCOORDENADAUSUARIO() {
        return iDCOORDENADAUSUARIO;
    }

    public void setIDCOORDENADAUSUARIO(Integer iDCOORDENADAUSUARIO) {
        this.iDCOORDENADAUSUARIO = iDCOORDENADAUSUARIO;
    }

    public String getNOMBRE() {
        return nOMBRE;
    }

    public void setNOMBRE(String nOMBRE) {
        this.nOMBRE = nOMBRE;
    }

    public String getUSUARIO() {
        return uSUARIO;
    }

    public void setUSUARIO(String uSUARIO) {
        this.uSUARIO = uSUARIO;
    }

    public String getPWD() {
        return pWD;
    }

    public void setPWD(String pWD) {
        this.pWD = pWD;
    }

}
