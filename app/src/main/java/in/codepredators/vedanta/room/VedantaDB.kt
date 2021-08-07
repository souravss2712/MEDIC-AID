package `in`.codepredators.vedanta.room

import `in`.codepredators.vedanta.models.Doctor
import `in`.codepredators.vedanta.models.Medicine
import `in`.codepredators.vedanta.models.Patient
import `in`.codepredators.vedanta.models.Prescription
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [((Patient :: class)), ((Prescription :: class)), ((Medicine :: class)), ((Doctor :: class))],version = 10)
abstract class VedantaDB : RoomDatabase() {
    abstract fun VedantaDAO() : VedantaDAO
}