package `in`.codepredators.vedanta.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "prescription")
class Appointment {
    @PrimaryKey
    var appointmentId: Int = 0
    @ColumnInfo(name = "APPOINTMENT_ID")
    lateinit var id: String
    @ColumnInfo(name = "PATIENT_NAME")
    var patientName: String = ""
    @ColumnInfo(name = "PATIENT_AGE")
    var patientAge: Double = 0.0
    @ColumnInfo(name = "PATIENT_GENDER")
    var patientGender: String = "\u0000"
    @ColumnInfo(name = "PHOTO_URL")
    var patientPhotoUrl: String = ""
    @ColumnInfo(name = "DOCTORID")
    var doctorId: String = ""
    @ColumnInfo(name = "DATE")
    var date: Long = 0
    @ColumnInfo(name = "AMOUNT_PAID")
    var amountPaid: Double = 0.0
    @ColumnInfo(name = "AMOUNT_DUE")
    var amountDue: Double = 0.0
    @ColumnInfo(name = "ADDRESS")
    var address: String = ""
    @ColumnInfo(name = "PATIENT_DISEASE")
    var patientDisease: String = ""
    @ColumnInfo(name = "TIME")
    var time: String = ""
}