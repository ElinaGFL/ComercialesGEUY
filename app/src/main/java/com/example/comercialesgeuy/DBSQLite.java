package com.example.comercialesgeuy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.comercialesgeuy.cita.Cita;
import com.example.comercialesgeuy.partners.Partner;
import com.example.comercialesgeuy.pedidos.Albaran;
import com.example.comercialesgeuy.pedidos.Linea;
import com.example.comercialesgeuy.pedidos.Producto;
import com.example.comercialesgeuy.pedidos.XMLParserProducto;

import java.util.ArrayList;
import java.util.List;

/*
Clase heredada de SQLiteOpenHelper abstracto, con la que puede crear, abrir y actualizar bases de datos
Aquí debe implementar 2 métodos abstractos requeridos: onCreate () y onUpgrade ()
// onCreate () se llama cuando la base de datos se crea por primera vez y onUpgrade () se llama cuando la base de datos cambia, es decir, si se especifica en
// aplicación, el número de versión de la base de datos es mayor que en la propia base de datos (se usa, por ejemplo, cuando se actualiza la aplicación, cuando se necesita actualizar la base de datos)
Aquí necesita implementar un constructor, en él llamamos al constructor de la superclase y le pasamos 4 parámetros: contexto, nombre de la base de datos,
un objeto de la clase CursorFactory, que extiende el cursor estándar (en este ejemplo, no se usa -> nulo) y la versión de la base de datos

// también tiene métodos opcionales:
onDowngrade() - llamado en una situación opuesta a onUpgrade ()
onOpen() - llamado cuando se abre la base de datos
getReadableDatabase(): devuelve una base de datos para lectura // dependiendo de la situación crea, elimina y crea una base de datos (si la versión ha cambiado) o simplemente devuelve datos de la base de datos
getWritableDatabase(): devuelve una base de datos para leer y escribir // dependiendo de la situación, crea, elimina y crea una base de datos (si la versión ha cambiado) o simplemente devuelve datos de la base de datos
*/

public class DBSQLite extends SQLiteOpenHelper {

    // constantes para la versión de la base de datos, el nombre de la base de datos y el nombre de la tabla

     /* comience a contar desde uno, al cambiar el número de versión, DBHelper comprenderá que la estructura de la base de datos debe actualizarse
        La verificación de la versión de la base de datos se implementa en el método onUpgrade ()
        * * *
        en esta clase se acostumbra declarar constantes de cadena públicas para los nombres de campos y tablas para que se puedan usar
        en otras clases para definir los nombres de tablas y columnas al realizar consultas a la base de datos, privado -> público
     */
    private static Context context;
    private static final int DATABASE_VERSION = 31;
    private static final String DATABASE_NAME = "GEUYDB";

    // CursorFactory no se usa en este caso, por lo que fue anulado
    // y reemplazó los parámetros estándar con constantes
    public DBSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /*
    este método implementa la lógica de crear tablas y llenarlas con datos iniciales usando comandos SQL especiales
    el método onCreate () se llama solo si la base de datos no existe y debe crearse
    para crear la base de datos, se usa el método execSQL () del objeto SQLiteDatabase para ejecutar la consulta SQL que crea la tabla
    debe tener en cuenta los espacios y signos entre los comandos y las teclas para obtener el comando correcto
     */
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

    /*
    en este método, que se activará cuando cambie el número de versión, puede implementar una consulta en la base de datos para destruir la tabla DROP TABLE
    luego llame al método onCreate () nuevamente para crear una nueva versión de la tabla con la estructura actualizada
     */
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

    /*
    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }
    */

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

    // constantes para encabezados de columna de tabla
    // el identificador debe tener un valor con el guión bajo y el id, este nombre se usa en Android para trabajar con cursores, esta es una característica de la plataforma

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
                    "FOREIGN KEY (" + COMERCIALES_KEY_ID + ") REFERENCES " + TABLE_COMERCIALES + "(" + COMERCIALES_KEY_ID + ") ON DELETE CASCADE )";

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
                    "FOREIGN KEY (" + COMERCIALES_KEY_ID + ") REFERENCES " + TABLE_COMERCIALES + "(" + COMERCIALES_KEY_ID + ") ON DELETE CASCADE )";

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

    // login
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

    //recoger el nombre del comercial segun su id
    public String leerNombreComercial(int idComercial) {
        SQLiteDatabase db = this.getReadableDatabase();
        String comercial=null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COMERCIALES + " WHERE "+ COMERCIALES_KEY_ID +"= " + idComercial, null);

