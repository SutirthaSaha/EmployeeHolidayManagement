package in.nfly.dell.employeeholidaymanagement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EmployeeViewAdapter extends RecyclerView.Adapter<EmployeeViewAdapter.EmployeeViewHolder>{

    private Context context;
    private ArrayList<String> nameDataSet,dateDataSet,currentBalanceDataSet;
    private ArrayList<Integer> idDataSet;

    public EmployeeViewAdapter(Context context, ArrayList<String> nameDataSet, ArrayList<String> dateDataSet, ArrayList<String> currentBalanceDataSet, ArrayList<Integer> idDataSet) {
        this.context = context;
        this.nameDataSet = nameDataSet;
        this.dateDataSet = dateDataSet;
        this.currentBalanceDataSet = currentBalanceDataSet;
        this.idDataSet = idDataSet;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_employee_details,parent,false);
        EmployeeViewHolder holder=new EmployeeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.fullNameCardText.setText(nameDataSet.get(position));
        holder.dateCardText.setText(dateDataSet.get(position));
        holder.currBalanceCardText.setText(currentBalanceDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dateDataSet.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder{

        public TextView fullNameCardText,dateCardText,currBalanceCardText;
        public EmployeeViewHolder(View itemView) {
            super(itemView);
            fullNameCardText=itemView.findViewById(R.id.fullNameCardText);
            dateCardText=itemView.findViewById(R.id.dateCardText);
            currBalanceCardText=itemView.findViewById(R.id.currBalanceCardText);
        }
    }
}
