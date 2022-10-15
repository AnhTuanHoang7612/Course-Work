package com.nathan.expensemanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nathan.expensemanager.R;
import com.nathan.expensemanager.model.ExpensesModel;
import java.util.List;

//Class này dùng để thiết lập data cho view là R.layout.item_expense với đối tượng là Expense
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseAdapterVH>{
    List<ExpensesModel> items;
    Context mContext;
    //Hàm khởi tạo adapter với tham số là môi trường contex
    public ExpenseAdapter(Context context){
        this.mContext = context;
    }

    //Hàm set lại data cho Recycler view
    public void setData(List<ExpensesModel> listExpense){
        this.items = listExpense;
        notifyDataSetChanged();
    }

    //Set up view cho item
    @NonNull
    @Override
    public ExpenseAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_expense, parent, false);
        return new ExpenseAdapterVH(view);
    }

    //Bingding data cho item
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapterVH holder, int position) {
        ExpensesModel expensesModel = items.get(position);
        if (expensesModel == null){
            return;
        }

        //set data for item
        holder.tvType.setText(expensesModel.getType());
        holder.tvAmount.setText(expensesModel.getAmount());
        holder.tvTimeOfExpense.setText(expensesModel.getTimeOfExpense());
    }

    //Get số lượng item của items
    @Override
    public int getItemCount() {
        if(items == null)
            return 0;
        return items.size();
    }

    //View Holder dùng để ánh xạ view
    class ExpenseAdapterVH extends RecyclerView.ViewHolder {
        TextView tvType, tvTimeOfExpense, tvAmount;
        public ExpenseAdapterVH(@NonNull View itemView) {
            super(itemView);

            //Ánh xạ view
            tvType = itemView.findViewById(R.id.tvType);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvTimeOfExpense = itemView.findViewById(R.id.tvTimeOfExpense);
        }
    }
}
