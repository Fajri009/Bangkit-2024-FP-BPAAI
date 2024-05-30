package com.example.bangkit_2024_fp_bpaai.utils

import android.content.*
import android.graphics.*
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import com.example.bangkit_2024_fp_bpaai.BuildConfig
import com.example.bangkit_2024_fp_bpaai.data.remote.response.ListStoryItem
import java.io.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

fun isValidEmail(email: CharSequence): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun String.withDateFormat(): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val date = format.parse(this) as Date

    val outputFormat = SimpleDateFormat("dd - MMMM - yyyy", Locale.getDefault())
    return outputFormat.format(date)
}

fun getImageUri(context: Context): Uri {
    var uri: Uri? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MyCamera/")
        }
        uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }
    return uri ?: getImageUriForPreQ(context)
}

private fun getImageUriForPreQ(context: Context): Uri {
    val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File(filesDir, "/MyCamera/$timeStamp.jpg")
    if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()
    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.fileprovider",
        imageFile
    )
}

fun uriToFile(imageUri: Uri, context: Context): File {
    val myFile = createCustomTempFile(context)
    val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buffer = ByteArray(1024)
    var length: Int
    while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
    outputStream.close()
    inputStream.close()
    return myFile
}

fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
}

fun convertListStoryToBitmap(context: Context, listStory: List<ListStoryItem?>?): List<Bitmap> {
    val bitmaps = mutableListOf<Bitmap>()
    for (storyItem in listStory!!) {
        val photoUrl = storyItem!!.photoUrl
        if (photoUrl != null) {
            try {
                val url = URL(photoUrl)
                val connection = url.openConnection()
                connection.doInput = true
                connection.connect()
                val inputStream = connection.getInputStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                bitmaps.add(bitmap)
                inputStream.close()
            } catch (e: IOException) {
                Log.e("Utils", "Error downloading bitmap from URL: $photoUrl", e)
            }
        }
    }
    return bitmaps
}