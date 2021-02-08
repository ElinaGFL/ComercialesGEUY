package com.example.comercialesgeuy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.comercialesgeuy.cita.Cita;
import com.example.comercialesgeuy.partners.Partner;
import com.example.comercialesgeuy.pedidos.Producto;
import com.example.comercialesgeuy.pedidos.gestion.XMLParserProducto;

import java.util.ArrayList;
import java.util.List;

public class DBSQLite extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 10;
    public static final String DATABASE_NAME = "GEUYDB";

    private static Context mContext;
    List<Producto> productoList = null;

    public DBSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
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

    private void insertXMLProductos(SQLiteDatabase db) {
        XMLParserProducto parser = new XMLParserProducto();

        try {
            productoList = parser.parseXML(mContext.getAssets().open("productos.xml"));

            for (Producto prod : productoList) {
                ContentValues values = new ContentValues();

                values.put(PRODUCTOS_KEY_CODIGO, prod.getCodigo());
                values.put(PRODUCTOS_KEY_DESCRIPCION, prod.getDescripcion());
                values.put(PRODUCTOS_KEY_PRVENT, prod.getPrvent());
                values.put(PRODUCTOS_KEY_EXISTENCIAS, prod.getExistencias());
                values.put(PRODUCTOS_KEY_IMG, prod.getImg());
                db.insert(TABLE_PRODUCTOS, null, values);
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
            "INSERT INTO " + TABLE_COMERCIALES + "(" + COMERCIALES_KEY_USR + ", " + COMERCIALES_KEY_PWD + "," + COMERCIALES_KEY_NOMBRE + "," + COMERCIALES_KEY_EMPRESA +")" +
                    " VALUES " + "('ANER', 'ONYX', 'ANER ZARAUTZ', 'ANER CORP'), " + "('Javi', 'Seara123', 'Javier' , 'Cebanc-CDEA')" ;

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
                    "FOREIGN KEY (" + CITAS_KEY_FK_COMERC + ") REFERENCES " + TABLE_COMERCIALES + "(" + COMERCIALES_KEY_ID + ") )";

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
                    "FOREIGN KEY (" + PARTNERS_KEY_FK_COMERC + ") REFERENCES " + TABLE_COMERCIALES + "(" + COMERCIALES_KEY_ID + ") )";
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
                    ALBARANES_KEY_FECHAALBARAN + " TEXT," +
                    ALBARANES_KEY_FECHAENVIO + " TEXT," +
                    ALBARANES_KEY_FECHAPAGO + " TEXT," +
                    "FOREIGN KEY (" + ALBARANES_KEY_FK_COMERC + ") REFERENCES " + TABLE_COMERCIALES + "(" + COMERCIALES_KEY_ID + ")," +
                    "FOREIGN KEY (" + ALBARANES_KEY_FK_PARTNER + ") REFERENCES " + TABLE_PARTNERS + "(" + PARTNERS_KEY_ID + ") )";

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
                    "FOREIGN KEY (" + LINEAS_KEY_FK_ALBARAN + ") REFERENCES " + TABLE_ALBARANES + "(" + ALBARANES_KEY_ID + ")," +
                    "FOREIGN KEY (" + LINEAS_KEY_FK_PRODUCTO + ") REFERENCES " + TABLE_PRODUCTOS + "(" + PRODUCTOS_KEY_ID + ") )";


    //queries

    public Comercial queryUsuario(String usuario, String contrasenna) {
        SQLiteDatabase db = this.getReadableDatabase();
        Comercial comercial = null;

        Cursor cursor = db.rawQuery("SELECT * FROM COMERCIALES WHERE UPPER(TRIM(USR)) = UPPER(TRIM('" + usuario + "')) AND TRIM(PWD) = '" + contrasenna + "'", null);
        //Cursor cursor = db.query(DBSQLite.TABLE_COMERCIALES, new String[]{DBSQLite.COMERCIALES_KEY_ID, DBSQLite.COMERCIALES_KEY_USR, DBSQLite.COMERCIALES_KEY_PWD}, DBSQLite.COMERCIALES_KEY_USR + "=? and " + DBSQLite.COMERCIALES_KEY_PWD + "=?", new String[]{usuario, contrasenna}, null, null, null, "1");
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            comercial = new Comercial();

            comercial.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.COMERCIALES_KEY_ID)));
            comercial.setNombre(cursor.getString(cursor.getColumnIndex(DBSQLite.COMERCIALES_KEY_NOMBRE)));
            comercial.setApellidos(cursor.getString(cursor.getColumnIndex(DBSQLite.COMERCIALES_KEY_APELLIDOS)));
            comercial.setNombre(cursor.getString(cursor.getColumnIndex(DBSQLite.COMERCIALES_KEY_NOMBRE)));
            comercial.setEmpresa(cursor.getString(cursor.getColumnIndex(DBSQLite.COMERCIALES_KEY_EMPRESA)));
            comercial.setEmail(cursor.getString(cursor.getColumnIndex(DBSQLite.COMERCIALES_KEY_EMAIL)));
            comercial.setTelefono(cursor.getString(cursor.getColumnIndex(DBSQLite.COMERCIALES_KEY_TLFN)));
        }
        db.close();
        cursor.close();
        return comercial;
    }

    public List<Producto> leerProductos() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Producto> productos = new ArrayList<>();
        Producto producto;

        Cursor cursor = db.query(DBSQLite.TABLE_PRODUCTOS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                producto = new Producto();

                producto.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.PRODUCTOS_KEY_ID)));
                producto.setCodigo(cursor.getString(cursor.getColumnIndex(DBSQLite.PRODUCTOS_KEY_CODIGO)));
                producto.setDescripcion(cursor.getString(cursor.getColumnIndex(DBSQLite.PRODUCTOS_KEY_DESCRIPCION)));
                producto.setPrvent(cursor.getInt(cursor.getColumnIndex(DBSQLite.PRODUCTOS_KEY_PRVENT)));
                producto.setExistencias(cursor.getInt(cursor.getColumnIndex(DBSQLite.PRODUCTOS_KEY_EXISTENCIAS)));
                producto.setImg(cursor.getString(cursor.getColumnIndex(DBSQLite.PRODUCTOS_KEY_IMG)));
                productos.add(producto);
                /*
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", fecha = " + cursor.getString(fechaIndex) +
                        ", cabecera = " + cursor.getString(cabeceraIndex) +
                        ", texto = " + cursor.getString(textoIndex));
                 */
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        db.close();
        cursor.close();
        return productos;
    }

    public List<Partner> rellenarPartnerList(int comercId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Partner> partnerList  = new ArrayList<>();
        Partner partner;

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBSQLite.TABLE_PARTNERS + " WHERE FK_COMERC_ID = " + comercId, null);

        if (cursor.moveToFirst()) {
            do {
                partner = new Partner();

                partner.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.PARTNERS_KEY_ID)));
                partner.setNombre(cursor.getString(cursor.getColumnIndex(DBSQLite.PARTNERS_KEY_NOMBRE)));
                partner.setApellidos(cursor.getString(cursor.getColumnIndex(DBSQLite.PARTNERS_KEY_APELLIDOS)));
                partner.setCorreo(cursor.getString(cursor.getColumnIndex(DBSQLite.PARTNERS_KEY_EMAIL)));
                partner.setTelefono(cursor.getString(cursor.getColumnIndex(DBSQLite.PARTNERS_KEY_TLFN)));
                partner.setCif(cursor.getString(cursor.getColumnIndex(DBSQLite.PARTNERS_KEY_CIF)));
                partner.setPoblacion(cursor.getString(cursor.getColumnIndex(DBSQLite.PARTNERS_KEY_POBLACION)));
                partnerList.add(partner);
                Log.d("mLog", "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
                Log.d("mLog", "ID = " + cursor.getInt(cursor.getColumnIndex(DBSQLite.PARTNERS_KEY_ID)));
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        db.close();
        cursor.close();
        return partnerList;
    }

    public List<Cita> leerCitas(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Cita> listaCitas = new ArrayList<>();
        Cita cita;

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBSQLite.TABLE_CITAS + " WHERE FK_COMERC_ID = " + id, null);

        //moveToFirst() перемещает курсор на первую строку в результате запроса и заодно проверяет есть ли вообще записи в нем
        if (cursor.moveToFirst()) {

            //далее мы получаем порядковые номера столбцов и курсор по их именам с помощью метода getColumnIndex()
            //эти номера потом мы используем для чтения данных с помощью getInt() и getString() и выводим данные в лог

            do {
                cita = new Cita();

                cita.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.CITAS_KEY_ID)));
                cita.setFechaHora(cursor.getString(cursor.getColumnIndex(DBSQLite.CITAS_KEY_FECHAHORA)));
                cita.setCabecera(cursor.getString(cursor.getColumnIndex(DBSQLite.CITAS_KEY_CABECERA)));
                cita.setTexto(cursor.getString(cursor.getColumnIndex(DBSQLite.CITAS_KEY_TEXTO)));
                listaCitas.add(cita);
                /*
                Log.d("mLog", "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", fecha = " + cursor.getString(fechaIndex) +
                        ", cabecera = " + cursor.getString(cabeceraIndex) +
                        ", texto = " + cursor.getString(textoIndex));
                 */
                //cursor.moveToNext() перебираем все строки в курсоре пока не добираемся до последней
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        //освобождаем память, т.к. курсор уже не будет нигде использоваться
        db.close();
        cursor.close();

        return listaCitas;
    }

    public void insertarCita(String fecha, String titulo, String texto, int comercId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBSQLite.CITAS_KEY_FECHAHORA, fecha);
        contentValues.put(DBSQLite.CITAS_KEY_CABECERA, titulo);
        contentValues.put(DBSQLite.CITAS_KEY_TEXTO, texto);
        contentValues.put(DBSQLite.CITAS_KEY_FK_COMERC, comercId);
        //методом insert вставляем подготовленные строки в таблицу, этот метод принимает имя таблицы и объект contentValues со
        //вставляемыми значениями, второй аргумент используется при вставке пустой строки
        db.insert(DBSQLite.TABLE_CITAS, null, contentValues);
        db.close();
    }

    public void insertarPartner(String nombre, String apellidos, String correo, String telefono, String poblacion, String cif, int comercId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBSQLite.PARTNERS_KEY_NOMBRE, nombre);
        contentValues.put(DBSQLite.PARTNERS_KEY_APELLIDOS, apellidos);
        contentValues.put(DBSQLite.PARTNERS_KEY_EMAIL, correo);
        contentValues.put(DBSQLite.PARTNERS_KEY_TLFN, telefono);
        contentValues.put(DBSQLite.PARTNERS_KEY_POBLACION, poblacion);
        contentValues.put(DBSQLite.PARTNERS_KEY_CIF, cif);
        contentValues.put(DBSQLite.PARTNERS_KEY_FK_COMERC, comercId);

        db.insert(DBSQLite.TABLE_PARTNERS, null, contentValues);
        db.close();
    }

    public void insertarPedido(ArrayList<Producto> productOrder, int comercId, Partner partner, String sEdtFechaPedido, String sEdtFechaEnvio, String sEdtFechaPago) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("FK_COMERC_ID", comercId);
        contentValues.put("FK_PARTNER_ID", partner.getId());
        contentValues.put("FECHAALBARAN", sEdtFechaPedido);
        contentValues.put("FECHAENVIO", sEdtFechaEnvio);
        contentValues.put("FECHAPAGO", sEdtFechaPago);

        //db.insert(DBSQLite.TABLE_ALBARANES, null, contentValues);

        long inserted = db.insert(DBSQLite.TABLE_ALBARANES, null, contentValues);

        for(Producto prod : productOrder) {
            prod.setExistenciasDespuesCompra(prod.getCantidadPedida());
            String sql = "UPDATE PRODUCTOS SET EXISTENCIAS = " + prod.getExistencias() + " WHERE _id = '" + prod.getId() + "'";
            db.execSQL(sql);

            float precioLinea = prod.getPrvent() * prod.getCantidadPedida();

            String sql1 = "INSERT INTO LINEAS (FK_ALBARAN_ID, FK_PRODUCTO_ID, CANTIDAD, PRECIOLINEA) values ( " +
                     inserted + ", '" + prod.getId() + "', " + prod.getCantidadPedida() + ", " + precioLinea + ")";
            db.execSQL(sql1);
        }

        db.close();
    }

    public int deleteCita(Cita cita) {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("mLog", "cita id = " +  cita.getId());
        int deleteItem = db.delete(DBSQLite.TABLE_CITAS, DBSQLite.CITAS_KEY_ID + "=" + cita.getId(), null);
        Log.d("mLog", "updates rows count = " + deleteItem);
        return deleteItem;
    }


    /*public int modificarCita(Cita cita) {

        SQLiteDatabase db = this.getReadableDatabase();
        int modificarItem = db.delete(DBSQLite.TABLE_CITAS, DBSQLite.CITAS_KEY_ID + "=" + cita.getId(), null);
        Log.d("mLog", "updates rows count = " + deleteItem);
        return deleteItem;

    }*/


}
