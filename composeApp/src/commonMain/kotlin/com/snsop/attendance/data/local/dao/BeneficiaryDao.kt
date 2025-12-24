package com.snsop.attendance.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.snsop.attendance.data.local.entities.BeneficiaryEntity
import com.snsop.attendance.data.local.entities.BomaEntity

@Dao
interface BeneficiaryDao {
    @Upsert
    suspend fun upsertBoma(bomaEntity: BomaEntity)

    @Upsert
    suspend fun upsertBeneficiaries(beneficiaries: List<BeneficiaryEntity>)

    @Query("SELECT * FROM beneficiary WHERE householdNumber = :householdNumber")
    suspend fun getBeneficiary(householdNumber: String): BeneficiaryEntity?

    @Query("UPDATE boma SET lastUpdated = :lastUpdated, totalBeneficiary = :totalBeneficiary WHERE id = :bomaId")
    suspend fun updateBomaInfo(bomaId: Int, lastUpdated: Long, totalBeneficiary: Long): Int

    @Query("SELECT * FROM boma WHERE id = :bomaId")
    suspend fun getBomaById(bomaId: Int): BomaEntity?

    @Query("SELECT * FROM boma")
    suspend fun getBomaList(): List<BomaEntity>

    @Query("SELECT * FROM beneficiary WHERE bomaId = :bomaId")
    suspend fun getBeneficiaries(bomaId: Int): List<BeneficiaryEntity>

    @Query("SELECT COUNT(*) FROM beneficiary")
    suspend fun getBeneficiaryCount(): Int
}