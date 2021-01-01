package com.larro.plus.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.larro.plus.R
import com.larro.plus.dialogs.QrCodeDialog
import com.larro.plus.models.Card
import com.larro.plus.models.UserInfo

private const val CARD_TYPE = 0
private const val POINTS_PANEL_TYPE = 1

class MainRecyclerViewAdapter(private val cardList: List<Card>, private val userInfo: UserInfo) :
    RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder>(), View.OnClickListener {

    var recyclerView : RecyclerView? = null

    class MainViewHolder(layout: View) : RecyclerView.ViewHolder(layout) {
        val view = layout
    }

    @Override
    override fun onAttachedToRecyclerView(attachedRecyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(attachedRecyclerView);
        recyclerView = attachedRecyclerView
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return POINTS_PANEL_TYPE
        return CARD_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(
                getLayoutId(viewType), parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        when(getItemViewType(position)) {
            CARD_TYPE -> setupCardUi(holder.view, cardList[position - 1])
            POINTS_PANEL_TYPE -> setupPointsPanelUi(holder.view)
        }
    }

    override fun getItemCount(): Int = cardList.size + 1 // all cards + points panel

    private fun getLayoutId(viewType: Int) : Int {
        return when (viewType) {
            CARD_TYPE -> R.layout.card_layout
            POINTS_PANEL_TYPE -> R.layout.points_panel_layout
            else -> R.layout.card_layout
        }
    }

    private fun setupPointsPanelUi(view: View) {
        val points = userInfo.points
        val pointsToNextLevel = userInfo.nextLevelPoints - userInfo.points
        val nextLevelStr = view.context.getString(
            R.string.next_level_points,
            pointsToNextLevel,
            userInfo.nextLevel
        )
        val headerStr = view.context.getString(R.string.points_header, userInfo.level)
        val nextLevelTextView : MaterialTextView? = view.findViewById(R.id.next_level_text)
        val headerTextView : MaterialTextView? = view.findViewById(R.id.header)
        val pointsTextView : MaterialTextView? = view.findViewById(R.id.body)
        val progressBar : ProgressBar? = view.findViewById(R.id.progress_bar)
        nextLevelTextView?.text = nextLevelStr
        headerTextView?.text = headerStr
        pointsTextView?.text = points.toString()
        val progress : Float = (userInfo.points.toFloat() / userInfo.nextLevelPoints.toFloat()) * 100
        progressBar?.progress = progress.toInt()
    }

    private fun setupCardUi(view: View, card: Card) {
        view.setOnClickListener(this)
        val titleTextView : TextView? = view.findViewById(R.id.card_title)
        val descriptionTextView : TextView? = view.findViewById(R.id.card_description)
        titleTextView?.text = card.title
        descriptionTextView?.text = card.description
    }

    override fun onClick(view: View?) {
        val position : Int? = view?.let { recyclerView?.getChildAdapterPosition(it) }
        if (position != null && position > 0) {
            val card : Card = cardList.get(position - 1) // (position -1) is used as first item in UI is not a card
            QrCodeDialog().show(view.context, card.qrcodeContent, card.qrcodeContent)
        }
    }

}