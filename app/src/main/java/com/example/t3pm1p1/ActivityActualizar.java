package com.example.t3pm1p1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t3pm1p1.configuracion.SQLiteConexion;
import com.example.t3pm1p1.transacciones.Personas;
import com.example.t3pm1p1.transacciones.Transacciones;

import java.util.ArrayList;

public class ActivityActualizar extends AppCompatActivity {

    SQLiteConexion conexion;
    TextView tv_actualizar_nombres, tv_actualizar_apellidos, tv_actualizar_edad, tv_actualizar_correo, tv_actualizar_direccion;
    EditText pt_actualizar_nombres, pt_actualizar_apellidos, pt_actualizar_edad, pt_actualizar_correo, pt_actualizar_direccion;
    Button btn_update, btn_delete;
    Personas persona;

    ArrayList<Personas> listapersonas;

    ArrayList<String> Arreglopersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);
        pt_actualizar_nombres = (EditText) findViewById(R.id.pt_actualizar_nombres);
        pt_actualizar_apellidos = (EditText) findViewById(R.id.pt_actualizar_apellidos);
        pt_actualizar_edad = (EditText) findViewById(R.id.pt_actualizar_edad);
        pt_actualizar_correo = (EditText) findViewById(R.id.pt_actualizar_correo);
        pt_actualizar_direccion = (EditText) findViewById(R.id.pt_actualizar_direccion);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null,1);

        ObtenerListaPersonas();

        Intent intent = getIntent();
        persona = (Personas) intent.getSerializableExtra("persona_actualizar");

        String x = persona.getNombres();

        pt_actualizar_nombres.setText(persona.getNombres());
        pt_actualizar_apellidos.setText(persona.getApellidos());
        pt_actualizar_edad.setText(Integer.toString(persona.getEdad()));
        pt_actualizar_correo.setText(persona.getCorreo());
        pt_actualizar_direccion.setText(persona.getDireccion());


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {ActualizarPersona();}
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {DeletePersona();}
        });

    }

    private void ObtenerListaPersonas(){

        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas persona = null;
        listapersonas = new ArrayList<Personas>();

        //Cursor
        Cursor cursor = db.rawQuery("SELECT * FROM personas", null);

        while(cursor.moveToNext()){
            persona = new Personas();
            persona.setId(cursor.getInt(0));
            persona.setNombres(cursor.getString(1));
            persona.setApellidos(cursor.getString(2));
            persona.setEdad(cursor.getInt(3));
            persona.setCorreo(cursor.getString(4));
            persona.setDireccion(cursor.getString(5));

            listapersonas.add(persona);

        }
        cursor.close();
    }
    private void ActualizarPersona(){
        try {
            SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
            SQLiteDatabase db = conexion.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put("nombres", pt_actualizar_nombres.getText().toString());
            valores.put("apellidos", pt_actualizar_apellidos.getText().toString());
            valores.put("edad", pt_actualizar_edad.getText().toString());
            valores.put("correo", pt_actualizar_correo.getText().toString());
            valores.put("direccion", pt_actualizar_direccion.getText().toString());

            String[] args = new String[]{Integer.toString(persona.getId())};
            db.update(Transacciones.tablapersonas, valores, "id=?", args);
            Intent intent = new Intent(getApplicationContext(),ActivityListView.class);
            startActivity(intent);


        }catch (Exception ex)
        {
            Toast.makeText(this,"No se pudo ingresar el contacto",Toast.LENGTH_LONG).show();
        }
    }

   private void DeletePersona() {
       try {
           SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
           SQLiteDatabase db = conexion.getWritableDatabase();
           String[] args = new String[]{Integer.toString(persona.getId())};
           db.delete(Transacciones.tablapersonas, "id=?", args);
           Intent intent = new Intent(getApplicationContext(), ActivityListView.class);
           startActivity(intent);

       } catch (Exception ex) {
            Toast.makeText(this,"Contacto no pudo ser borrado", Toast.LENGTH_LONG).show();
       }
   }
}