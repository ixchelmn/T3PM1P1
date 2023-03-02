package com.example.t3pm1p1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.t3pm1p1.configuracion.SQLiteConexion;
import com.example.t3pm1p1.transacciones.Personas;
import com.example.t3pm1p1.transacciones.Transacciones;

import java.util.ArrayList;

public class ActivityListView extends AppCompatActivity {

    SQLiteConexion conexion;

    ListView listview;

    ArrayList<Personas> listapersonas;

    ArrayList<String> Arreglopersonas;

    Button btn_actualizar, btn_eliminar;

    Personas currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null,1);
        listview = (ListView) findViewById(R.id.listview);
        btn_actualizar = (Button) findViewById(R.id.btn_actualizar);
        btn_eliminar = (Button) findViewById(R.id.btn_eliminar);

        ObtenerListaPersonas();

        ArrayAdapter adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arreglopersonas);
        listview.setAdapter(adp);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentuser = listapersonas.get(i);
            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( currentuser == null ) return;
                Intent intent = new Intent(getApplicationContext(),ActivityActualizar.class);
                intent.putExtra("persona_actualizar",currentuser);
                startActivity(intent);
            }
        });
        btn_eliminar.setOnClickListener(new View.OnClickListener() {
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
        FillList();

    }

    private void FillList(){
        Arreglopersonas = new ArrayList<String>();
        for(int i = 0; i < listapersonas.size(); i++){
            Arreglopersonas.add(listapersonas.get(i).getId() + " | "+
                    listapersonas.get(i).getNombres() + " | " +
                    listapersonas.get(i).getApellidos() +
                    listapersonas.get(i).getEdad() + " | " +
                    listapersonas.get(i).getCorreo() + " | " +
                    listapersonas.get(i).getDireccion());

        }
    }

    private void DeletePersona() {
        try {
            SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
            SQLiteDatabase db = conexion.getWritableDatabase();
            String[] args = new String[]{Integer.toString(currentuser.getId())};
            db.delete(Transacciones.tablapersonas, "id=?", args);
            Intent intent = new Intent(getApplicationContext(), ActivityListView.class);
            startActivity(intent);

        } catch (Exception ex) {
            Toast.makeText(this,"Contacto no pudo ser borrado", Toast.LENGTH_LONG).show();
        }
    }

}