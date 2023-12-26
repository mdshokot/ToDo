package com.shokot.todo.domain.convertors

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import android.util.Base64
import android.graphics.BitmapFactory
import androidx.room.TypeConverter

class ImageConvertor {
    @TypeConverter
    fun convertBitmapToString(bitmap: Bitmap?): String? {
        if(bitmap === null){
            return null
        }
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
        val byteArray = baos.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    @TypeConverter
    fun convertStringToBitmap(encodedString : String?) : Bitmap? {
        return try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e:Exception) {
            e.printStackTrace()
            null
        }
    }

}