        if (cursor.moveToFirst()) {
            do {
                comercial=cursor.getString(cursor.getColumnIndex(COMERCIALES_KEY_NOMBRE));
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        //db.close();
        cursor.close();
        return comercial;
    }

    // recoger todos productos de BD
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

    // recoger todos partners
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

    //recoger los datos del partner según su id
    public Partner leerPartner(int idpartner) {
        SQLiteDatabase db = this.getReadableDatabase();
        Partner partner  = new Partner();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PARTNERS + " WHERE "+ PARTNERS_KEY_ID +"= " + idpartner, null);

        if (cursor.moveToFirst()) {
            do {
                partner = new Partner();

                partner.setNombre(cursor.getString(cursor.getColumnIndex(PARTNERS_KEY_NOMBRE)));
                partner.setApellidos(cursor.getString(cursor.getColumnIndex(PARTNERS_KEY_APELLIDOS)));
                partner.setCorreo(cursor.getString(cursor.getColumnIndex(PARTNERS_KEY_EMAIL)));
                partner.setTelefono(cursor.getString(cursor.getColumnIndex(PARTNERS_KEY_TLFN)));
                partner.setCif(cursor.getString(cursor.getColumnIndex(PARTNERS_KEY_CIF)));
                partner.setPoblacion(cursor.getString(cursor.getColumnIndex(PARTNERS_KEY_POBLACION)));
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        //db.close();
        cursor.close();
        return partner;
    }

    //añadir un partner nuevo
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

    //borar solo un partner
    public int deletePartner(Partner partner) {
        SQLiteDatabase db = this.getReadableDatabase();

        //Log.d("mLog", "partner id = " +  partner.getId());
        int deleteItem = db.delete(TABLE_PARTNERS, PARTNERS_KEY_ID + "=" + partner.getId(), null);
        Log.d("mLog", "updates rows count = " + deleteItem);

        //db.close();
        return deleteItem;
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

    // recoger todos los pedidos según el comercial entrado
    public List<Albaran> leerPedidos(int comercId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Albaran> albaranList  = new ArrayList<>();
        Albaran albaran;

        Cursor cursor = db.rawQuery("SELECT " + TABLE_ALBARANES + ".*, " + TABLE_PARTNERS + "." +
                PARTNERS_KEY_NOMBRE + ", " + TABLE_PARTNERS + "." + PARTNERS_KEY_APELLIDOS +
                " FROM " + TABLE_ALBARANES + " LEFT JOIN " + TABLE_PARTNERS + " ON " +
                TABLE_ALBARANES + "." + ALBARANES_KEY_FK_PARTNER + " = " + TABLE_PARTNERS + "." + PARTNERS_KEY_ID +
                " WHERE " + TABLE_ALBARANES + "." + ALBARANES_KEY_FK_COMERC +"= " + comercId, null);

        if (cursor.moveToFirst()) {
            do {
                albaran = new Albaran();

                albaran.setId(cursor.getInt(cursor.getColumnIndex(ALBARANES_KEY_ID)));
                albaran.setIdComerc(cursor.getInt(cursor.getColumnIndex(ALBARANES_KEY_FK_COMERC)));
                albaran.setIdPartner(cursor.getInt(cursor.getColumnIndex(ALBARANES_KEY_FK_PARTNER)));
                albaran.setFechaPedido(cursor.getString(cursor.getColumnIndex(ALBARANES_KEY_FECHAALBARAN)));
                albaran.setFechaEnvio(cursor.getString(cursor.getColumnIndex(ALBARANES_KEY_FECHAENVIO)));
                albaran.setFechaPago(cursor.getString(cursor.getColumnIndex(ALBARANES_KEY_FECHAPAGO)));
                String nombrePartner = cursor.getString(cursor.getColumnIndex(PARTNERS_KEY_NOMBRE)) + " " + cursor.getString(cursor.getColumnIndex(PARTNERS_KEY_APELLIDOS));
                albaran.setNombrePartner(nombrePartner);

                albaranList.add(albaran);
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        //db.close();
        cursor.close();
        return albaranList;
    }

    //añadir un pedido nuevo y quitar la cantidad comprada desde su existencias
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

        int count = 0;
        for(Producto prod : productOrder) {
            count++;
            prod.setExistenciasDespuesCompra(prod.getCantidadPedida());
            String sql = "UPDATE PRODUCTOS SET EXISTENCIAS = " + prod.getExistencias() + " WHERE _id = '" + prod.getId() + "'";
            db.execSQL(sql);

            float precioLinea = prod.getPrvent() * prod.getCantidadPedida();

            String sql1 = "INSERT INTO LINEAS (_id, FK_ALBARAN_ID, FK_PRODUCTO_ID, CANTIDAD, PRECIOLINEA) values ( " + count +
                    "," + inserted + ", '" + prod.getId() + "', " + prod.getCantidadPedida() + ", " + precioLinea + ")";
            db.execSQL(sql1);
        }
        //db.close();
    }

    //recoger todas lineas del pedido/albaran segun su id
    public List<Linea> leerLineas(int idAlbaran) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Linea> listLineas  = new ArrayList<>();
        Linea linea;

        Cursor cursor = db.rawQuery("SELECT " + TABLE_LINEAS + ".*, " + TABLE_PRODUCTOS + "." +
                PRODUCTOS_KEY_DESCRIPCION + " FROM " + TABLE_LINEAS + " INNER JOIN " + TABLE_PRODUCTOS + " ON " +
                TABLE_LINEAS + "." + LINEAS_KEY_FK_PRODUCTO + " = " + TABLE_PRODUCTOS + "." + PRODUCTOS_KEY_ID +
                " WHERE " + TABLE_LINEAS + "." + LINEAS_KEY_FK_ALBARAN +"= " + idAlbaran, null);

        if (cursor.moveToFirst()) {
            do {
                linea = new Linea();

                linea.setIdLinea(cursor.getInt(cursor.getColumnIndex(LINEAS_KEY_ID)));
                linea.setCantidad(cursor.getInt(cursor.getColumnIndex(LINEAS_KEY_CANTIDAD)));
                linea.setIdAlbaran(cursor.getInt(cursor.getColumnIndex(LINEAS_KEY_FK_ALBARAN)));
                linea.setIdProducto(cursor.getInt(cursor.getColumnIndex(LINEAS_KEY_FK_PRODUCTO)));
                linea.setPrecioLinea(cursor.getInt(cursor.getColumnIndex(LINEAS_KEY_PRECIOLINEA)));
                linea.setNombre(cursor.getString(cursor.getColumnIndex(PRODUCTOS_KEY_DESCRIPCION)));

                listLineas.add(linea);
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        //db.close();
        cursor.close();
        return listLineas;
    }

    //borrar la cita
    public int deleteCita(Cita cita) {
        SQLiteDatabase db = this.getReadableDatabase();

        Log.d("mLog", "cita id = " +  cita.getId());
        int deleteItem = db.delete(TABLE_CITAS, CITAS_KEY_ID + "=" + cita.getId(), null);
        Log.d("mLog", "updates rows count = " + deleteItem);

        //db.close();
        return deleteItem;
    }

    //modificar la cita
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

    // recoger la lista de citas según el comercial entrado
    public List<Cita> leerCitas(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Cita> listaCitas = new ArrayList<>();
        Cita cita;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CITAS + " WHERE FK_COMERC_ID = " + id +
                " ORDER BY " + CITAS_KEY_FECHAHORA, null);

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

    public List<Cita> leerCitasPorFecha(int comercId, String fechaElegida) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Cita> listaCitas = new ArrayList<>();
        Cita cita;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CITAS + " WHERE FK_COMERC_ID = " + comercId +
                " AND date(" + CITAS_KEY_FECHAHORA + ") = date('" + fechaElegida + "') " +
                " ORDER BY " + CITAS_KEY_FECHAHORA, null);

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

    //añadir una nueva cita
    public void insertarCita(String fecha, String titulo, String texto, int comercId) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CITAS_KEY_FECHAHORA, fecha);
        contentValues.put(CITAS_KEY_CABECERA, titulo);
        contentValues.put(CITAS_KEY_TEXTO, texto);
        contentValues.put(CITAS_KEY_FK_COMERC, comercId);

        //Log.d("mLog", "cita id = " +  cita.getId());
        Log.d("mLog", "cita FechaHora = " +  fecha);
        Log.d("mLog", "cita Cabecera = " +  titulo);
        Log.d("mLog", "cita texto = " +  texto);
        Log.d("mLog", "cita FK = " +  comercId);

        db.insert(TABLE_CITAS, null, contentValues);
        //db.close();
    }

    public int deleteLineasPorIdAlbaran(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        int deleteItem = db.delete(TABLE_LINEAS, LINEAS_KEY_FK_ALBARAN + " = " + id, null);

        Log.d("mLog", "updates rows count = " + deleteItem);

        //db.close();
        return deleteItem;
    }

    public int deletePedidosPorAlbaran(Albaran alba) {
        SQLiteDatabase db = this.getReadableDatabase();

        Log.d("mLog", "partner id = " +  alba.getId());

        int deleteItem = db.delete(TABLE_ALBARANES, ALBARANES_KEY_ID + " = " + alba.getId(), null);

        Log.d("mLog", "updates rows count = " + deleteItem);

        //db.close();
        return deleteItem;
    }
}
