package com.example.comercialesgeuy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.comercialesgeuy.cita.Cita;
import com.example.comercialesgeuy.partners.Partner;
import com.example.comercialesgeuy.pedidos.Producto;
import com.example.comercialesgeuy.pedidos.gestion.XMLParserProducto;

import java.util.ArrayList;
import java.util.List;

public class DBSQLite extends SQLiteOpenHelper {

    private static Context context;
    public static final int DATABASE_VERSION = 25;
    public static final String DATABASE_NAME = "GEUYDB";

    public DBSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_COMERCIALES);
        db.execSQL(CREAR_CITAS);
        db.execSQL(CREAR_PARTNERS);
        db.execSQL(CREAR_PRODUCTOS);
        db.execSQL(CREAR_ALBARANES);
        db.execSQL(CREAR_LINEAS);

        db.execSQL(INSERT_COMERCIALES);
        insertXMLProductos(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    private void insertXMLProductos(SQLiteDatabase db) {
        List<Producto> productoList;
        XMLParserProducto parser = new XMLParserProducto();

        try {
            productoList = parser.parseXML(context.getAssets().open("productos.xml"));

            for (Producto prod : productoList) {
                ContentValues values = new ContentValues();

                values.put(PRODUCTOS_KEY_CODIGO, prod.getCodigo());
                values.put(PRODUCTOS_KEY_DESCRIPCION, prod.getDescripcion());
                values.put(PRODUCTOS_KEY_PRVENT, prod.getPrvent());
                values.put(PRODUCTOS_KEY_EXISTENCIAS, prod.getExistencias());
                values.put(PRODUCTOS_KEY_IMG, prod.getImg());
                db.insert(DBSQLite.TABLE_PRODUCTOS, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINEAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALBARANES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTNERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMERCIALES);

        onCreate(db);
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

    private static final String INSERT_COMERCIALES =
            "INSERT INTO " + TABLE_COMERCIALES +
                    " VALUES "  + "(null, 'GEUY', '123', 'User', 'Prueba', 'GEUY corp.', 'support@gmail.com', '666555444', 'Sur'), "
                                + "(null, 'Javi', 'Seara123', 'Javier' , 'Seara', 'CEBANC-CDEA', 'seara@gmail.com', '654342123', 'Norte'), "
                                + "(null, 'Patxi', 'Urbieta123', 'Patxi' , 'Urbieta', 'Paginas S.L.', 'patxi@gmail.com', '653478123', 'Centro')";

    // TABLA CITAS

    public static final String TABLE_CITAS = "CITAS";

    public static final String CITAS_KEY_ID = "_id";
    public static final String CITAS_KEY_FECHAHORA = "FECHAHORA";
    public static final String CITAS_KEY_CABECERA = "CABECERA";
    public static final String CITAS_KEY_TEXTO = "TEXTO";
    public static final String CITAS_KEY_FK_COMERC = "FK_COMERC_ID";

    //CREATE TABLE CITAS (_id INTEGER PRIM KEY AUTOINCR, FECHA TEXT, ...
    private static final String CREAR_CITAS =
            "CREATE TABLE " + TABLE_CITAS + "(" +
                    CITAS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CITAS_KEY_FECHAHORA + " TEXT, " +
                    CITAS_KEY_CABECERA + " TEXT, " +
                    CITAS_KEY_TEXTO + " TEXT, " +
                    CITAS_KEY_FK_COMERC + " INTEGER, " +
                    "CONSTRAINT " + CITAS_KEY_FK_COMERC + " FOREIGN KEY (" + COMERCIALES_KEY_ID + ") REFERENCES " + TABLE_COMERCIALES + "(" + COMERCIALES_KEY_ID + ") ON DELETE CASCADE )";

    // TABLA PARTNERS

    public static final String TABLE_PARTNERS = "PARTNERS";

    public static final String PARTNERS_KEY_ID = "_id";
    public static final String PARTNERS_KEY_NOMBRE = "NOMBRE";
    public static final String PARTNERS_KEY_APELLIDOS = "APELLIDOS";
    public static final String PARTNERS_KEY_POBLACION = "POBLACION";
    public static final String PARTNERS_KEY_CIF = "CIF";
    public static final String PARTNERS_KEY_EMAIL = "EMAIL";
    public static final String PARTNERS_KEY_TLFN = "TELEFONO";
    public static final String PARTNERS_KEY_FK_COMERC = "FK_COMERC_ID";
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
                    "CONSTRAINT " + PARTNERS_KEY_FK_COMERC + " FOREIGN KEY (" + COMERCIALES_KEY_ID + ") REFERENCES " + TABLE_COMERCIALES + "(" + COMERCIALES_KEY_ID + ") ON DELETE CASCADE )";

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
    public static final String ALBARANES_KEY_FK_COMERC = "FK_COMERC_ID";
    public static final String ALBARANES_KEY_FK_PARTNER = "FK_PARTNER_ID";
    public static final String ALBARANES_KEY_FECHAALBARAN = "FECHAALBARAN";
    public static final String ALBARANES_KEY_FECHAENVIO = "FECHAENVIO";
    public static final String ALBARANES_KEY_FECHAPAGO = "FECHAPAGO";

    private static final String CREAR_ALBARANES =
            "CREATE TABLE " + TABLE_ALBARANES + "(" +
                    ALBARANES_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ALBARANES_KEY_FK_COMERC + " INTEGER, " +
                    ALBARANES_KEY_FK_PARTNER + " INTEGER, " +
                    ALBARANES_KEY_FECHAALBARAN + " TEXT, " +
                    ALBARANES_KEY_FECHAENVIO + " TEXT, " +
                    ALBARANES_KEY_FECHAPAGO + " TEXT, " +
                    "CONSTRAINT " + ALBARANES_KEY_FK_COMERC + " FOREIGN KEY (" + COMERCIALES_KEY_ID + ") REFERENCES " + TABLE_COMERCIALES + "(" + COMERCIALES_KEY_ID + ") ON DELETE CASCADE, " +
                    "CONSTRAINT " + ALBARANES_KEY_FK_PARTNER + " FOREIGN KEY (" + PARTNERS_KEY_ID + ") REFERENCES " + TABLE_PARTNERS + "(" + PARTNERS_KEY_ID + ") ON DELETE CASCADE )";

    // TABLA LINEAS

    public static final String TABLE_LINEAS = "LINEAS";

    public static final String LINEAS_KEY_ID = "_id";
    public static final String LINEAS_KEY_FK_ALBARAN = "FK_ALBARAN_ID";
    public static final String LINEAS_KEY_FK_PRODUCTO = "FK_PRODUCTO_ID";
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
                    "CONSTRAINT " + LINEAS_KEY_FK_ALBARAN + " FOREIGN KEY (" + LINEAS_KEY_FK_ALBARAN + ") REFERENCES " + TABLE_ALBARANES + "(" + ALBARANES_KEY_ID + ") ON DELETE CASCADE," +
                    "CONSTRAINT " + LINEAS_KEY_FK_PRODUCTO + " FOREIGN KEY (" + LINEAS_KEY_FK_PRODUCTO + ") REFERENCES " + TABLE_PRODUCTOS + "(" + PRODUCTOS_KEY_ID + "))";

    //queries

    public Comercial queryUsuario(String usuario, String contrasenna) {
        SQLiteDatabase db = this.getReadableDatabase();
        Comercial comercial = null;

        Cursor cursor = db.rawQuery("SELECT * FROM COMERCIALES WHERE UPPER(TRIM(USR)) = UPPER(TRIM('" + usuario + "')) AND TRIM(PWD) = '" + contrasenna + "'", null);
        //Cursor cursor = db.query(DBSQLite.TABLE_COMERCIALES, new String[]{DBSQLite.COMERCIALES_KEY_ID, DBSQLite.COMERCIALES_KEY_USR, DBSQLite.COMERCIALES_KEY_PWD}, DBSQLite.COMERCIALES_KEY_USR + "=?
        // and " + DBSQLite.COMERCIALES_KEY_PWD + "=?", new String[]{usuario, contrasenna}, null, null, null, "1");
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            comercial = new Comercial();

            comercial.setId(cursor.getInt(cursor.getColumnIndex(COMERCIALES_KEY_ID)));
            comercial.setNombre(cursor.getString(cursor.getColumnIndex(COMERCIALES_KEY_NOMBRE)));
            comercial.setApellidos(cursor.getString(cursor.getColumnIndex(COMERCIALES_KEY_APELLIDOS)));
            comercial.setNombre(cursor.getString(cursor.getColumnIndex(COMERCIALES_KEY_NOMBRE)));
            comercial.setEmpresa(cursor.getString(cursor.getColumnIndex(COMERCIALES_KEY_EMPRESA)));
            comercial.setEmail(cursor.getString(cursor.getColumnIndex(COMERCIALES_KEY_EMAIL)));
            comercial.setTelefono(cursor.getString(cursor.getColumnIndex(COMERCIALES_KEY_TLFN)));
        }
        //db.close();
        cursor.close();
        return comercial;
    }

    public List<Producto> leerProductos() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Producto> productos = new ArrayList<>();
        Producto producto;

        Cursor cursor = db.query(TABLE_PRODUCTOS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                producto = new Producto();

                producto.setId(cursor.getInt(cursor.getColumnIndex(PRODUCTOS_KEY_ID)));
                producto.setCodigo(cursor.getString(cursor.getColumnIndex(PRODUCTOS_KEY_CODIGO)));
                producto.setDescripcion(cursor.getString(cursor.getColumnIndex(PRODUCTOS_KEY_DESCRIPCION)));
                producto.setPrvent(cursor.getInt(cursor.getColumnIndex(PRODUCTOS_KEY_PRVENT)));
                producto.setExistencias(cursor.getInt(cursor.getColumnIndex(PRODUCTOS_KEY_EXISTENCIAS)));
                producto.setImg(cursor.getString(cursor.getColumnIndex(PRODUCTOS_KEY_IMG)));
                productos.add(producto);
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        //db.close();
        cursor.close();
        return productos;
    }

    public List<Partner> leerPartners(int comercId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Partner> partnerList  = new ArrayList<>();
        Partner partner;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PARTNERS + " WHERE FK_COMERC_ID = " + comercId, null);

        if (cursor.moveToFirst()) {
            do {
                partner = new Partner();

                partner.setId(cursor.getInt(cursor.getColumnIndex(PARTNERS_KEY_ID)));
                partner.setNombre(cursor.getString(cursor.getColumnIndex(PARTNERS_KEY_NOMBRE)));
                partner.setApellidos(cursor.getString(cursor.getColumnIndex(PARTNERS_KEY_APELLIDOS)));
                partner.setCorreo(cursor.getString(cursor.getColumnIndex(PARTNERS_KEY_EMAIL)));
                partner.setTelefono(cursor.getString(cursor.getColumnIndex(PARTNERS_KEY_TLFN)));
                partner.setCif(cursor.getString(cursor.getColumnIndex(PARTNERS_KEY_CIF)));
                partner.setPoblacion(cursor.getString(cursor.getColumnIndex(PARTNERS_KEY_POBLACION)));
                partnerList.add(partner);
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        //db.close();
        cursor.close();
        return partnerList;
    }

    public List<Cita> leerCitas(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Cita> listaCitas = new ArrayList<>();
        Cita cita;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CITAS + " WHERE FK_COMERC_ID = " + id, null);

        if (cursor.moveToFirst()) {
            do {
                cita = new Cita();

                cita.setId(cursor.getInt(cursor.getColumnIndex(CITAS_KEY_ID)));
                cita.setFechaHora(cursor.getString(cursor.getColumnIndex(CITAS_KEY_FECHAHORA)));
                cita.setCabecera(cursor.getString(cursor.getColumnIndex(CITAS_KEY_CABECERA)));
                cita.setTexto(cursor.getString(cursor.getColumnIndex(CITAS_KEY_TEXTO)));
                listaCitas.add(cita);
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        //db.close();
        cursor.close();
        return listaCitas;
    }

    public void insertarCita(String fecha, String titulo, String texto, int comercId) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CITAS_KEY_FECHAHORA, fecha);
        contentValues.put(CITAS_KEY_CABECERA, titulo);
        contentValues.put(CITAS_KEY_TEXTO, texto);
        contentValues.put(CITAS_KEY_FK_COMERC, comercId);

        db.insert(TABLE_CITAS, null, contentValues);
        //db.close();
    }

    public void insertarPartner(String nombre, String apellidos, String correo, String telefono, String poblacion, String cif, int comercId) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PARTNERS_KEY_NOMBRE, nombre);
        contentValues.put(PARTNERS_KEY_APELLIDOS, apellidos);
        contentValues.put(PARTNERS_KEY_EMAIL, correo);
        contentValues.put(PARTNERS_KEY_TLFN, telefono);
        contentValues.put(PARTNERS_KEY_POBLACION, poblacion);
        contentValues.put(PARTNERS_KEY_CIF, cif);
        contentValues.put(PARTNERS_KEY_FK_COMERC, comercId);

        db.insert(TABLE_PARTNERS, null, contentValues);
        //db.close();
    }

    public void insertarPedido(ArrayList<Producto> productOrder, int comercId, Partner partner, String sEdtFechaPedido, String sEdtFechaEnvio, String sEdtFechaPago) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ALBARANES_KEY_FK_COMERC, comercId);
        contentValues.put(ALBARANES_KEY_FK_PARTNER, partner.getId());
        contentValues.put(ALBARANES_KEY_FECHAALBARAN, sEdtFechaPedido);
        contentValues.put(ALBARANES_KEY_FECHAENVIO, sEdtFechaEnvio);
        contentValues.put(ALBARANES_KEY_FECHAPAGO, sEdtFechaPago);

        //db.insert(DBSQLite.TABLE_ALBARANES, null, contentValues);

        long inserted = db.insert(TABLE_ALBARANES, null, contentValues);

        for(Producto prod : productOrder) {
            prod.setExistenciasDespuesCompra(prod.getCantidadPedida());
            String sql = "UPDATE PRODUCTOS SET EXISTENCIAS = " + prod.getExistencias() + " WHERE _id = '" + prod.getId() + "'";
            db.execSQL(sql);

            float precioLinea = prod.getPrvent() * prod.getCantidadPedida();

            String sql1 = "INSERT INTO LINEAS (FK_ALBARAN_ID, FK_PRODUCTO_ID, CANTIDAD, PRECIOLINEA) values ( " +
                     inserted + ", '" + prod.getId() + "', " + prod.getCantidadPedida() + ", " + precioLinea + ")";
            db.execSQL(sql1);
        }
        //db.close();
    }

    public int deleteCita(Cita cita) {
        SQLiteDatabase db = this.getReadableDatabase();

        Log.d("mLog", "cita id = " +  cita.getId());
        int deleteItem = db.delete(TABLE_CITAS, CITAS_KEY_ID + "=" + cita.getId(), null);
        Log.d("mLog", "updates rows count = " + deleteItem);

        //db.close();
        return deleteItem;
    }


    public int modificarCita(Cita cita) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues cv = new ContentValues();
        //These Fields should be your String values of actual column names
        cv.put(CITAS_KEY_FECHAHORA, cita.getFechaHora());
        cv.put(CITAS_KEY_CABECERA, cita.getCabecera());
        cv.put(CITAS_KEY_TEXTO, cita.getTexto());

        String id = String.valueOf(cita.getId());
        Log.d("mLog", "cita id = " +  cita.getId());
        Log.d("mLog", "cita FechaHora = " +  cita.getFechaHora());
        Log.d("mLog", "cita Cabecera = " +  cita.getCabecera());
        Log.d("mLog", "cita texto = " +  cita.getTexto());

        int modificarItem = db.update(TABLE_CITAS, cv, CITAS_KEY_ID + "= ?", new String[]{id});
        Log.d("mLog", "updates rows count = " + modificarItem);

        //db.close();
        return modificarItem;
    }

    public int deletePartnerOnCascade(Partner partner) {
        SQLiteDatabase db = this.getReadableDatabase();
        //db.execSQL("PRAGMA foreign_keys = ON");
        Log.d("mLog", "partner id = " +  partner.getId());
        int deleteItem = db.delete(TABLE_PARTNERS, PARTNERS_KEY_ID + "=" + partner.getId(), null);
        Log.d("mLog", "updates rows count = " + deleteItem);

        //db.close();
        return deleteItem;
    }

    public int modificarPartner(Partner partner) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(PARTNERS_KEY_NOMBRE, partner.getNombre());
        cv.put(PARTNERS_KEY_APELLIDOS, partner.getApellidos());
        cv.put(PARTNERS_KEY_EMAIL, partner.getCorreo());
        cv.put(PARTNERS_KEY_TLFN, partner.getTelefono());
        cv.put(PARTNERS_KEY_POBLACION, partner.getPoblacion());
        cv.put(PARTNERS_KEY_CIF, partner.getCif());

        String id = String.valueOf(partner.getId());

        Log.d("mLog", "partner id = " +  partner.getId());
        Log.d("mLog", "partner nombre = " +  partner.getNombre());
        Log.d("mLog", "partner email = " +  partner.getCorreo());

        int modificarItem = db.update(TABLE_PARTNERS, cv, PARTNERS_KEY_ID + "= ?", new String[]{id});
        Log.d("mLog", "updates rows count = " + modificarItem);

        //db.close();
        return modificarItem;
    }
}
