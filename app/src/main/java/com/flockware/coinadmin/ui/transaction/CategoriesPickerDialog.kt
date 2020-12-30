package com.flockware.coinadmin.ui.transaction

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.flockware.coinadmin.data.models.Category
import com.flockware.coinadmin.databinding.DialogCategoriesPickerBinding
import com.flockware.coinadmin.ui.transaction.controllers.CategoriesPickerController

class CategoriesPickerDialog(context: Context, val categoriesList: List<Category>) : Dialog(context) {

    private lateinit var binding: DialogCategoriesPickerBinding
    private lateinit var controller: CategoriesPickerController

    var onAddCategory: () -> Unit = {}
    var onSelectedCategory: (Category) -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogCategoriesPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)

        window!!.apply {
            setLayout(size.x, -1)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setDimAmount(0.7f)
//            attributes.windowAnimations = R.style.WSDialogAnimation
        }

        binding.dcpBack.setOnClickListener { this.dismiss() }

        controller = CategoriesPickerController(this.categoriesList)
        binding.dcpRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CategoriesPickerDialog.context)
            adapter = controller.adapter
        }
        controller.onAddClick = {
            onAddCategory()
            this.dismiss()
        }
        controller.onCategoryClick = {
            onSelectedCategory(it)
            this.dismiss()
        }
        controller.requestModelBuild()
    }

}