package `in`.codepredators.vedanta.models

import `in`.codepredators.vedanta.converters.DataTypeConverter
import `in`.codepredators.vedanta.converters.DoubleTypeConverters
import `in`.codepredators.vedanta.converters.StringTypeConverter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "doctor")
class Doctor {
    @PrimaryKey
    @ColumnInfo(name = "DOCTOR_ID")
    var id: String = ""

    @ColumnInfo(name = "REG_NUMBER")
    var regNumber: String=""

    @ColumnInfo(name = "EMAIL_ADDRESS")
    var email: String=""

    @ColumnInfo(name = "CITY")
    var city: String=""

    @ColumnInfo(name = "DOCTOR_NAME")
    var name: String = ""

    @ColumnInfo(name = "DOCTOR_JOB")
    var job: String = ""

    @ColumnInfo(name = "DOCTOR_PRESCRIPTIONS")
    @TypeConverters(StringTypeConverter::class)
    var prescriptions: List<String> = ArrayList()

    @ColumnInfo(name = "DOCTOR_PATIENTS")
    @TypeConverters(StringTypeConverter::class)
    var patients: List<String> = ArrayList()

    @ColumnInfo(name = "WORKING_HOURS")
    @TypeConverters(DoubleTypeConverters::class)
    var workingHours: List<Double> = ArrayList()

    @ColumnInfo(name = "SPECIALIZATION")
    var specialization: String = ""

    @ColumnInfo(name = "DEGREE")
    var degree: String = ""

    @ColumnInfo(name = "CLINIC_ADDRESS")
    var clinicAddress: String = ""

    @ColumnInfo(name = "CLINIC_NAME")
    var clinicName: String = ""

    @ColumnInfo(name = "DOB")
    var dateOfBirth: String = ""

    @ColumnInfo(name="SEX")
    var sex: String ="\u0000"

    @ColumnInfo(name = "APPOINTMENTS")
    @TypeConverters(StringTypeConverter::class)
    var appointments: List<String> = ArrayList()  //Patient ID

    @ColumnInfo(name = "PREFFERED_MEDICINE")
    @TypeConverters(DataTypeConverter::class)
    var preferredMedicines: List<Medicine> = ArrayList()
}