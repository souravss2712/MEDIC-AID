package `in`.codepredators.vedanta.models

import `in`.codepredators.vedanta.converters.StringTypeConverter
import androidx.room.*


@Entity(tableName = "prescription")
class Prescription {
    @PrimaryKey(autoGenerate = true)
    var prescription_key:Int=0
    @ColumnInfo(name= "PRESCRIPTION_ID")
    lateinit var id: String
    @ColumnInfo(name = "PRESCRIPTION_NAME")
    var name: String=""
    @ColumnInfo(name = "PATIENT_NAME")
    var patientName: String=""
    @ColumnInfo(name = "PATIENT_SEX")
    var patientSex: String=""
    @ColumnInfo(name = "PATIENT_AGE")
    var patientAge: String=""
    @ColumnInfo(name = "PATIENT_ID")
    var patientId: String=""
    @ColumnInfo(name = "DOCTORID")
    var doctorId: String=""
    @ColumnInfo(name = "DATE")
    var date: Long = 0
    @ColumnInfo(name="SIGNATURE")
    var signature: String = ""
    @ColumnInfo(name = "SYMPTOMS")
    @TypeConverters(StringTypeConverter::class)
    lateinit var symptoms: List<String>
    @ColumnInfo(name = "DIAGNOSIS")
    @TypeConverters(StringTypeConverter::class)
    lateinit var diagnosis: List<String>
    @ColumnInfo(name = "PRESCRIPTIONS")
    @TypeConverters(StringTypeConverter::class)
    lateinit var prescription: List<String>
    @ColumnInfo(name = "ADVICES")
    @TypeConverters(StringTypeConverter::class)
    lateinit var advice: List<String>
    @ColumnInfo(name = "REMARKS")
    @TypeConverters(StringTypeConverter::class)
    lateinit var remarks: List<String>
}