package com.example.agriguard.modules.main.complain.service

import com.example.agriguard.modules.main.complain.mapper.toDTO
import com.example.agriguard.modules.main.complain.mapper.toEntity
import com.example.agriguard.modules.main.complain.model.dto.ComplaintInsuranceDto
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import javax.inject.Inject

class ComplaintService @Inject constructor(private val realm: Realm) {

//    suspend fun saveComplainForm(data: ComplaintInsuranceDto,  imageUri: ByteArray?): Result<ComplaintInsuranceDto> {
//        return try {
//            realm.write {
//                val complain = query<ComplaintInsurance>("userId == $0", data).find().firstOrNull()
//                if (complain != null && imageUri != null) {
//                    val base64Image =
//                        android.util.Base64.encodeToString(imageUri, android.util.Base64.DEFAULT)
//                    complain.imageBase64 = base64Image
//                    copyToRealm(complain, UpdatePolicy.ALL)
//                    Result.success(complain.toDTO())
//                } else {
//                    Result.failure(Exception())
//                }
//            }
//        } catch (error: Exception) {
//            Result.failure(error)
//        }
//    }

    suspend fun upsertComplaint(dates: ComplaintInsuranceDto): Result<ComplaintInsuranceDto> {
        return try {
            realm.write {
                val userComplaint = copyToRealm(dates.toEntity(), updatePolicy = UpdatePolicy.ALL)
                Result.success(userComplaint.toDTO())
            }
        } catch (error: Exception) {
            Result.failure(error)
        }
    }


}