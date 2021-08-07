package `in`.codepredators.vedanta.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "patient")
class Patient {
    @PrimaryKey(autoGenerate = true)
    var patient_key:Int=0
    @ColumnInfo(name = "PATIENTID")
    lateinit var id: String
    @ColumnInfo(name = "PATIENTNAME")
    lateinit var name: String
    @ColumnInfo(name = "AGE")
    lateinit var age: String
    @ColumnInfo(name = "SEX")
    var sex: Char = '\u0000'
    @Embedded
    lateinit var prescription: Prescription
    @ColumnInfo(name = "WEIGHT")
    var weight: Double = 0.0
    @ColumnInfo(name = "HEIGHT")
    var height: Double = 0.0
}