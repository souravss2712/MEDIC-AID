package `in`.codepredators.vedanta.room

import `in`.codepredators.vedanta.models.Doctor
import `in`.codepredators.vedanta.models.Medicine
import `in`.codepredators.vedanta.models.Patient
import `in`.codepredators.vedanta.models.Prescription
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface VedantaDAO {
    @Insert
    fun sav(patient : Patient)

    @Query("select * from patient")
    fun getpatient() : List<Patient>

    @Query("select * from prescription where PATIENT_NAME LIKE :name")
    fun searchPrescriptionByName(name: String) : List<Prescription>

    @Query("select * from patient where DIAGNOSIS LIKE :diagnosis")
    fun searchPrescriptionByDiagnosis(diagnosis: String) : List<Prescription>

    @Query("select * from patient where DIAGNOSIS LIKE :diagnosis AND PATIENT_NAME like :name")
    fun searchPrescriptionByDiagnosisNName(diagnosis: String, name: String) : List<Prescription>


    @Query("select * from patient where PATIENT_NAME like :name")
    fun searchPrescriptionByMedicationNName(name: String) : List<Prescription>


    @Query("select * from patient where DIAGNOSIS LIKE :diagnosis")
    fun searchPrescriptionByDiagnosisNPrescriptions(diagnosis: String) : List<Prescription>

    @Query("select * from patient where DIAGNOSIS LIKE :diagnosis AND PATIENT_NAME LIKE :name")
    fun searchPrescriptionByDiagnosisNPrescriptionsNName(diagnosis: String, name: String) : List<Prescription>

    @Insert
    fun insertpresciption(prescription : Prescription)

    @Query("select * from prescription")
    fun getPrescription() : List<Prescription>

    @Insert
    fun insertMedicine(medicine : Medicine)

    @Query("select * from medicine")
    fun getMedicine() : List<Medicine>

    @Insert
    fun insertDoctorInfo(doctor : Doctor)

    @Query("update doctor set SPECIALIZATION = :specialisation where DOCTOR_ID = :id")
    fun insertSpecialisation(specialisation: String, id: String)

    @Query("select * from doctor")
    fun getDoctors() : List<Doctor>
}