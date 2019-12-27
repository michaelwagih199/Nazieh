package omel.polymogo.nazieh.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.helpers.KeyValueBD
import omel.polymogo.nazieh.helpers.MessageAlerts
import omel.polymogo.nazieh.models.IncomeProfitPojo
import omel.polymogo.nazieh.recyclerviewAdapter.IncomeAdapter
import omel.polymogo.nazieh.recyclerviewAdapter.ItemAdapter

class Profits : AppCompatActivity() , View.OnClickListener{
    var messageAlerts = MessageAlerts()
    val key = KeyValueBD()

    private lateinit var flbtnAddProfit: FloatingActionButton

    private lateinit var mContext: Context
    lateinit var mRecyclerView : RecyclerView
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    var profitArray = ArrayList<IncomeProfitPojo?> ()
    private lateinit var adapter: IncomeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profits)
        mContext = this
        flbtnAddProfit = findViewById(R.id.flBtnAddProfit)
        flbtnAddProfit.setOnClickListener(this)
        mRecyclerView = findViewById(R.id.recyclerView2)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        loadFirebaseAllData()

    }

    override fun onClick(v: View?) {
        if (v!=null){
            when(v.getId()){

                R.id.flBtnAddProfit->{
                    val i = Intent(this, EditProfit::class.java)
                    startActivity(i)
                }

            }
        }
    }

    private fun loadFirebaseAllData() {

        val query = myRef.child("Income")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {

                        for (issue in dataSnapshot.children) {
                            val note = issue.getValue<IncomeProfitPojo>(IncomeProfitPojo::class.java)

                            profitArray.add(note)
                        }
                        adapter = IncomeAdapter(mContext, profitArray)
                        mRecyclerView.adapter = adapter
                        mRecyclerView.addOnItemClickListener(object : OnItemClickListener {
                            override fun onItemClicked(position: Int, view: View) {
                                Toast.makeText(mContext, "clicked on " + profitArray.get(position)?.customerName.toString(), Toast.LENGTH_SHORT).show()
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

    class UsersViewHolder(var mview : View) : RecyclerView.ViewHolder(mview) {


    }


    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
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
