package com.inigofrabasa.supermarvel.views

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.inigofrabasa.supermarvel.R
import com.inigofrabasa.supermarvel.databinding.FragmentSuperheroDetailBinding
import com.inigofrabasa.supermarvel.viewmodels.SuperheroeDetailViewModel
import java.util.*
import kotlin.concurrent.schedule

class SuperheroeDetailFragment : Fragment(){
    private lateinit var binding: FragmentSuperheroDetailBinding

    private val viewModel: SuperheroeDetailViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(SuperheroeDetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSuperheroDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSuperheroe()
        subscribeUi()

        val content = view.findViewById<View>(R.id.content).also { it.alpha = 0f }
        ObjectAnimator.ofFloat(content, View.ALPHA, 0f, 1f).apply {
            startDelay = 50
            duration = 150
            start()
        }

        Timer().schedule(300L){
            activity!!.runOnUiThread {
                val marginTop = ValueAnimator.ofInt(0, 22)
                marginTop.duration = 200
                marginTop.addUpdateListener { animation ->
                    val lp = content.layoutParams as RelativeLayout.LayoutParams
                    lp.setMargins(0, animation.animatedValue as Int, 0, animation.animatedValue as Int)
                    content.layoutParams = lp
                }
                marginTop.start()
            }
        }
    }

    private fun subscribeUi() {
        viewModel.superheroe.observe(this, androidx.lifecycle.Observer {
            binding.superheroe = it
        })
    }
}