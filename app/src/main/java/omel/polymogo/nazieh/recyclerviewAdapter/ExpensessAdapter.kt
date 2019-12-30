package omel.polymogo.nazieh.recyclerviewAdapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.activities.EditProfit
import omel.polymogo.nazieh.activities.Expenses
import omel.polymogo.nazieh.models.ExpencessPojo


class ExpensessAdapter(val context: Context, val models: ArrayList<ExpencessPojo?>) :
    RecyclerView.Adapter<ExpensessAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpensessAdapter.ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_expenses, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpensessAdapter.ItemViewHolder, position: Int) {
        models[position]?.let { holder.bindItems(context, it) }
    }

    override fun getItemCount(): Int {
        return models.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(context: Context, model: ExpencessPojo) {

            val txtVstatment =
                itemView.findViewById<TextView>(R.id.txtVstatment) as TextView
            val txtVExpenses = itemView.findViewById<TextView>(R.id.txtVExpenses) as TextView
            val txtVExpensesDate =
                itemView.findViewById<TextView>(R.id.txtVExpensesDate) as TextView

            val edit = itemView.findViewById<ImageView>(R.id.edit_Image_expenses) as ImageView
            val delete = itemView.findViewById<ImageView>(R.id.delete_image_expenses) as ImageView

            txtVstatment.text = model.statatment
            txtVExpenses.text = model.expenses.toString()
            txtVExpensesDate.text = model.date
            var id = model.userId

            edit.setOnClickListener {
                var i = Intent(context, EditProfit::class.java)
                i.putExtra("editFlag", "edit")
                i.putExtra("ID", model.userId)
                context.startActivity(i)
                (context as Activity).finish()
            }

            delete.setOnClickListener {
                try {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    val dialog: AlertDialog = builder.setTitle("مسح لعميل ")
                        .setMessage("هل تريد مسح العميل؟")
                        .setPositiveButton("OK") { _, _ ->
                            val dbNode =
                                FirebaseDatabase.getInstance().getReference("expenses").child(id)
                            dbNode.setValue(null)
                            //
                            var i = Intent(context, Expenses::class.java)
                            context.startActivity(i)
                            (context as Activity).finish()


                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                    dialog.show()

                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE)

                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.DKGRAY)

                } catch (e: Exception) {
                    Log.d("tag", e.localizedMessage)
                }
            }

        }


    }

}