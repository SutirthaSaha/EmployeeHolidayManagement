package in.nfly.dell.employeeholidaymanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="employee";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(id integer primary key autoincrement,name text,surname text,joiningDate text,date text,thisMonth text,openingBalance text,monthsCompleted text,daysCompleted text,holidaysTaken text)");
    }

    public Cursor insertData(String name,String surname,String joiningDate,String date,String thisMonth,SQLiteDatabase db){
        Cursor cursor=db.rawQuery("insert into users(name,surname,joiningDate,date,thisMonth,openingBalance,monthsCompleted,daysCompleted,holidaysTaken) values ('"+name+"','"+surname+"','"+joiningDate+"','"+date+"','"+thisMonth+"','0','0','0','0')",null);
        return cursor;
    }

    public Cursor viewOneData(int id,SQLiteDatabase db){
        Cursor cursor=db.rawQuery("select * from users where id="+id,null);
        return cursor;
    }

    public Cursor viewAllData(SQLiteDatabase db){
        Cursor cursor=db.rawQuery("select * from users",null);
        return cursor;
    }

    public Cursor searchDate(String date,int id,SQLiteDatabase db){
        Cursor cursor=db.rawQuery("select * from users where date='"+date+"'and id="+id,null);
        return cursor;
    }

    public Cursor searchMonth(String thisMonth,int id,SQLiteDatabase db){
        Cursor cursor=db.rawQuery("select * from users where thisMonth='"+thisMonth+"'and id="+id,null);
        return cursor;
    }

    public Cursor updateDate(int id,String date,String daysCompleted,SQLiteDatabase db){
        Cursor cursor=db.rawQuery("update users set date='"+date+"',daysCompleted='"+daysCompleted+"' where id="+id,null);
        return cursor;
    }
    public Cursor updateMonth(int id,String thisMonth,String monthsCompleted,SQLiteDatabase db){
        Cursor cursor=db.rawQuery("update users set thisMonth='"+thisMonth+"',monthsCompleted='"+monthsCompleted+"' where id="+id,null);
        return cursor;
    }

    public Cursor updateData(int id,String name,String surname,String holidaysTaken,SQLiteDatabase db){
        Cursor cursor=db.rawQuery("update users set name='"+name+"',surname='"+surname+"',holidaysTaken='"+holidaysTaken+"' where id="+id,null);
        return cursor;
    }
    public int deleteData(String name,SQLiteDatabase db){
        String where="name=?";
        String[] whereArgs=new String[]{String.valueOf(name)};
        db.delete("users",where,whereArgs);
        return  1;
    }

    public void deleteRow(int value,SQLiteDatabase db)
    {
        db.execSQL("delete from users where id="+value);
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
