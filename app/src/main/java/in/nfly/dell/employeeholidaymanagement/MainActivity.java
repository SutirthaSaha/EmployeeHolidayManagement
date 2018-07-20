package in.nfly.dell.employeeholidaymanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private int H;
    private String appName;

    private String date;
    private String month;
    private EditText addName,addSurname,optionsEditText;
    private TextInputLayout optionsTextInputOutput;

    private RecyclerView employeeRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private ArrayList<String> currentBalanceDataSet=new ArrayList<String>(){};
    private ArrayList<Integer> idDataSet=new ArrayList<Integer>(){};
    private ArrayList<String> nameDataSet=new ArrayList<String>(){};
    private ArrayList<String> dateDataSet=new ArrayList<String>(){};
    private ArrayList<String> daysCompletedDataSet=new ArrayList<String>(){};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Calendar calendar = Calendar.getInstance();
        month=Integer.toString(calendar.get(Calendar.MONTH));
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        employeeRecyclerView=findViewById(R.id.employeeRecyclerView);
        layoutManager=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        employeeRecyclerView.setLayoutManager(layoutManager);

        User user=new User(MainActivity.this);
        H=user.getH();
        appName=user.getAppName();
        Toast.makeText(this, H+"\n"+appName, Toast.LENGTH_SHORT).show();
        setValues();
    }

    private void updateDateValues(final int id,final String daysCompleted) {
        DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor=databaseHelper.searchDate(date,id,db);
        cursor.moveToFirst();
        if(cursor.getCount()==0) {
                Cursor cursor1 = databaseHelper.updateDate(id, date, Integer.toString(Integer.parseInt(daysCompleted)+1),db);
                if (cursor1.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "Date Updated Successfully", Toast.LENGTH_LONG).show();
                }
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.menu_h_value:
                LayoutInflater layoutInflater=getLayoutInflater();
                View editOptionsLayout=layoutInflater.inflate(R.layout.dialog_edit_options,null);

                optionsTextInputOutput=editOptionsLayout.findViewById(R.id.optionsTextInputOutput);
                optionsEditText=editOptionsLayout.findViewById(R.id.optionsEditText);
                optionsEditText.setText(Integer.toString(H));
                optionsTextInputOutput.setHint("H- value");
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
                alertDialog.setView(editOptionsLayout);
                alertDialog.setCancelable(false);

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences=getSharedPreferences("App Details", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putInt("H",Integer.parseInt(optionsEditText.getText().toString()));
                        editor.apply();
                    }
                });

                AlertDialog alert=alertDialog.create();
                alert.show();
                return true;

            case R.id.menu_app_name:
                Toast.makeText(MainActivity.this, "App Name is Selected", Toast.LENGTH_SHORT).show();
                LayoutInflater layoutInflater1=getLayoutInflater();
                View editOptionsLayout1=layoutInflater1.inflate(R.layout.dialog_edit_options,null);

                optionsTextInputOutput=editOptionsLayout1.findViewById(R.id.optionsTextInputOutput);
                optionsEditText=editOptionsLayout1.findViewById(R.id.optionsEditText);

                optionsEditText.setText(appName);
                optionsTextInputOutput.setHint("App Name");
                AlertDialog.Builder alertDialog1=new AlertDialog.Builder(MainActivity.this);
                alertDialog1.setView(editOptionsLayout1);
                alertDialog1.setCancelable(false);

                alertDialog1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog1.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences=getSharedPreferences("App Details", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("appName",optionsEditText.getText().toString());
                        editor.apply();
                    }
                });

                AlertDialog alert1=alertDialog1.create();
                alert1.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateMonthValues(final int id,final String monthsCompleted) {
        DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor=databaseHelper.searchMonth(month,id,db);
        cursor.moveToFirst();
        if(cursor.getCount()==0) {
                Cursor cursor1 = databaseHelper.updateDate(id, date,Integer.toString(Integer.parseInt(monthsCompleted)+1), db);
                if (cursor1.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "Date Updated Successfully", Toast.LENGTH_LONG).show();
                }
        }
    }

    public void setValues() {
        idDataSet.clear();
        currentBalanceDataSet.clear();
        nameDataSet.clear();
        dateDataSet.clear();
        daysCompletedDataSet.clear();
        DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor=databaseHelper.viewAllData(db);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            do{
                updateDateValues(cursor.getInt(0),cursor.getString(8));
                updateMonthValues(cursor.getInt(0),cursor.getString(7));
                idDataSet.add(cursor.getInt(0));
                currentBalanceDataSet.add(Integer.toString(Integer.parseInt(cursor.getString(6))+H*Integer.parseInt(cursor.getString(7))-Integer.parseInt(cursor.getString(9))));
                nameDataSet.add(cursor.getString(1)+" "+cursor.getString(2));
                dateDataSet.add(cursor.getString(3));
                daysCompletedDataSet.add(cursor.getString(8));
            }while(cursor.moveToNext());
        }
        Toast.makeText(this, idDataSet.toString(), Toast.LENGTH_SHORT).show();
        adapter=new EmployeeViewAdapter(MainActivity.this,nameDataSet,dateDataSet,daysCompletedDataSet,idDataSet);
        employeeRecyclerView.setAdapter(adapter);
    }

    public void onAddEmployeeClick(View view) {
        LayoutInflater layoutInflater=getLayoutInflater();
        View addEmployeeLayout=layoutInflater.inflate(R.layout.dialog_add_employee,null);

        addName=addEmployeeLayout.findViewById(R.id.addName);
        addSurname=addEmployeeLayout.findViewById(R.id.addSurname);

        Toast.makeText(this, month+"\n"+date, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
        alertDialog.setView(addEmployeeLayout);
        alertDialog.setCancelable(false);

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Submit clicked", Toast.LENGTH_SHORT).show();
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                Cursor cursor1 = databaseHelper.insertData(addName.getText().toString().trim(), addSurname.getText().toString().trim(),date,date,month,db);
                    if (cursor1.getCount() == 0) {
                        Toast.makeText(MainActivity.this, "Employee Added Successfully", Toast.LENGTH_LONG).show();
                        setValues();
                    }
                }
        });

        AlertDialog alert=alertDialog.create();
        alert.show();
    }

    public class EmployeeViewAdapter extends RecyclerView.Adapter<EmployeeViewAdapter.EmployeeViewHolder>{

        private Context context;
        private ArrayList<String> nameDataSet,dateDataSet,currentBalanceDataSet;
        private ArrayList<Integer> empIdDataSet;

        public EmployeeViewAdapter(Context context, ArrayList<String> nameDataSet, ArrayList<String> dateDataSet, ArrayList<String> currentBalanceDataSet, ArrayList<Integer> idDataSet) {
            this.context = context;
            this.nameDataSet = nameDataSet;
            this.dateDataSet = dateDataSet;
            this.currentBalanceDataSet = currentBalanceDataSet;
            this.empIdDataSet = idDataSet;
        }

        @NonNull
        @Override
        public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_employee_details,parent,false);
            EmployeeViewHolder holder=new EmployeeViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull EmployeeViewHolder holder, final int position) {
            holder.fullNameCardText.setText(nameDataSet.get(position));
            holder.dateCardText.setText(dateDataSet.get(position));
            holder.currBalanceCardText.setText(currentBalanceDataSet.get(position));
            holder.employeeDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, Integer.toString(empIdDataSet.get(position)), Toast.LENGTH_SHORT).show();
                    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    databaseHelper.deleteRow(empIdDataSet.get(position),db);
                    setValues();
                }
            });
        }

        @Override
        public int getItemCount() {
            return dateDataSet.size();
        }

        public class EmployeeViewHolder extends RecyclerView.ViewHolder{

            public TextView fullNameCardText,dateCardText,currBalanceCardText;
            public ImageView employeeDeleteBtn;
            public EmployeeViewHolder(View itemView) {
                super(itemView);
                fullNameCardText=itemView.findViewById(R.id.fullNameCardText);
                dateCardText=itemView.findViewById(R.id.dateCardText);
                currBalanceCardText=itemView.findViewById(R.id.currBalanceCardText);
                employeeDeleteBtn=itemView.findViewById(R.id.employeeDeleteBtn);
            }
        }
    }

}
