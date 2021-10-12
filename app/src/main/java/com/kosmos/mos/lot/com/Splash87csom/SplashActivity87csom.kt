package com.kosmos.mos.lot.com.Splash87csom

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kosmos.mos.lot.com.WebActivity87csom.WebActivity87csom
import com.kosmos.mos.lot.com.databinding.ActivityMain87csomBinding
import kotlinx.coroutines.launch

class SplashActivity87csom : AppCompatActivity(), SplashContract87csom.SplashMethods87csom {

    private lateinit var binding87csom: ActivityMain87csomBinding
    private lateinit var splashPresenterMethods87csom: SplashContract87csom.SplashPresenter87csom
    private var animator87csom: ValueAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSharedPreferences("SP_87csom", MODE_PRIVATE).getString("Last_Page_87csom", null)?.let {
            startActivity(Intent(this, WebActivity87csom::class.java))
            finish()
        }
        binding87csom = ActivityMain87csomBinding.inflate(layoutInflater)
        setContentView(binding87csom.root)
        splashPresenterMethods87csom = SplashPresenter_Impl87csom(this)
        splashPresenterMethods87csom.setupFB87csom()
        splashPresenterMethods87csom.thisEndsHere87csom()
    }

    override fun onCLick87csom(clickScope87csom: () -> Unit) {
        binding87csom.b87csom.setOnClickListener {
            lifecycleScope.launch {
                animation87csom()
                binding87csom.b87csom.isClickable = false
                clickScope87csom()
            }
        }
    }

    override fun onStop() {
        animator87csom?.cancel()
        super.onStop()
    }

    private fun animation87csom () {
        animator87csom = ValueAnimator.ofInt(0,3).apply {
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                var s = "Загрузка"
                for (i in 0..it.animatedValue as Int) {
                    s += "."
                }
                binding87csom.b87csom.text = s
            }
            duration = 500
            start()
        }

        binding87csom.pb87csom.animate().alpha(1f)
    }

    override fun startNewActivity87csom(url87csom: String) {
        startActivity(Intent(this, WebActivity87csom::class.java).apply { putExtra("webURL87csom", url87csom) })
        finish()
    }

    override fun getPackageName87csom(): String = packageName
}