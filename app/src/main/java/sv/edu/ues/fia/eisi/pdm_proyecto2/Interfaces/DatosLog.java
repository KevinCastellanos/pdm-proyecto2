package sv.edu.ues.fia.eisi.pdm_proyecto2.Interfaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class DatosLog {
    public void Registrar(String usuario, String pass, Context context){
        try {
            AdminSQL admin = new AdminSQL(context, "administracionUsuarios", null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


            if (!usuario.isEmpty() && !pass.isEmpty()) {
                ContentValues registro = new ContentValues();

                registro.put("usuario", usuario);
                registro.put("pass", pass);

                Long respuesta = BaseDeDatos.insert("User", null, registro);
                if (respuesta.toString() == "-1"){
                    Log.e("Incorrecto", "No se ha insertado el usuario.");
                }else{
                    Log.e("Correcto", "Se ha insertado el usuario.");
                }

                BaseDeDatos.close();


            } else {
                Log.e("Incorrecto", "No se ha insertado el usuario.");
            }
        }catch (Exception ex){
            Log.e("Correcto", ex.getMessage());
        }
    }

/*
    //Método para consultar un artículo o producto
    public void Buscar(Context context, String usuarioP){
        AdminSQL admin = new AdminSQL(context, "administracionUsuarios", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

        String usuario = usuarioP;
        String usuarioRespuesta;

        if(!usuario.isEmpty()){
            Cursor fila = BaseDeDatabase.rawQuery
                    ("select usuario from User where usuario =" + usuario, null);

            if(fila.moveToFirst()){
                usuarioRespuesta= fila.getString(0);
                BaseDeDatabase.close();
            } else {
                Toast.makeText(context,"No existe el usuario", Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
            }

        } else {
            Toast.makeText(context, "Debes introducir la información correcta", Toast.LENGTH_SHORT).show();
        }
    }
*/

    //Método para eliminar un producto o Artículo
    public int Eliminar(Context context){
        AdminSQL admin = new AdminSQL(context,"administracionUsuarios", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

            int cantidad = BaseDatabase.delete("User", "usuario is not null" , null);
            BaseDatabase.close();

            if(cantidad == 1){
                Toast.makeText(context, "Usuario eliminado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "El Usuario no existe", Toast.LENGTH_SHORT).show();
            }

            return cantidad;
        }

/*
    //Método para modificar un artículo o producto
    public void Modificar(View view){
        AdminSQL admin = new AdminSQL(this, "administracion", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            int cantidad = BaseDatabase.update("articulos", registro, "codigo=" + codigo, null);
            BaseDatabase.close();

            if(cantidad == 1){
                Toast.makeText(this, "Artículo modificado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El artículo no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }*/
}
