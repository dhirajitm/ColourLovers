package com.app.colourlovers.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.colourlovers.R
import com.app.colourlovers.data.ColourResponseItem
import com.app.colourlovers.data.MainRepository
import com.app.colourlovers.data.MainViewModel
import com.app.colourlovers.data.MyViewModelFactory
import com.app.colourlovers.databinding.ActivityMainBinding
import com.app.colourlovers.retrofit.RetrofitService

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    private val TAG = "MainActivity"
    var adapter: ColorAdapter? = null
    var colourList = ArrayList<ColourResponseItem>()

    private val sharedPrefColour = "sharedPrefColour"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        // val view = mBinding.root
        setContentView(mBinding.root)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefColour, Context.MODE_PRIVATE)

        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(
            MainViewModel::class.java
        )

        viewModel.masterData.observe(this, Observer {
            var cr: ArrayList<ColourResponseItem> = it;
            Log.d(TAG, "onCreate: " + cr.size)
            colourList = cr
            for (item: ColourResponseItem in cr) {
                if (sharedPreferences.contains(item.hex)) {
                    item.liked = true
                }
            }
            adapter = ColorAdapter(this, colourList)
            mBinding.colourTilesRv.adapter = adapter
            mBinding.loadingBar.visibility = View.GONE
        })

        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "onCreate: ERROR")
            Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
            mBinding.loadingBar.visibility = View.GONE
        })

        mBinding.searchBtn.setOnClickListener {
            if (mBinding.searchEt.text.toString().length < 3) {
                Toast.makeText(this, getString(R.string.min_3_char), Toast.LENGTH_SHORT).show()
            } else {
                viewModel.getAllColors(mBinding.searchEt.text.toString(), "json", 20, 0);
                closeKeyBoard(mBinding.searchBtn)
                mBinding.loadingBar.visibility = View.VISIBLE
            }
        }
    }

    private fun closeKeyBoard(view: View?) {
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}