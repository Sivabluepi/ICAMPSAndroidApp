package com.bluepi.icampsandroid.ui.language

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluepi.icampsandroid.databinding.ActivityLanguageBinding
import com.bluepi.icampsandroid.ui.language.adapters.LanguageCustomAdapter
import com.bluepi.icampsandroid.ui.language.listeners.ItemClickListener
import com.bluepi.icampsandroid.ui.language.models.LanguagesDataModelClass
import com.bluepi.icampsandroid.ui.tutorial.TutorialPageActivity
import com.bluepi.icampsandroid.ui.utils.AppUtil
import com.google.gson.Gson

class LanguagePageActivity : AppCompatActivity(), ItemClickListener {
    private var adapter: LanguageCustomAdapter? = null
    private lateinit var binding: ActivityLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupRecyclerView()

        binding.continueButton.setOnClickListener{
            val intent = Intent(this, TutorialPageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupRecyclerView() {

        // this creates a vertical layout Manager
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val languagesListModel = Gson().fromJson(AppUtil.getJsonFromAsset(this, "languages_list.json"), LanguagesDataModelClass::class.java)
        if(languagesListModel != null){
            val dataList = languagesListModel.languages
            // This will pass the ArrayList to our Adapter
            adapter = LanguageCustomAdapter(dataList, this)
            // Setting the Adapter with the recyclerview
            binding.recyclerview.adapter = adapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(responseItem: Any) {
        // Notify adapter
        binding.recyclerview.post { adapter?.notifyDataSetChanged() }
    }
}