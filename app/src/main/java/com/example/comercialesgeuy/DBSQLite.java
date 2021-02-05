package com.example.comercialesgeuy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBSQLite extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "GEUYDB";

    public DBSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_COMERCIALES);
        db.execSQL(CREAR_CITAS);
        db.execSQL(CREAR_PARTNERS);
        db.execSQL(CREAR_PRODUCTOS);
        db.execSQL(CREAR_ALBARANES);
        db.execSQL(CREAR_LINEAS);

        db.execSQL(INSERT_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMERCIALES);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITAS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTNERS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALBARANES);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINEAS);

        //onCreate(db);
    }

    // TABLA COMERCIALES

    public static final String TABLE_COMERCIALES = "COMERCIALES";

    public static final String COMERCIALES_KEY_ID = "_id";
    public static final String COMERCIALES_KEY_USR = "USR";
    public static final String COMERCIALES_KEY_PWD = "PWD";
    public static final String COMERCIALES_KEY_NOMBRE = "NOMBRE";
    public static final String COMERCIALES_KEY_APELLIDOS = "APELLIDOS";
    public static final String COMERCIALES_KEY_EMPRESA = "EMPRESA";
    public static final String COMERCIALES_KEY_EMAIL = "EMAIL";
    public static final String COMERCIALES_KEY_TLFN = "TELEFONO";
    public static final String COMERCIALES_KEY_DELEG = "DELEGACION";

    private static final String CREAR_COMERCIALES =
            "CREATE TABLE " + TABLE_COMERCIALES + "(" +
                    COMERCIALES_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COMERCIALES_KEY_USR + " TEXT NOT NULL, " +
                    COMERCIALES_KEY_PWD + " TEXT NOT NULL," +
                    COMERCIALES_KEY_NOMBRE + " TEXT," +
                    COMERCIALES_KEY_APELLIDOS + " TEXT," +
                    COMERCIALES_KEY_EMPRESA + " TEXT," +
                    COMERCIALES_KEY_EMAIL + " TEXT," +
                    COMERCIALES_KEY_TLFN + " TEXT," +
                    COMERCIALES_KEY_DELEG + " TEXT" + ")";

    private static final String INSERT_USUARIOS =
            "INSERT INTO " + TABLE_COMERCIALES + "(" + COMERCIALES_KEY_USR + ", " + COMERCIALES_KEY_PWD + "," + COMERCIALES_KEY_NOMBRE + "," + COMERCIALES_KEY_EMPRESA +")" +
                    " VALUES " + "('ANER', 'ONYX', 'ANER ZARAUTZ', 'ANER CORP'), " + "('Javi', 'Seara123', 'Javier' , 'Cebanc-CDEA')" ;

    // TABLA CITAS

    public static final String TABLE_CITAS = "CITAS";

    public static final String CITAS_KEY_ID = "_id";
    public static final String CITAS_KEY_FECHA = "FECHA";
    public static final String CITAS_KEY_HORA = "HORA";
    public static final String CITAS_KEY_CABECERA = "CABECERA";
    public static final String CITAS_KEY_TEXTO = "TEXTO";

    //CREATE TABLE CITAS (_id INTEGER PRIM KEY AUTOINCR, FECHA TEXT, ...
    private static final String CREAR_CITAS =
            "CREATE TABLE " + TABLE_CITAS + "(" +
                    CITAS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CITAS_KEY_FECHA + " TEXT, " +
                    CITAS_KEY_HORA + " TEXT, " +
                    CITAS_KEY_CABECERA + " TEXT," +
                    CITAS_KEY_TEXTO + " TEXT" + ")";

    // TABLA PARTNERS

    public static final String TABLE_PARTNERS = "PARTNERS";

    public static final String PARTNERS_KEY_ID = "_id";
    public static final String PARTNERS_KEY_NOMBRE = "NOMBRE";
    public static final String PARTNERS_KEY_APELLIDOS = "APELLIDOS";
    public static final String PARTNERS_KEY_POBLACION = "POBLACION";
    public static final String PARTNERS_KEY_CIF = "CIF";
    public static final String PARTNERS_KEY_EMAIL = "EMAIL";
    public static final String PARTNERS_KEY_TLFN = "TELEFONO";
    public static final String PARTNERS_KEY_FK_COMERC = COMERCIALES_KEY_ID;
    //public static final String PARTNERS_KEY_FK_COMERC = "FK_COMERCIALES1";

    private static final String CREAR_PARTNERS =
            "CREATE TABLE " + TABLE_PARTNERS + "(" +
                    PARTNERS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PARTNERS_KEY_NOMBRE + " TEXT," +
                    PARTNERS_KEY_APELLIDOS + " TEXT," +
                    PARTNERS_KEY_POBLACION + " TEXT," +
                    PARTNERS_KEY_CIF + " TEXT," +
                    PARTNERS_KEY_EMAIL + " TEXT," +
                    PARTNERS_KEY_TLFN + " TEXT," +
                    PARTNERS_KEY_FK_COMERC + " INTEGER NOT NULL," +
                    "FOREIGN KEY (" + PARTNERS_KEY_FK_COMERC + ") REFERENCES " + TABLE_COMERCIALES + "(" + PARTNERS_KEY_FK_COMERC + ") )";
                    //"CONSTRAINT " + PARTNERS_KEY_FK_COMERC + " FOREIGN KEY (" + COMERCIALES_KEY_ID + ") REFERENCES " + TABLE_COMERCIALES + "(" + COMERCIALES_KEY_ID + ") )";


    // TABLA PRODUCTOS

    public static final String TABLE_PRODUCTOS = "PRODUCTOS";

    public static final String PRODUCTOS_KEY_ID = "_id";
    public static final String PRODUCTOS_KEY_CODIGO = "CODIGO";
    public static final String PRODUCTOS_KEY_DESCRIPCION = "DESCRIPCION";
    public static final String PRODUCTOS_KEY_PRVENT = "PRVENT";
    public static final String PRODUCTOS_KEY_EXISTENCIAS = "EXISTENCIAS";
    public static final String PRODUCTOS_KEY_IMG = "IMG";

    private static final String CREAR_PRODUCTOS =
            "CREATE TABLE " + TABLE_PRODUCTOS + "(" +
                    PRODUCTOS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PRODUCTOS_KEY_CODIGO + " TEXT, " +
                    PRODUCTOS_KEY_DESCRIPCION + " TEXT, " +
                    PRODUCTOS_KEY_PRVENT + " TEXT," +
                    PRODUCTOS_KEY_EXISTENCIAS + " TEXT," +
                    PRODUCTOS_KEY_IMG + " TEXT" + ")";

    // TABLA ALBARANES

    public static final String TABLE_ALBARANES = "ALBARANES";

    public static final String ALBARANES_KEY_ID = "_id";
    public static final String ALBARANES_KEY_FK_COMERC = COMERCIALES_KEY_ID;
    public static final String ALBARANES_KEY_FK_PARTNER = PARTNERS_KEY_ID;
    public static final String ALBARANES_KEY_FECHAALBARAN = "FECHAALBARAN";
    public static final String ALBARANES_KEY_FECHAENVIO = "FECHAENVIO";
    public static final String ALBARANES_KEY_FECHAPAGO = "FECHAPAGO";

    private static final String CREAR_ALBARANES =
            "CREATE TABLE " + TABLE_ALBARANES + "(" +
                    ALBARANES_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ALBARANES_KEY_FK_COMERC + " INTEGER, " +
                    ALBARANES_KEY_FK_PARTNER + " INTEGER, " +
                    ALBARANES_KEY_FECHAALBARAN + " TEXT," +
                    ALBARANES_KEY_FECHAENVIO + " TEXT," +
                    ALBARANES_KEY_FECHAPAGO + " TEXT," +
                    "FOREIGN KEY (" + ALBARANES_KEY_FK_COMERC + ") REFERENCES " + TABLE_COMERCIALES + "(" + ALBARANES_KEY_FK_COMERC + ")," +
                    "FOREIGN KEY (" + ALBARANES_KEY_FK_PARTNER + ") REFERENCES " + TABLE_PARTNERS + "(" + ALBARANES_KEY_FK_PARTNER + ") )";

    // TABLA LINEAS

    public static final String TABLE_LINEAS = "LINEAS";

    public static final String LINEAS_KEY_ID = "_id";
    public static final String LINEAS_KEY_FK_ALBARAN = ALBARANES_KEY_ID;
    public static final String LINEAS_KEY_FK_PRODUCTO = PRODUCTOS_KEY_ID;
    public static final String LINEAS_KEY_CANTIDAD = "CANTIDAD";
    public static final String LINEAS_KEY_PRECIOLINEA = "PRECIOLINEA";

    private static final String CREAR_LINEAS =
            "CREATE TABLE " + TABLE_LINEAS + "(" +
                    LINEAS_KEY_ID + " INTEGER, " +
                    LINEAS_KEY_FK_ALBARAN + " INTEGER, " +
                    LINEAS_KEY_FK_PRODUCTO + " INTEGER, " +
                    LINEAS_KEY_CANTIDAD + " INTEGER," +
                    LINEAS_KEY_PRECIOLINEA + " REAL," +
                    "PRIMARY KEY (" + LINEAS_KEY_ID + "," + LINEAS_KEY_FK_ALBARAN + "), " +
                    "FOREIGN KEY (" + LINEAS_KEY_FK_ALBARAN + ") REFERENCES " + TABLE_ALBARANES + "(" + LINEAS_KEY_FK_ALBARAN + ")," +
                    "FOREIGN KEY (" + LINEAS_KEY_FK_PRODUCTO + ") REFERENCES " + TABLE_PRODUCTOS + "(" + LINEAS_KEY_FK_PRODUCTO + ") )";
}
