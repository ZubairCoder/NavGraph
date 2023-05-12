package com.example.navgraph

import android.os.Bundle
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.navgraph.databinding.FragmentSecondBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SecondFragment : Fragment() {
    private  var binding : FragmentSecondBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSecondBinding.inflate(LayoutInflater.from(context),container,false)
        //val name = arguments?.getString("name")
        var email = arguments?.getString("email")

        GlobalScope.launch {
            val data = AppDatabase(requireContext()).getDataDao().getOneData(email ?: return@launch)
            setData(data)
        }
        //binding?.UserName?.text = name.toString()
        binding?.txtEmail?.text = email.toString()
        Toast.makeText(requireContext(),"$email",Toast.LENGTH_SHORT).show()


        binding?.imagProfile?.setOnClickListener {
            GlobalScope.launch {
                val data = AppDatabase(requireContext()).getDataDao().getOneData(email?:return@launch)
                display(data)
            }
        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    private suspend fun setData(data: DataModel) {
        withContext(Dispatchers.Main){
            val UserName = data.Fname +" " +data.Lname
            val UserEmail = data.Email

            binding?.UserName?.setText(UserName)
            binding?.txtEmail?.setText(UserEmail)
        }

    }

    private suspend fun display(data: DataModel) {

        withContext(Dispatchers.Main) {
            val bundle = Bundle()
            //bundle.putString("name", data.Fname)
           // bundle.putString("bankacc", data.BankAcc.toString())
            bundle.putString("email", data.Email)
           // bundle.putString("password", data.Password)
          //  bundle.putString("phoneno", data.PhoneNo.toString())
           // bundle.putString("address", data.Address)
          //  bundle.putString("creditcard", data.CreditNo.toString())
            findNavController().navigate(R.id.settingFragment, bundle)
        }

    }

}