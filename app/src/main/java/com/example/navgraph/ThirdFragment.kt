package com.example.navgraph

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.navgraph.databinding.FragmentThirdBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ThirdFragment : Fragment() {
    private  var binding : FragmentThirdBinding? = null

/*    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // in here you can do logic when backPress is clicked
            }
        })
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentThirdBinding.inflate(LayoutInflater.from(context),container,false)
        // Inflate the layout for this fragment

        binding?.btnSignIn?.setOnClickListener {
            findNavController().navigate(R.id.firstFragment)
        }
        binding?.btnSignup?.setOnClickListener {
            if (binding?.txtfname?.text!!.isNotEmpty()&& binding?.txtlname?.text!!.isNotEmpty()&&
                    binding?.txtemail?.text!!.isNotEmpty()&&
                    binding?.txtpassword?.text!!.isNotEmpty()&&
                    binding?.checkBox?.isChecked == true){
                insertData()
                Toast.makeText(context, "Data Inserted", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.firstFragment)
            }
            else{
                Toast.makeText(context, "Fields are empty", Toast.LENGTH_SHORT).show()

            }
        }
        binding?.btncancel?.setOnClickListener {
            activity?.finish()
        }


        return binding?.root
    }
    @SuppressLint("SuspiciousIndentation")
    private fun insertData(){
        val f_name = binding?.txtfname?.text.toString()
        val l_name = binding?.txtlname?.text.toString()
        val _email = binding?.txtemail?.text.toString()
        val _password = binding?.txtpassword?.text.toString()

            GlobalScope.launch {
                val dataModel = DataModel(null,f_name,l_name,_email,_password)
                AppDatabase(requireContext()).getDataDao().dataInsert(dataModel)
            }
        binding?.txtfname?.text?.clear()
        binding?.txtlname?.text?.clear()
        binding?.txtemail?.text?.clear()
        binding?.txtpassword?.text?.clear()
        binding?.checkBox?.isChecked= false

        }

}