package com.inigofrabasa.supermarvel.views

import android.app.Activity
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inigofrabasa.supermarvel.adapters.SuperheroesAdapter
import com.inigofrabasa.supermarvel.databinding.FragmentListSuperheroesBinding
import com.inigofrabasa.supermarvel.utils.SlideExplode
import com.inigofrabasa.supermarvel.viewmodels.SuperheroesListViewModel
import kotlinx.android.synthetic.main.fragment_list_superheroes.*

private val transitionInterpolator = FastOutSlowInInterpolator()
private const val TRANSITION_DURATION = 300L
private const val TAP_POSITION = "tap_position"

class ListSuperheroesFragment : Fragment() {

    private lateinit var binding: FragmentListSuperheroesBinding
    private lateinit var rvSuperheroeList: RecyclerView

    private var tapPosition = RecyclerView.NO_POSITION
    val viewRect = Rect()

    private val viewModel: SuperheroesListViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(SuperheroesListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListSuperheroesBinding.inflate(inflater, container, false)
        rvSuperheroeList = binding.superheroesList
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tapPosition = savedInstanceState?.getInt(TAP_POSITION, RecyclerView.NO_POSITION) ?: RecyclerView.NO_POSITION

        postponeEnterTransition()

        binding.hideDeleteSearch = true
        binding.etSearchTerm.text.clear()

        rvSuperheroeList = binding.superheroesList
        rvSuperheroeList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        binding.isLoaded = false

        val adapter = SuperheroesAdapter(
            viewRect,
            transitionInterpolator,
            TRANSITION_DURATION,
            activity as FragmentActivity,
            this@ListSuperheroesFragment)

        if(viewModel.superheroes.value == null) viewModel.getSuperheroes()
        subscribeUi(adapter)

        binding.searchButton.setOnClickListener {
            if (binding.etSearchTerm.text.toString() != ""){
                searchSuperheroe(binding.etSearchTerm.text.toString(), adapter)
            }
        }

        binding.etSearchTerm.setOnKeyListener{ _, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                searchSuperheroe(binding.etSearchTerm.text.toString(), adapter)
            }
            true
        }

        binding.deleteSearchButton.setOnClickListener {
            deleteSearch(adapter)
        }
    }

    private fun subscribeUi(adapter: SuperheroesAdapter) {
        viewModel.superheroes.observe(this, Observer { superheroes ->
            if (superheroes != null) {
                populateView(superheroes, adapter)
            }
        })
    }

    private fun populateView(superheroes : Any, adapter: SuperheroesAdapter){

        binding.superheroesList.adapter = adapter
        val allElements = ArrayList<Any>()

        allElements.apply{
            addAll(superheroes as ArrayList<Any>)
        }

        with(adapter) { submitList(allElements) }
        binding.isLoaded = true

        (view?.parent as? ViewGroup)?.doOnPreDraw {
            if (exitTransition == null) {
                exitTransition = SlideExplode().apply {
                    duration = TRANSITION_DURATION
                    interpolator = transitionInterpolator
                }
            }

            val layoutManager = superheroesList.layoutManager as LinearLayoutManager
            layoutManager.findViewByPosition(tapPosition)?.let { view ->
                view.getGlobalVisibleRect(viewRect)
                (exitTransition as Transition).epicenterCallback =
                    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                    object : Transition.EpicenterCallback() {
                        override fun onGetEpicenter(transition: Transition) = viewRect
                    }
            }

            startPostponedEnterTransition()
        }
    }

    private fun searchSuperheroe(request: String, adapter: SuperheroesAdapter) {
        hideKeyboard(activity as FragmentActivity)
        binding.hideDeleteSearch = false
        viewModel.searchSuperheroes(request)
        viewModel.searchSuperheroes.observe(this, Observer { superheroes ->
            if (superheroes != null) {
                populateView(superheroes, adapter)
            }
        })
    }

    private fun deleteSearch(adapter: SuperheroesAdapter){
        binding.hideDeleteSearch = true
        binding.etSearchTerm.text.clear()
        populateView(viewModel.superheroes.value as Any, adapter)
    }

    private fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAP_POSITION, tapPosition)
    }
}