package com.bluepi.icampsandroid.ui.tutorial

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bluepi.icampsandroid.databinding.ActivityTutorialBinding
import com.bluepi.icampsandroid.ui.tutorial.adapters.TutorialPagesAdapter
import com.bluepi.icampsandroid.ui.tutorial.models.TutorialPagesModelClass
import com.bluepi.icampsandroid.ui.utils.AppUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class TutorialPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTutorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        initViews()
    }

    private fun initViews() {
        // ArrayList of class ItemsViewModel
        val tutorialPagesModelClass = Gson().fromJson(
            AppUtil.getJsonFromAsset(this, "tutorialpage_list.json"),
            TutorialPagesModelClass::class.java
        )
        if (tutorialPagesModelClass != null) {
            val dataList = tutorialPagesModelClass.tutorial_pages_list_data
            binding.photosViewpager.adapter = TutorialPagesAdapter(this, dataList)
            TabLayoutMediator(binding.tabLayout, binding.photosViewpager) { tab, position ->
                //code here.
            }.attach()
            binding.photosViewpager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val positionPage = position + 1
                    /*Snackbar.make(
                        binding.root,
                        "Position::>> " + (position + 1),
                        Snackbar.LENGTH_SHORT
                    ).show()*/

                    if(positionPage == 4){

                    }
                }
            })
        }
    }
}