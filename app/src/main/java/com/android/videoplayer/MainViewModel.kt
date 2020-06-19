package com.android.videoplayer

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class MainViewModel : ViewModel() {

    var mediaObjects: ArrayList<File> = arrayListOf()
    val videoList = MutableLiveData<ArrayList<File>>()

    fun getVideoList(){
        searchVideos(Environment.getExternalStorageDirectory())
        videoList.postValue(mediaObjects)
    }

    private fun searchVideos(dir: File) {
        val pattern = ".mp4"
        //Get the listfile of that flder
        val listFile = dir.listFiles()
        if (listFile != null) {
            for (i in listFile.indices) {

                if (listFile[i].isDirectory) {
                    searchVideos(listFile[i])
                } else {
                    if (listFile[i].name.endsWith(pattern)) {
                        // Do what ever u want, add the path of the video to the list
                        mediaObjects.add(listFile[i])
                    }
                }
            }
        }
    }


}