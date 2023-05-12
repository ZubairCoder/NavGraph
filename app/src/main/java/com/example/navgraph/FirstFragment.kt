package com.example.navgraph

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navgraph.databinding.FragmentFirstBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FirstFragment : Fragment() {
    private var binding: FragmentFirstBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFirstBinding.inflate(LayoutInflater.from(context), container, false)

        binding?.btnLogin?.setOnClickListener {
            if (binding?.txtEmail?.text!!.isNotEmpty() && binding?.txtPassword?.text!!.isNotEmpty()){

                //findNavController().navigate(R.id.secondFragment)
                getData()
                binding?.txtEmail?.text?.clear()
                binding?.txtPassword?.text?.clear()

            }
            else{
                Toast.makeText(context, "Please Enter Data", Toast.LENGTH_SHORT).show()
            }
            //Navigation.findNavController(requireView()).enableOnBackPressed(true)
            //Navigation.findNavController(requireView()).popBackStack()
        }


        binding?.btnSignUp?.setOnClickListener {
            findNavController().navigate(R.id.thirdFragment)
        }
        return binding?.root
    }

    @SuppressLint("SuspiciousIndentation")
    fun getData(){
        val email = binding?.txtEmail?.text?.toString()
        val password = binding?.txtPassword?.text?.toString()
        GlobalScope.launch {
            val data = AppDatabase(requireContext()).getDataDao().getOneData(email?:return@launch)
                display(data,password?:return@launch)
        }
    }

    private suspend fun display(data: DataModel,password : String) {
        withContext(Dispatchers.Main){
            if (data != null) {
                if (data.Email.isNotEmpty()) {
                    if (data.Password.equals(password)){
                        val bundle = Bundle()
                        //val bundle =  bundleOf("name" to data.Fname )
                       // bundle.putString("name",data.Fname)
                        bundle.putString("email",data.Email)
                        findNavController().navigate(R.id.secondFragment, bundle)
                    }
                    else{
                        Toast.makeText(requireContext(), "Wrong Password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(requireContext(), "Wrong Email And Password", Toast.LENGTH_SHORT).show()
            }
        }
    }


/*    override fun onDetach() {
        super.onDetach()
        findNavController().navigate(R.id.firstFragment)
    }*/

}

