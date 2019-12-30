package omel.polymogo.nazieh.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.models.ExpencessPojo
import omel.polymogo.nazieh.recyclerviewAdapter.ExpensessAdapter

class Expenses : AppCompatActivity(), View.OnClickListener {

    private lateinit var flbtnAddExpensess: FloatingActionButton
    private lateinit var mContext: Context

    lateinit var mRecyclerView: RecyclerView
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    var expensesArray = ArrayList<ExpencessPojo?>()
    private lateinit var adapter: ExpensessAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)
        flbtnAddExpensess = findViewById(R.id.flBtnAddExpenses)
        flbtnAddExpensess.setOnClickListener(this)
        mContext = this

        mRecyclerView = findViewById(R.id.recyclerViewExpenses)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        loadFirebaseAllData()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.getId()) {

                R.id.flBtnAddExpenses -> {
                    val i = Intent(this, EditExpensess::class.java)
                    startActivity(i)
                    finish()
                }

            }
        }
    }

    private fun loadFirebaseAllData() {

        val query = myRef.child("expenses")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {

                        for (issue in dataSnapshot.children) {
                            val note = issue.getValue<ExpencessPojo>(ExpencessPojo::class.java)

                            expensesArray.add(note)
                        }
                        adapter = ExpensessAdapter(mContext, expensesArray)
                        mRecyclerView.adapter = adapter
                        mRecyclerView.addOnItemClickListener(object : Expenses.OnItemClickListener {
                            override fun onItemClicked(position: Int, view: View) {
//                                Toast.makeText(
//                                    mContext,
//                                    "clicked on " + expensesArray.get(position)?.userId.toString(),
//                                    Toast.LENGTH_SHORT
//                                ).show()
                            }
                        })
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ram", databaseError.details)
            }
        })


    }

    // // View Holder Class

    class UsersViewHolder(var mview: View) : RecyclerView.ViewHolder(mview) {


    }


    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View) {
                view?.setOnClickListener(null)
            }

            override fun onChildViewAttachedToWindow(view: View) {
                view?.setOnClickListener({
                    val holder = getChildViewHolder(view)
                    onClickListener.onItemClicked(holder.adapterPosition, view)
                })
            }

        })


    }
}
