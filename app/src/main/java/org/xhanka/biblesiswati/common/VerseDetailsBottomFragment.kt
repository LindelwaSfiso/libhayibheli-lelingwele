package org.xhanka.biblesiswati.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel

class VerseDetailsBottomFragment : BaseBottomRoundView() {
    private val model by activityViewModels<BibleViewModel>()
    private var verseId = 0

    // TODO: REMOVE THIS, AND USE VIEW MODEL
    private var onClick: OnClick? = null

    interface OnClick {
        fun isClicked(isClicked: Boolean)
    }

    @JvmName("setOnClick1")
    fun setOnClick(onClick: OnClick?) {
        this.onClick = onClick
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) verseId = arguments!!.getInt(KEY, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment__verse_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val translationText = view.findViewById<TextView>(R.id.translationText)
        model.getAltVerse(verseId).observe(this, { text: String? ->
            translationText.text = text
        })

        val favToggle: Chip = view.findViewById(R.id.favToggle)

        favToggle.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                favToggle.isChecked = true
                favToggle.setText(R.string.remove_from_favorites)
                model.addToFavorites(verseId)
                if (onClick != null) onClick!!.isClicked(true)
            } else {
                favToggle.isChecked = false
                favToggle.setText(R.string.add_to_favorite)
                model.removeFromFavorites(verseId)
                if (onClick != null) onClick!!.isClicked(false)
            }
        }

        model.isAddedToFavorites(verseId).observe(this, { integer: Int? ->
            if (integer != null && integer == 1) {
                favToggle.isChecked = true
                favToggle.setText(R.string.remove_from_favorites)
            }
        })
    }

    companion object {
        const val KEY = "ID"

        fun getInstance(verseId: Int): VerseDetailsBottomFragment {
            val instance = VerseDetailsBottomFragment()
            val bundle = Bundle()
            bundle.putInt(KEY, verseId)
            instance.arguments = bundle
            return instance
        }
    }
}