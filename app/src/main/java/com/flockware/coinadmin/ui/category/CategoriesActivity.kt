package com.flockware.coinadmin.ui.category

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.flockware.coinadmin.R
import com.flockware.coinadmin.data.AppResult
import com.flockware.coinadmin.data.models.Category
import com.flockware.coinadmin.data.repositories.CategoryIsUsedException
import com.flockware.coinadmin.databinding.ActivityCategoriesBinding
import com.flockware.coinadmin.ui.category.controllers.CategoriesController
import com.flockware.coinadmin.ui.dialogs.BottomSheetMenu
import com.flockware.coinadmin.ui.transaction.AddTransactionActivity
import com.flockware.coinadmin.utils.ColorUtils
import com.flockware.coinadmin.utils.LoggerCompat
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.getViewModel

class CategoriesActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesBinding
    private lateinit var viewModel: CategoriesViewModel
    private lateinit var controller: CategoriesController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel()

        initController()
        setOnClickListeners()

        observeData()

        viewModel.loadCategories()
    }

    private fun initController() {
        controller = CategoriesController(viewModel.categoriesList)

        binding.acRecyclerView.apply {
            layoutManager = GridLayoutManager(this@CategoriesActivity, 2)
            adapter = controller.adapter
            setHasFixedSize(false)
        }

        controller.onCategoryClick = { category ->
            showOptions(category)
        }
    }

    private fun setOnClickListeners() {
        binding.acTopBar.onLeftIconClick = { this.finish() }
        binding.acTopBar.onRightIconClick = {
            val intent = Intent(this, AddCategoryActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun observeData() {
        viewModel.categoriesUpdated.observe(this) { updated ->
            LoggerCompat.log("updateted: $updated")
            if (updated == true)
                controller.requestModelBuild()
        }

        viewModel.categoriesLiveData.observe(this) {
            viewModel.loadCategories()
        }

        viewModel.categoryDeleted.observe(this) { result ->
            when (result) {
                is AppResult.Success -> {
                    Snackbar.make(binding.root, "Categoria eliminata", Snackbar.LENGTH_SHORT).show()
                }
                is AppResult.Error -> {
                    when (result.exception) {
                        is CategoryIsUsedException -> {
                            Snackbar.make(binding.root, "La categoria è utilizzata. Non puoi eliminarla.", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun showOptions(category: Category) {
        BottomSheetMenu.Builder(this)
            .addMenuOption(resources.getString(R.string.edit), ColorUtils.getColorContent(this)) {
                val intent = Intent(this, AddCategoryActivity::class.java)
                intent.putExtra("edit_category_id", category.id)
                this.startActivity(intent)
            }
            .addMenuOption(resources.getString(R.string.delete), ColorUtils.getColorContent(this)) { viewModel.deleteCategory(category) }
            .addMenuOption(resources.getString(R.string.close), ColorUtils.getColorContentSoft(this))
            .build()
            .show()

    }
}