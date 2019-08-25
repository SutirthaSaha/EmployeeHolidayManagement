package in.nfly.dell.employeeholidaymanagement;

import android.app.DatePickerDialog;
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
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private Float H;
    private String appName;

    private String holidaysTaken;
    private String date,joiningDate;
    private String dayOfMonth;
    private String month;
    private Long diff;
    private EditText addName,addSurname,optionsEditText,addDate,addOpeningBalance,addDesignation;
    private ImageView addDateBtn;
    private TextInputLayout optionsTextInputOutput;

    private RecyclerView employeeRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EmployeeViewAdapter adapter;

    private Date startDate,endDate;

    private ArrayList<String> currentBalanceDataSet=new ArrayList<String>(){};
    private ArrayList<Integer> idDataSet=new ArrayList<Integer>(){};
    private ArrayList<String> nameDataSet=new ArrayList<String>(){};
    private ArrayList<String> dateDataSet=new ArrayList<String>(){};
    private ArrayList<String> holidaysTakenDataSet=new ArrayList<String>(){};
    private ArrayList<String> designationDataSet=new ArrayList<String>(){};
    private ArrayList<Employee> mainEmployeeDataSet=new ArrayList<Employee>(){};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Calendar calendar = Calendar.getInstance();
        dayOfMonth=Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        //Toast.makeText(this, dayOfMonth, Toast.LENGTH_SHORT).show();
        month=Integer.toString(calendar.get(Calendar.MONTH));
        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        //date=Integer.toString(calendar.get(Calendar.MINUTE));
        employeeRecyclerView=findViewById(R.id.employeeRecyclerView);
        layoutManager=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        employeeRecyclerView.setLayoutManager(layoutManager);

        User user=new User(MainActivity.this);
        H=user.getH();
        appName=user.getAppName();
        getSupportActionBar().setTitle(appName);
        //Toast.makeText(this, H+"\n"+appName, Toast.LENGTH_SHORT).show();
        setValues();
    }

    private void updateDateValues(final int id) {
        DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor=databaseHelper.searchDate(date,id,db);
        cursor.moveToFirst();
        if(cursor.getCount()==0) {
                Cursor cursor1 = databaseHelper.updateDate(id, date,db);
                if (cursor1.getCount() == 0) {
                    recreate();
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
                optionsEditText.setText(Float.toString(H));
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
                        editor.putFloat("H",Float.parseFloat(optionsEditText.getText().toString()));
                        editor.apply();
                        User user=new User(MainActivity.this);
                        H=user.getH();
                        appName=user.getAppName();
                        recreate();
                        getSupportActionBar().setTitle(appName);
                    }
                });

                AlertDialog alert=alertDialog.create();
                alert.show();
                return true;

            case R.id.menu_app_name:
                //Toast.makeText(MainActivity.this, "App Name is Selected", Toast.LENGTH_SHORT).show();
                LayoutInflater layoutInflater1=getLayoutInflater();
                View editOptionsLayout1=layoutInflater1.inflate(R.layout.dialog_edit_options,null);

                optionsTextInputOutput=editOptionsLayout1.findViewById(R.id.optionsTextInputOutput);
                optionsEditText=editOptionsLayout1.findViewById(R.id.optionsEditText);

                optionsEditText.setText(appName);
                optionsTextInputOutput.setHint("Company Name");
                AlertDialog.Builder alertDialog1=new AlertDialog.Builder(MainActivity.this);
                alertDialog1.setView(editOptionsLayout1);
                alertDialog1.setCancelable(false);

                alertDialog1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog1.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences=getSharedPreferences("App Details", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("appName",optionsEditText.getText().toString());
                        editor.apply();
                        User user=new User(MainActivity.this);
                        H=user.getH();
                        appName=user.getAppName();
                        getSupportActionBar().setTitle(appName);
                    }
                });

                AlertDialog alert1=alertDialog1.create();
                alert1.show();
                return true;

            case R.id.menu_search:
                SearchView searchView= (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateDateHolidaysValues(final int id,final String openingBalance,final int flag) {
        DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor=databaseHelper.searchDate(date,id,db);
        cursor.moveToFirst();
        if(cursor.getCount()==0) {
            Cursor cursor1;
            if(flag!=0) {
                cursor1 = databaseHelper.updateDateHolidays(id, date, Float.toString(Float.parseFloat(openingBalance)+H), db);
            }else{
                cursor1 = databaseHelper.updateFlag(id, db);
            }
            if (cursor1.getCount() == 0) {
                recreate();
                Toast.makeText(MainActivity.this, "Date Holidays Updated Successfully", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateMonthValues(final int id,final String openingBalance,final int flag) {
        DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor=databaseHelper.searchMonth(month,id,db);
        cursor.moveToFirst();
        if(cursor.getCount()==0) {
            Cursor cursor1;
            if(flag!=0) {
                cursor1 = databaseHelper.updateMonth(id, month, Float.toString(Float.parseFloat(openingBalance)+H), db);
            }else{
                cursor1 = databaseHelper.updateFlag(id, db);
            }
                if (cursor1.getCount() == 0) {
                    recreate();
                    Toast.makeText(MainActivity.this, "Month Updated Successfully", Toast.LENGTH_LONG).show();
                }
        }
    }

    public void setValues() {

        idDataSet.clear();
        currentBalanceDataSet.clear();
        nameDataSet.clear();
        dateDataSet.clear();
        holidaysTakenDataSet.clear();
        designationDataSet.clear();
        mainEmployeeDataSet.clear();

        DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor=databaseHelper.viewAllData(db);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            do{
                //updateDateValues(cursor.getInt(0));
                //updateDateHolidaysValues(cursor.getInt(0),Float.toString(Math.round(Float.parseFloat(cursor.getString(6))-Integer.parseInt(cursor.getString(7)))),cursor.getInt(9));
                updateMonthValues(cursor.getInt(0),Float.toString(Math.round(Float.parseFloat(cursor.getString(6))-Integer.parseInt(cursor.getString(7)))),cursor.getInt(9));
                idDataSet.add(cursor.getInt(0));
                currentBalanceDataSet.add(Float.toString(Math.round(Float.parseFloat(cursor.getString(6))-Integer.parseInt(cursor.getString(7)))));
                nameDataSet.add(cursor.getString(1)+" "+cursor.getString(2));
                dateDataSet.add(cursor.getString(3));
                designationDataSet.add(cursor.getString(8));
                Employee employee=new Employee(cursor.getString(1)+" "+cursor.getString(2),cursor.getString(3),Float.toString(Math.round(Float.parseFloat(cursor.getString(6))-Integer.parseInt(cursor.getString(7)))),cursor.getString(8),cursor.getInt(0));
                mainEmployeeDataSet.add(employee);
            }while(cursor.moveToNext());
        }
        //Toast.makeText(this, idDataSet.toString(), Toast.LENGTH_SHORT).show();
        adapter=new EmployeeViewAdapter(MainActivity.this,mainEmployeeDataSet);

        employeeRecyclerView.setAdapter(adapter);
    }

    public void onAddEmployeeClick(View view) {
        LayoutInflater layoutInflater=getLayoutInflater();
        View addEmployeeLayout=layoutInflater.inflate(R.layout.dialog_add_employee,null);

        addName=addEmployeeLayout.findViewById(R.id.addName);
        addSurname=addEmployeeLayout.findViewById(R.id.addSurname);
        addDate=addEmployeeLayout.findViewById(R.id.addDate);
        addDateBtn=addEmployeeLayout.findViewById(R.id.addDateBtn);
        addOpeningBalance=addEmployeeLayout.findViewById(R.id.addOpeningBal);
        addDesignation=addEmployeeLayout.findViewById(R.id.addDesignation);

        final Calendar calendar = Calendar.getInstance();
        final  DatePickerDialog.OnDateSetListener joiningDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(addDate,calendar);
            }

        };
        addDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, joiningDate, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //Toast.makeText(this, month+"\n"+date, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
        alertDialog.setView(addEmployeeLayout);
        alertDialog.setCancelable(false);

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(MainActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(MainActivity.this, "Submit clicked", Toast.LENGTH_SHORT).show();
                String Format = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.UK);
                try {
                    startDate=sdf.parse(addDate.getText().toString().trim());
                    endDate= sdf.parse(date);
                    diff = endDate.getTime() - startDate.getTime();
                    Toast.makeText(MainActivity.this, "Days="+TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+"\nMonths="+(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)/30), Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                Cursor cursor1 = databaseHelper.insertData(addName.getText().toString().trim(), addSurname.getText().toString().trim(),addDate.getText().toString(),date,month,addOpeningBalance.getText().toString().trim(),addDesignation.getText().toString().trim(),db);
                    if (cursor1.getCount() == 0) {
                        Toast.makeText(MainActivity.this, "Employee Added Successfully", Toast.LENGTH_LONG).show();
                        setValues();

                    }
                }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();
    }
    private void updateLabel(EditText edittext, Calendar calendar) {
        String Format = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.UK);
        edittext.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //adapter.updateList(mainEmployeeDataSet);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput=newText.toLowerCase();
        ArrayList<Employee> newList=new ArrayList<>();

        for(Employee employee:mainEmployeeDataSet){
            String employeeName=employee.getName().toLowerCase();
            //Toast.makeText(this, employeeName, Toast.LENGTH_SHORT).show();
            if(employeeName.contains(userInput)){
                //Employee newEmployee=new Employee(employee.getName(),employee.getDate(),employee.getCurrentBalance(),employee.getDaysCompleted(),employee.getMonthsCompleted(),employee.getDesignation(),employee.getEmpId());
                newList.add(employee);
            }
        }
        adapter.updateList(newList);
        return true;
    }

    @Override
    public void recreate()
    {
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            super.recreate();
        }
        else
        {
            startActivity(getIntent());
            finish();
        }
    }

    public class EmployeeViewAdapter extends RecyclerView.Adapter<EmployeeViewAdapter.EmployeeViewHolder>{

        private Context context;
        private ArrayList<Employee> employeeDataSet;

        public EmployeeViewAdapter(Context context, ArrayList<Employee> employeeDataSet) {
            this.context = context;
            this.employeeDataSet = employeeDataSet;
        }

        public void updateList(ArrayList<Employee> newDataSet){
            employeeDataSet=new ArrayList<Employee>(){};
            employeeDataSet.addAll(newDataSet);
            notifyDataSetChanged();
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
            holder.fullNameCardText.setText(this.employeeDataSet.get(position).getName());
            holder.dateCardText.setText(this.employeeDataSet.get(position).getDate());
            holder.currBalanceCardText.setText(this.employeeDataSet.get(position).getCurrentBalance());
            holder.designationCardText.setText(this.employeeDataSet.get(position).getDesignation());
            holder.employeeDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, Integer.toString(empIdDataSet.get(position)), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            MainActivity.this);
                    alert.setTitle("Alert!!");
                    alert.setMessage("Are you sure to delete record");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do your work here
                            DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                            SQLiteDatabase db = databaseHelper.getWritableDatabase();
                            databaseHelper.deleteRow(employeeDataSet.get(position).getEmpId(),db);
                            setValues();
                            dialog.dismiss();

                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    alert.show();
                }
            });
            holder.employeeEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater=getLayoutInflater();
                    View editOptionsLayout=layoutInflater.inflate(R.layout.dialog_edit_options,null);

                    final DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);
                    final SQLiteDatabase db=databaseHelper.getReadableDatabase();
                    Cursor cursor=databaseHelper.viewOneData(employeeDataSet.get(position).getEmpId(),db);
                    cursor.moveToFirst();
                    if(cursor.getCount()>0) {
                        holidaysTaken=cursor.getString(7);
                    }
                    optionsTextInputOutput=editOptionsLayout.findViewById(R.id.optionsTextInputOutput);
                    optionsEditText=editOptionsLayout.findViewById(R.id.optionsEditText);
                    //optionsEditText.setText(holidaysTaken);
                    optionsTextInputOutput.setHint("Holidays Wanted");
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
                            Toast.makeText(MainActivity.this, "Submit clicked", Toast.LENGTH_SHORT).show();
                            if((Float.parseFloat(currentBalanceDataSet.get(position))-Integer.parseInt(optionsEditText.getText().toString()))>=0)
                            {
                                holidaysTaken = Integer.toString(Integer.parseInt(holidaysTaken) + Integer.parseInt(optionsEditText.getText().toString()));
                                Cursor cursor1 = databaseHelper.updateHolidaysTaken(idDataSet.get(position), holidaysTaken, db);
                                if (cursor1.getCount() == 0) {
                                    setValues();
                                    Toast.makeText(MainActivity.this, "Holidays Taken Updated Successfully", Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(context, "Employee not allowed these many holidays", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    AlertDialog alert=alertDialog.create();
                    alert.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return employeeDataSet.size();
        }

        public class EmployeeViewHolder extends RecyclerView.ViewHolder{

            public TextView fullNameCardText,dateCardText,currBalanceCardText,designationCardText;
            public ImageView employeeDeleteBtn,employeeEditBtn;
            public EmployeeViewHolder(View itemView) {
                super(itemView);

                fullNameCardText=itemView.findViewById(R.id.fullNameCardText);
                dateCardText=itemView.findViewById(R.id.dateCardText);
                currBalanceCardText=itemView.findViewById(R.id.currBalanceCardText);
                designationCardText=itemView.findViewById(R.id.designationCardText);
                employeeDeleteBtn=itemView.findViewById(R.id.employeeDeleteBtn);
                employeeEditBtn=itemView.findViewById(R.id.employeeEditBtn);
            }
        }
    }
}