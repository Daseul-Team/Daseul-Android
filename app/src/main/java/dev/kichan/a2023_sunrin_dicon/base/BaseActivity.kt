package dev.kichan.a2023_sunrin_dicon.base

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<D : ViewDataBinding>(
    @LayoutRes
    private val layoutResId : Int
) : AppCompatActivity() {
    protected lateinit var binding : D

    protected fun hideKeyboard(){
        val view = currentFocus
        if(view != null){
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this

        initView()
    }

    abstract fun initView()

    protected fun showToast(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}