package omel.polymogo.nazieh.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.item_user.view.*
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.helpers.MessageAlerts
import omel.polymogo.nazieh.models.User

class Customer : AppCompatActivity() , View.OnClickListener {
    var messageAlerts = MessageAlerts()
    private lateinit var  mContext: Context
    private lateinit var btnFAddCustomers: FloatingActionButton

    lateinit var mSearchText : EditText
    lateinit var mRecyclerView : RecyclerView

    lateinit var mDatabase : DatabaseReference

    lateinit var FirebaseRecyclerAdapter : FirebaseRecyclerAdapter<User, UsersViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)
        mContext = this
        btnFAddCustomers  = findViewById(R.id.fBtnAddCustomer)
        btnFAddCustomers.setOnClickListener(this)

        mSearchText =findViewById(R.id.searchText)
        mRecyclerView = findViewById(R.id.recyclerView)


        mDatabase = FirebaseDatabase.getInstance().getReference("Customers")

        mRecyclerView.setHasFixedSize(true)
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

                loadFirebaseData(searchText)
            }
        } )


    }

    private fun loadFirebaseAllData() {
            val firebaseSearchQuery = mDatabase

            FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<User, UsersViewHolder>(

                User::class.java,
                R.layout.item_user,
                UsersViewHolder::class.java,
                firebaseSearchQuery

            ) {
                override fun populateViewHolder(viewHolder: UsersViewHolder, model: User?, position: Int) {

                    viewHolder.mview.txtVCustomerName.setText(model?.name)
                    viewHolder.mview.txtVCustomerPhone.setText(model?.phone)


                }



            }

            mRecyclerView.adapter = FirebaseRecyclerAdapter


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


    private fun loadFirebaseData(searchText : String) {

        if(searchText.isEmpty()){

            FirebaseRecyclerAdapter.cleanup()
            mRecyclerView.adapter = FirebaseRecyclerAdapter

        }else {

            val firebaseSearchQuery = mDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff")

            FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<User, UsersViewHolder>(

                User::class.java,
                omel.polymogo.nazieh.R.layout.item_user,
                UsersViewHolder::class.java,
                firebaseSearchQuery


            ) {
                override fun populateViewHolder(viewHolder: UsersViewHolder, model: User?, position: Int) {

                    viewHolder.mview.txtVCustomerName.setText(model?.name)
                    viewHolder.mview.txtVCustomerPhone.setText(model?.phone)

                }


            }

            mRecyclerView.adapter = FirebaseRecyclerAdapter

        }
    }



}
