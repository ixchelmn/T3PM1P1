package com.example.t3pm1p1.transacciones;

public class Transacciones
{
    // Nombre de la base de datos
    public static final String NameDatabase = "T3PM1P1";
    // Tablas de la base de datos
    public static final String tablapersonas = "personas";

    /* Transacciones de la base de datos PM01DB */
    public static final String CreateTBPersonas =
            "CREATE TABLE personas (id INTEGER PRIMARY KEY AUTOINCREMENT, nombres TEXT ," +
                    "apellidos TEXT, edad INTEGER, correo TEXT, direccion TEXT)";

    public static final String DropTablePersonas = "DROP TABLE IF EXISTS personas";

    // Helpers
    public static final String Empty = "";
}
