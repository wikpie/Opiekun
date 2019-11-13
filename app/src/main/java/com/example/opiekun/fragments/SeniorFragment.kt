package com.example.opiekun.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opiekun.*
import com.example.opiekun.R
import com.google.firebase.database.*
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_senior.*


class SeniorFragment : Fragment() {
    val seniorNames=ArrayList<String>()
    val seniorImages=ArrayList<Image>()
    val seniorIds=ArrayList<String>()
    var seniorCount:Long=0
    private var uid=" "
    private var model: Communicator?=null
    private lateinit var ref: DatabaseReference
    private lateinit var recycler:RecyclerView
    private lateinit var adapterSenior: SeniorAdapter
    private val ref1= FirebaseDatabase.getInstance().getReference("seniors")
    companion object {

        @JvmStatic
        fun newInstance(uid: String) = SeniorFragment().apply {
            arguments = Bundle().apply {
                putString("REPLACE WITH A STRING CONSTANT", uid)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("REPLACE WITH A STRING CONSTANT")?.let {
            uid = it
            Log.d("careid",uid)
            ref= FirebaseDatabase.getInstance().getReference("caregivers/$uid/seniorsIds")
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView=inflater.inflate(R.layout.fragment_senior, container, false)
        recycler = rootView.findViewById(R.id.recycling_senior) as RecyclerView
        recycler.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            adapterSenior=SeniorAdapter()
            adapter = adapterSenior
            (adapter as SeniorAdapter).onItemClick = {

                model!!.setMsgCommunicator(it.id)
                Log.d("pizdanad", it.id)
            }
        }
        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model= ViewModelProviders.of(activity!!).get(Communicator::class.java)
        model!!.setMsgCommunicator(" ")
        add_button.setOnClickListener{
            val seniorID=java.util.UUID.randomUUID().toString()
            val builder1: AlertDialog.Builder = AlertDialog.Builder(context)
            builder1.setMessage("Wygenerowano id: $seniorID . Zapisz je i podaj seniorowi żeby podał je przy rejestracji")
            builder1.setCancelable(true)

            builder1.setPositiveButton("Dodaj") { dialog, _ ->
                val nextSenior=seniorCount+1
                ref.child("$nextSenior").setValue(seniorID)
                dialog.cancel()
            }

            builder1.setNegativeButton("Wstecz") { dialog, _ ->
                dialog.cancel()
            }

            val alert11: AlertDialog = builder1.create()
            alert11.show()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
            val postListener1 = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for(i in seniorIds){
                        val seniorName=dataSnapshot.child("$i/imie").value.toString()
                        seniorNames.add(seniorName)
                        Log.d("seniorname", seniorName)
                    }
                    addDataSet()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("Main", "loadPost:onCancelled", databaseError.toException()!!)
                }
            }
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //getEmails(dataSnapshot.value as Map<String, Any>)
                    seniorCount=dataSnapshot.childrenCount
                    if(seniorCount.toInt()==0){
                        toast("Nie masz żadnego dodanego seniora")
                    }
                    if(seniorCount>0){
                        for(snap in dataSnapshot.children){
                            val id=snap.value.toString()
                            seniorIds.add(id)
                            Log.d("seniorid", id)

                        }
                        model!!.setMsgCommunicator(seniorIds[0])
                    }
                    ref1.addValueEventListener(postListener1)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("Main", "loadPost:onCancelled", databaseError.toException()!!)
                }
            }
            ref.addValueEventListener(postListener)
        }
    private fun addDataSet(){
        val list = ArrayList<Senior>()
        for (i in 0 until seniorCount ){

        list.add(
            Senior(
                seniorNames[i.toInt()],
                "https://media.istockphoto.com/photos/portrait-of-smiling-handsome-man-in-blue-tshirt-standing-with-crossed-picture-id1045886560?k=6&m=1045886560&s=612x612&w=0&h=hXrxai1QKrfdqWdORI4TZ-M0ceCVakt4o6532vHaS3I=",
                seniorIds[i.toInt()]
            )
        )
            Log.d("seniuuur",seniorNames[i.toInt()])
        }

        adapterSenior.submitList(list)
        adapterSenior.notifyDataSetChanged()
    }


    }

