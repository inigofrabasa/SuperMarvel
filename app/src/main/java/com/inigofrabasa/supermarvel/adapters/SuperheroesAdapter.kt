package com.inigofrabasa.supermarvel.adapters

import android.graphics.Rect
import android.os.Build
import android.transition.*
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.inigofrabasa.supermarvel.R
import com.inigofrabasa.supermarvel.SuperheroesApp
import com.inigofrabasa.supermarvel.data.model.Model
import com.inigofrabasa.supermarvel.databinding.SuperheroesListItemBinding
import com.inigofrabasa.supermarvel.utils.setCommonInterpolator
import com.inigofrabasa.supermarvel.views.SuperheroeDetailFragment
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class SuperheroesAdapter (
    private val viewRect: Rect,
    private val transitionInterpolator: FastOutSlowInInterpolator,
    private val TRANSITION_DURATION: Long,
    private val activity: FragmentActivity,
    private val fragment: Fragment
    )
    : RecyclerView.Adapter<SuperheroesAdapter.ViewHolder>()
    {
        private lateinit var items : ArrayList<Any>

        fun submitList(inItems : ArrayList<Any>){
            this.items = inItems
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(SuperheroesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            fun onViewClick() {
                SuperheroesApp.instance.superheroe.value = items[position] as Model.Superheroe
                holder.itemView.getGlobalVisibleRect(viewRect)

                (fragment.exitTransition as Transition).epicenterCallback =
                    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                    object : Transition.EpicenterCallback() {
                        override fun onGetEpicenter(transition: Transition) = viewRect
                    }

                Timer().schedule(250L){
                    val sharedElementTransition = TransitionSet()
                        .addTransition(ChangeBounds())
                        .addTransition(ChangeTransform())
                        .addTransition(ChangeImageTransform()).apply {
                            duration = TRANSITION_DURATION
                            setCommonInterpolator(transitionInterpolator)
                        }

                    val fragment = SuperheroeDetailFragment().apply {
                        sharedElementEnterTransition = sharedElementTransition
                        sharedElementReturnTransition = sharedElementTransition
                    }

                    activity.supportFragmentManager
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .addSharedElement(holder.itemView, "transition_name")
                        .commit()
                }
            }

            holder.bindData(items[position] as Model.Superheroe, ::onViewClick)
        }

        override fun getItemCount(): Int {
            items.apply { return this.size }
        }

        class ViewHolder (
            private val binding: SuperheroesListItemBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            fun bindData(item: Model.Superheroe, expandHandler: () -> Unit) {
                (binding).apply{
                        superheroe = item
                        setClickListener { expandHandler()  }
                        itemView.transitionName = item.name
                        itemView.tag = item.name
                    }

            }
        }
}