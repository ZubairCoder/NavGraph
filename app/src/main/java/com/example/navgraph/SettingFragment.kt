package com.example.navgraph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.navgraph.databinding.FragmentSettingBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingFragment : Fragment() {
    private var binding: FragmentSettingBinding? = null
    private var email: String? = null
    private var data: DataModel? = null
    private var id: Int? = null
    private var lastname: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSettingBinding.inflate(LayoutInflater.from(context), container, false)
        // val name = arguments?.getString("name")
        email = arguments?.getString("email")

        GlobalScope.launch {
            val data = AppDatabase(requireContext()).getDataDao().getOneData(email ?: return@launch)
            setData(data)
        }

        /*val bankacc = arguments?.getString("bankacc")
        val password = arguments?.getString("password")
        val phoneno = arguments?.getString("phoneno")
        val address = arguments?.getString("address")
        val creditcard = arguments?.getString("creditcard")
        binding?.UserName?.setText(name.toString())
        binding?.txtEmail?.setText(email.toString())
        binding?.txtEmail?.keyListener = null
        binding?.txtAccountNumber?.setText(bankacc.toString())
        binding?.txtPassword?.setText(password.toString())
        binding?.txtPhoneNumber?.setText(phoneno.toString())
        binding?.txtAddress?.setText(address.toString())
        binding?.txtCraditCard?.setText(creditcard.toString())*/

        binding?.btnSave?.setOnClickListener {
            Update()
            Toast.makeText(context, "Data is Updated", Toast.LENGTH_SHORT).show()
        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    private suspend fun setData(data: DataModel) {
        withContext(Dispatchers.Main) {
            val name = data.Fname.toString()
            val bankAcc = data.BankAcc.toString()
            val password = data.Password.toString()
            val email = data.Email.toString()
            val phoneNo = data.PhoneNo.toString()
            val address = data.Address.toString()
            val creditCard = data.CreditNo.toString()
            id = data.Id
            lastname = data.Lname

            binding?.UserName?.setText(name)
            binding?.txtAccountNumber?.setText(bankAcc)
            binding?.txtPassword?.setText(password)
            binding?.txtEmail?.setText(email)
            binding?.txtPhoneNumber?.setText(phoneNo)
            binding?.txtAddress?.setText(address)
            binding?.txtCraditCard?.setText(creditCard)
        }

    }


    private fun Update() {
        val f_name = binding?.UserName?.text.toString()
        val bankAcc = binding?.txtAccountNumber?.text.toString()
        val _password = binding?.txtPassword?.text.toString()
        val phone_no = binding?.txtPhoneNumber?.text.toString()
        val address = binding?.txtAddress?.text.toString()
        val creditcard = binding?.txtCraditCard?.text.toString()

        GlobalScope.launch {
            val dataModel = DataModel(
                id,
                f_name,
                lastname,
                email ?: return@launch,
                _password,
                bankAcc.toInt(),
                phone_no.toLong(),
                address,
                creditcard.toLong()
            )
            AppDatabase(requireContext()).getDataDao().updateData(dataModel)
        }
    }


}