package com.example.mbm.common

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import androidx.work.Worker
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.google.gson.Gson
import com.pranrflgroup.spro.common.api.ApiHistory
import com.pranrflgroup.spro.common.api.ApiInterface
import com.pranrflgroup.spro.common.db.dao.CommonDao
import com.pranrflgroup.spro.common.db.dao.OrderDao
import com.pranrflgroup.spro.dataStore.Image
import com.pranrflgroup.spro.vanSales.model.VanTrip
import timber.log.Timber
import java.io.File

class UploadDataWorker(ctx: Context, workerParams: WorkerParameters) : Worker(ctx, workerParams) {

    companion object {
        const val TAG = "UploadDataWorker"
        const val STATUS = "status"
        lateinit var request: OneTimeWorkRequest
        lateinit var workManager: WorkManager

        val gson = Gson()
        val status = MutableLiveData<String>()

        fun start(context: Context) {
            if (status.value.equals("init")) {
                Log.e(TAG,"upload worker running")
                return
            }

            status.postValue("init")

            workManager = WorkManager.getInstance(context)
            val constraints =
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            request = OneTimeWorkRequest.Builder(UploadDataWorker::class.java)
                .setConstraints(constraints)
                .build()
            workManager.enqueue(request)
        }
    }

    override fun doWork(): Result {
        Log.e(TAG,"start worker")
        status.postValue("start")

        image()

        status.postValue("end")
        Log.e(TAG,"end worker")
        return Result.success()
    }

    private fun image() {

        try {
            val images = commonDao.getUnSyncImages()

            if (images.isNotEmpty()) {
                for (image in images) {
                    val file = File(image.path)
                    Timber.e("fileUpload: path: " + image.path)
                    if (file.exists()) {
                        uploadAWS(file, image)
                    } else {
                        Timber.e("Data not found")
                        commonDao.syncImage(image.id)
                    }
                }
            } else {
                Timber.e("fileUpload: images is empty: ")
            }

        } catch (ex: Exception) {
            Timber.e("fileUpload: ex: " + ex.localizedMessage)
        }

    }


    private fun uploadAWS(file: File, image: Image) {
        val s3: AmazonS3Client
        val credentials: BasicAWSCredentials
        val observer: TransferObserver
        val key = "NKHLZGVLLLAIV62USI5G"
        val secret = "+hznHV41sb/5vlStEUczr0FZlS57hYWnNZh4HY6SSgk"
        credentials = BasicAWSCredentials(key, secret)
        s3 = AmazonS3Client(credentials)
        s3.endpoint = "https://sgp1.digitaloceanspaces.com/"
        val transferUtility = TransferUtility(s3, applicationContext)
        val filePermission = CannedAccessControlList.PublicRead
        observer = transferUtility.upload(
            "prgfms",  //empty bucket name, included in endpoint
            image.folderName + "/" + file.name,
            file,  //a File object that you want to upload
            filePermission
        )

        observer.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (TransferState.COMPLETED == observer.state) {
                    commonDao.syncImage(image.id)
                    status.postValue("end")
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                Timber.e("fileUpload: $bytesTotal : $bytesCurrent")
            }

            override fun onError(id: Int, ex: java.lang.Exception) {
                Log.e("fileUpload", "error: " + ex.message!!)
            }
            })
    }

}