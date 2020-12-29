package com.larro.plus.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.larro.plus.R
import com.larro.plus.models.Card

class CardRecyclerViewAdapter(val cardList : List<Card>) :
    RecyclerView.Adapter<CardRecyclerViewAdapter.CardViewHolder>() {

    private val CARD_TYPE = 0
    private val POINTS_TYPE = 1

    class CardViewHolder(private val layout : View) : RecyclerView.ViewHolder(layout) {
        val title : TextView? = layout.findViewById(R.id.card_title)
        val description : TextView? = layout.findViewById(R.id.card_description)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return POINTS_TYPE
        return CARD_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(LayoutInflater.from(parent.context).inflate(
            getLayoutId(viewType), parent,
            false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        if (position != 0) {
            val currentCard = cardList.get(position - 1) // -1 is used as first position is for points panel
            holder.title?.text = currentCard.title
            holder.description?.text = currentCard.description
        }
    }

    override fun getItemCount(): Int = cardList.size + 1 // all cards + points panel

    fun getLayoutId(viewType : Int) : Int {
        return when (viewType) {
            CARD_TYPE -> R.layout.card_layout
            POINTS_TYPE -> R.layout.points_layout
            else -> R.layout.card_layout
        }
    }

}