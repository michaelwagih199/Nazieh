package omel.polymogo.nazieh.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.helpers.MessageAlerts
import omel.polymogo.nazieh.models.User
import omel.polymogo.nazieh.recyclerviewAdapter.ItemAdapter
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener


class Customer : AppCompatActivity() , View.OnClickListener {
    internal var TAG = Customer::class.java.simpleName
    var messageAlerts = MessageAlerts()
    private lateinit var  mContext: Context
    private lateinit var btnFAddCustomers: FloatingActionButton

    lateinit var mSearchText : EditText
    lateinit var mRecyclerView : RecyclerView

    private lateinit var adapter: ItemAdapter

    //add Firebase Database stuff

    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var myRef: DatabaseReference


//    lateinit var mDatabase : DatabaseReference

    lateinit var FirebaseRecyclerAdapter : FirebaseRecyclerAdapter<User, UsersViewHolder>

    var customersArray = ArrayList<User ?> ()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)
        mContext = this
        btnFAddCustomers  = findViewById(R.id.fBtnAddCustomer)
        btnFAddCustomers.setOnClickListener(this)

        mSearchText =findViewById(R.id.searchText)
        mRecyclerView = findViewById(R.id.recyclerView)


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

//        mDatabase = FirebaseDatabase.getInstance().getReference("Customers")

       // mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))

        // load data
        loadFirebaseAllData()

        // to search event
        mSearchText.addTextChangedListener(object  : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val searchText = mSearchText.getText().toString().trim()
                //loadFirebaseData(searchText)
                searchByName(searchText)
            }
        } )

        ///



    }

    private fun loadFirebaseAllData() {

        val query = myRef.child("Customers")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {

                        for (issue in dataSnapshot.children) {
                            val note = issue.getValue<User>(User::class.java)

                            customersArray.add(note)

                        }
                        adapter = ItemAdapter(mContext, customersArray)
                        mRecyclerView.adapter = adapter
                        mRecyclerView.addOnItemClickListener(object : OnItemClickListener {
                            override fun onItemClicked(position: Int, view: View) {
                                Toast.makeText(mContext, "clicked on " + customersArray.get(position)?.name.toString(), Toast.LENGTH_SHORT).show()
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

    private fun searchByName(searchText : String) {

        val query = myRef.child("Customers").orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {

                        for (issue in dataSnapshot.children) {
                            val note = issue.getValue<User>(User::class.java)

                            customersArray.add(note)

                        }
                        adapter = ItemAdapter(mContext, customersArray)
                        mRecyclerView.adapter = adapter
                        mRecyclerView.addOnItemClickListener(object : OnItemClickListener {
                            override fun onItemClicked(position: Int, view: View) {
                                Toast.makeText(mContext, "clicked on " + customersArray.get(position)?.name.toString(), Toast.LENGTH_SHORT).show()
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


    override fun onClick(v: View?) {
        if (v!=null){
            when(v.getId()){
                R.id.fBtnAddCustomer->{
                    val i = Intent(this, AddCustomers::class.java)
                    startActivity(i)
                }
            }
        }

    }

    // // View Holder Class

    class UsersViewHolder(var mview : View) : RecyclerView.ViewHolder(mview) {


    }


//    private fun loadFirebaseData(searchText : String) {
//
//        if(searchText.isEmpty()){
//
//            FirebaseRecyclerAdapter.cleanup()
//            mRecyclerView.adapter = FirebaseRecyclerAdapter
//
//        }else {
//
//            val firebaseSearchQuery = mDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff")
//
//            FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<User, UsersViewHolder>(
//
//                User::class.java,
//                R.layout.item_user,
//                UsersViewHolder::class.java,
//                firebaseSearchQuery
//
//
//            ) {
//                override fun populateViewHolder(viewHolder: UsersViewHolder, model: User?, position: Int) {
//
//                    viewHolder.mview.txtVCustomerName.setText(model?.name)
//                    viewHolder.mview.txtVCustomerPhone.setText(model?.phone)
//
//                }
//
//
//            }
//
//            mRecyclerView.adapter = FirebaseRecyclerAdapter
//
//        }
//    }


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
