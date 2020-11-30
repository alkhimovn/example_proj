package com.example.job

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity: AppCompatActivity(), BaseFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
    }

    protected var fragment: BaseFragment? = null
    protected var title : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resources?.configuration?.let { adjustFontScale(it) }
        setContentView(R.layout.activity_base)
    }

    protected fun setCloseBtn(){
        findViewById<ImageView>(R.id.iv_back).setImageResource(R.drawable.close_ic)
    }

    override fun onResume() {
        super.onResume()
        findViewById<ImageView>(R.id.iv_back)?.setOnClickListener { onBackPressed() }
        findViewById<TextView>(R.id.base_title)?.setText(title)

        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        if (fragment != null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_base, fragment!!)
                .commitNow()
        }
    }

    protected fun setBaseFragment(frg: BaseFragment){
        fragment = frg
        if (fragment != null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_base, fragment!!)
                .commitNow()
        }
    }

    fun showWarning(message: CharSequence){
        baseContext.toast(message)
    }

    fun Context.toast(message: CharSequence) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        val view = toast.view.findViewById<TextView>(android.R.id.message)
        view?.let {
            view.gravity = Gravity.CENTER
        }
        toast.show()
    }

    open fun adjustFontScale(configuration: Configuration) {
        configuration.fontScale = 1.0.toFloat()
        val metrics = resources.displayMetrics
        val wm =
            getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)
    }
}