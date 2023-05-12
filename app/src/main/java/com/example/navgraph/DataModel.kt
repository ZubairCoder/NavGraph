package com.example.navgraph

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_table")
data class DataModel(@PrimaryKey(autoGenerate = true)var Id : Int?=null,
                     var Fname : String,
                     var Lname : String?,
                     var Email : String,
                     var Password : String,
                     var BankAcc : Int =12345,
                     var PhoneNo : Long = 923001234567,
                     var Address : String = "Address",
                     var CreditNo : Long = 12345678,

)
