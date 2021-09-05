package com.fintech.developerslife

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.bumptech.glide.request.target.Target

const val ERROR_WITH_LOADING_DATA = "Произошла ошибка при загрузке данных. Проверьте подключение к сети."

class MainActivity : AppCompatActivity() {
    private lateinit var imageViewForGif: ImageView
    private lateinit var textViewForGifInfo: TextView
    private lateinit var buttonNext: Button
    private lateinit var buttonBack: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonForLoadAgain: Button

    private val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myViewModel.currentGif.observe(this, Observer { showCurrentGif(it) })
        myViewModel.isBackButtonClickable.observe(this, Observer { hideOrShowBackButton(it) })

        imageViewForGif = findViewById(R.id.image_view_for_gif)
        textViewForGifInfo = findViewById(R.id.text_view_for_gif_info)
        buttonNext = findViewById(R.id.button_next)
        buttonBack = findViewById(R.id.button_back)
        progressBar = findViewById(R.id.progress_bar_for_gif)
        buttonForLoadAgain = findViewById(R.id.button_for_load_again)

        buttonBack.isClickable = false
        progressBar.isVisible = true
        buttonForLoadAgain.isVisible = false

        if (checkInternet()) {
            myViewModel.showNextGif()
        }
        else {
            showDataWhenErrorOccured()
        }

        buttonNext.setOnClickListener {
            progressBar.isVisible = true
            myViewModel.showNextGif()
        }

        buttonBack.setOnClickListener {
            progressBar.isVisible = true
            myViewModel.showPrevGif()
        }

        buttonForLoadAgain.setOnClickListener {
            if (checkInternet()) {
                textViewForGifInfo.text = ""
                buttonForLoadAgain.isVisible = false
                buttonNext.isVisible = true
                buttonBack.isVisible = true
                progressBar.isVisible = true
                myViewModel.showNextGif()
            }
            else {
                showDataWhenErrorOccured()
            }
        }
    }

    fun showCurrentGif(currentGif:ApiResult) {
        Glide.with(this).load(currentGif.gifURL).listener(object : RequestListener<String, GlideDrawable> {
            override fun onResourceReady(
                resource: GlideDrawable?,
                model: String?,
                target: Target<GlideDrawable>?,
                isFromMemoryCache: Boolean,
                isFirstResource: Boolean,
            ): Boolean {
                progressBar.setVisibility(View.GONE)
                return false
            }

            override fun onException(
                e: java.lang.Exception?,
                model: String?,
                target: Target<GlideDrawable>?,
                isFirstResource: Boolean,
            ): Boolean { return false }
        }).into(GlideDrawableImageViewTarget(imageViewForGif))
        textViewForGifInfo.text = currentGif.description
    }

    fun hideOrShowBackButton(isClickable: Boolean) {
        buttonBack.isClickable = isClickable
    }

    fun checkInternet(): Boolean {
        val cm: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiInfo: NetworkInfo? = cm.getActiveNetworkInfo()
        if (wifiInfo != null && wifiInfo.isConnectedOrConnecting()) {
            return true
        }
        return false
    }


    fun showDataWhenErrorOccured() {
        progressBar.isVisible = false
        textViewForGifInfo.text = ERROR_WITH_LOADING_DATA
        buttonBack.isVisible = false
        buttonNext.isVisible = false
        buttonForLoadAgain.isVisible = true
    }
}