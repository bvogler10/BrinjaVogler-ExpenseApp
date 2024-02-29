package com.example.expensetracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.database.Expense
import com.example.expensetracker.databinding.ListItemExpenseBinding

class ExpenseListAdapter(
    private var expenses: List<Expense>,
    private val onItemClick: (Expense) -> Unit
    ) :
    RecyclerView.Adapter<ExpenseListAdapter.ExpenseHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemExpenseBinding.inflate(inflater, parent, false)
        return ExpenseHolder(binding)
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    override fun onBindViewHolder(holder: ExpenseHolder, position: Int) {
        val expense = expenses[position]
        holder.bind(expense)
    }

    fun updateExpenseList(refreshList: List<Expense>) {
        expenses = refreshList
        notifyDataSetChanged()
    }

    inner class ExpenseHolder(
        private val binding: ListItemExpenseBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(expenses[position])
                }
            }
        }
        fun bind(expense: Expense) {
            binding.expenseTitle.text = expense.title
            binding.expenseType.text = expense.type
            val expenseAmount = expense.amount.toString()
            binding.expenseAmount.text = "$$expenseAmount"
            binding.expenseDate.text = expense.date.toString()
        }
    }
}