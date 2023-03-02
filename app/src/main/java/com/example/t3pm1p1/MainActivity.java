package com.example.t3pm1p1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t3pm1p1.configuracion.SQLiteConexion;
import com.example.t3pm1p1.transacciones.Transacciones;

public class MainActivity extends AppCompatActivity {

    TextView tv_nombres, tv_apellidos, tv_edad, tv_correo, tv_direccion;
    EditText pt_nombres, pt_apellidos, pt_edad, pt_correo, pt_direccion;
    Button btn_salvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_nombres = (TextView) findViewById(R.id.tv_nombres);
        tv_apellidos = (TextView) findViewById(R.id.tv_apellidos);
        tv_edad = (TextView) findViewById(R.id.tv_edad);
        tv_correo = (TextView) findViewById(R.id.tv_correo);
        tv_direccion = (TextView) findViewById(R.id.tv_direccion);

        pt_nombres = (EditText) findViewById(R.id.pt_nombres);
        pt_apellidos = (EditText) findViewById(R.id.pt_apellidos);
        pt_edad = (EditText) findViewById(R.id.pt_edad);
        pt_correo = (EditText) findViewById(R.id.pt_correo);
        pt_direccion = (EditText) findViewById(R.id.pt_direccion);

        btn_salvar = (Button) findViewById(R.id.btn_salvar);

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalvarContacto();
                Intent intent = new Intent(getApplicationContext(),ActivityListView.class);
                startActivity(intent);
            }
        });

    }

    private void SalvarContacto(){
        if(validationDetails()){
            try {
                SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
                SQLiteDatabase db = conexion.getWritableDatabase();

                ContentValues valores = new ContentValues();
                valores.put("nombres", pt_nombres.getText().toString());
                valores.put("apellidos", pt_apellidos.getText().toString());
                valores.put("edad", pt_edad.getText().toString());
                valores.put("correo", pt_correo.getText().toString());
                valores.put("direccion", pt_direccion.getText().toString());


                Long Resultado = db.insert(Transacciones.tablapersonas, "id", valores);
                Toast.makeText(this, Resultado.toString(), Toast.LENGTH_SHORT).show();

                ClearScreen();
            }catch (Exception ex)
            {
                Toast.makeText(this,"No se pudo ingresar el contacto",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Campos Vacios",Toast.LENGTH_SHORT).show();
        }
    }

    private void ClearScreen() {
        pt_nombres.setText(Transacciones.Empty);
        pt_apellidos.setText(Transacciones.Empty);
        pt_edad.setText(Transacciones.Empty);
        pt_correo.setText(Transacciones.Empty);
        pt_direccion.setText(Transacciones.Empty);
    }

    private boolean validationDetails() {

        if (pt_nombres.getText().toString().isEmpty()) {
            pt_nombres.setError("Ingrese sus nombres");
            return false;
        }
        if (pt_apellidos.getText().toString().isEmpty()) {
            pt_apellidos.setError("Ingrese sus apellidos ");
            return false;
        }
        if (pt_edad.getText().toString().isEmpty()) {
            pt_edad.setError("Ingrese su edad");
            return false;
        }
        if (pt_correo.getText().toString().isEmpty()) {
            pt_correo.setError("Ingrese su correo");
            return false;
        }
        if (pt_direccion.getText().toString().isEmpty()) {
            pt_direccion.setError("Ingrese su direccion");
            return false;
        }
        return true;
    }
}