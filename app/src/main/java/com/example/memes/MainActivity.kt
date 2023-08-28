package com.example.memes

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {
    lateinit var memeImageView: ImageView
    lateinit var progressBar: ProgressBar
    var currentImageurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Background color of title bar
        val colorDrawable = ColorDrawable(Color.parseColor("#FB6F92"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)


        memeImageView = findViewById(R.id.memeImage)
        progressBar = findViewById(R.id.progbar)
        loadmeme()
    }
    private fun loadmeme(){
        progressBar.visibility=View.VISIBLE
        val url = "https://meme-api.com/gimme"
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentImageurl=response.getString("url")
//                Glide.with(this).load(Url).into(memeImageView)
                Glide.with(this).load(currentImageurl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }
                }).into(memeImageView)

            },
            { error ->
                Toast.makeText(this,"Something went wrong ..",Toast.LENGTH_LONG).show()
            }
        )

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest)
    }
    fun nextmeme(view: View){
        loadmeme()
    }

    fun sharememe(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, checkout this cool meme I got from Reddit $currentImageurl")
        val chooser=Intent.createChooser(intent,"Share this meme using....")
        startActivity(chooser)
    }
}