package in.nfly.dell.employeeholidaymanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="user";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(id integer primary key autoincrement,name text,surname text,joining_date text,date text,this_month text,opening_balance text,holidays_taken text,designation text,flag integer)");
    }

    public Cursor insertData(String name,String surname,String joiningDate,String date,String thisMonth,String openingBalance,String designation,SQLiteDatabase db){
        Cursor cursor;
        if(Integer.parseInt(openingBalance)!=0) {
            cursor = db.rawQuery("insert into users(name,surname,joining_date,date,this_month,opening_balance,holidays_taken,designation,flag) values ('" + name + "','" + surname + "','" + joiningDate + "','" + date + "','" + thisMonth + "','" + openingBalance + "','0','" + designation + "',1)", null);
        }
        else{
            cursor = db.rawQuery("insert into users(name,surname,joining_date,date,this_month,opening_balance,holidays_taken,designation,flag) values ('" + name + "','" + surname + "','" + joiningDate + "','" + date + "','" + thisMonth + "','" + openingBalance + "','0','" + designation + "',0)", null);
        }
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
        Cursor cursor=db.rawQuery("select * from users where this_month='"+thisMonth+"'and id="+id,null);
        return cursor;
    }

    public Cursor updateDate(int id,String date,SQLiteDatabase db){
        Cursor cursor=db.rawQuery("update users set date='"+date+"' where id="+id,null);
        return cursor;
    }
    public Cursor updateFlag(int id,SQLiteDatabase db){
        Cursor cursor=db.rawQuery("update users set flag=1 where id="+id,null);
        return cursor;
    }
    public Cursor updateHolidaysTaken(int id,String holidaysTaken,SQLiteDatabase db){
        Cursor cursor=db.rawQuery("update users set holidays_taken='"+holidaysTaken+"' where id="+id,null);
        return cursor;
    }
    public Cursor updateDateHolidays(int id,String date,String openingBalance,SQLiteDatabase db){
        Cursor cursor=db.rawQuery("update users set date='"+date+"',opening_balance='"+openingBalance+"',holidays_taken='0' where id="+id,null);
        return cursor;
    }
    public Cursor updateMonth(int id,String thisMonth,String openingBalance,SQLiteDatabase db){
        Cursor cursor=db.rawQuery("update users set this_month='"+thisMonth+"',opening_balance='"+openingBalance+"',holidays_taken='0' where id="+id,null);
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
