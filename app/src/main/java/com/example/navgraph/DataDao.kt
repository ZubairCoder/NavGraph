package com.example.navgraph

import androidx.room.*

@Dao
interface DataDao {
    @Insert
    fun  dataInsert(dataModel: DataModel)

    @Query("SELECT * FROM user_table")
    suspend fun getAllData() : List<DataModel>

    @Query("SELECT * FROM user_table where Email like :useremail limit 1")
    fun getOneData(useremail : String) : DataModel

    @Update
    fun updateData(dataModel: DataModel)

    @Delete
    fun deleteData(dataModel: DataModel)
}