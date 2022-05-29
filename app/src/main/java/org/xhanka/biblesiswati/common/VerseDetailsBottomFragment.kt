package org.xhanka.biblesiswati.common

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.databinding.FragmentVerseDetailsBottomBinding
import org.xhanka.biblesiswati.ui.main.models.Verse
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel


/**
 * @author Dlamini Lindelwa Sifiso [23-May-22]
 */
class VerseDetailsBottomFragment : BaseBottomRoundView() {
    private val model by activityViewModels<BibleViewModel>()
    private lateinit var verse: Verse
    private lateinit var bibleVersion: BibleVersion

    private var versionInfo = Pair("NIV", "Genesis")

    private var _binding: FragmentVerseDetailsBottomBinding? = null
    private val binding get() = _binding!!

    private lateinit var saveIntent: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            verse = it.getParcelable(VERSE_KEY)!!
            bibleVersion = it.getSerializable(VERSION_KEY)!! as BibleVersion
        }
        saveIntent = registerForActivityResult(
            ActivityResultContracts.CreateDocument("image/png")
        ) {
            it?.let { uri ->
                val outputStream = requireActivity().contentResolver.openOutputStream(uri)!!
                val bitmap = getScreenShot(
                    title = "${versionInfo.second} ${verse.chapter}:${verse.verseNum}",
                    verseText = binding.translationText.text.toString(),
                )
                if (!bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)) {
                    context?.let { con ->
                        Toast.makeText(con, "Oops. Couldn't save screenshot!", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                binding.translateParent.isVisible = true
                binding.previewParent.isVisible = false

                context?.let { con ->
                    Toast.makeText(con, "Successfully saved screenshot...", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerseDetailsBottomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        verse.let {

            configVersionChips(it.nivVerse, binding.nivChip)
            configVersionChips(it.kjvVerse, binding.kjvChip)
            configVersionChips(it.siswatiVerse, binding.siswatiChip)
            configVersionChips(it.bbeVerse, binding.bbeChip)
            configVersionChips(it.yltVerse, binding.yltChip)
            configVersionChips(it.zuluVerse, binding.zuluChip)
            configVersionChips(it.asvVerse, binding.asvChip)
            configVersionChips(it.wbtVerse, binding.wbtChip)

            binding.translateVersionsGroup.setOnCheckedStateChangeListener { group, _ ->
                configureOnChangeVersions(group.checkedChipId)
            }

            configureDefaultTranslation()

            binding.shareVerse.setOnClickListener { chipView ->
                chipView.context.startActivity(
                    Intent.createChooser(
                        Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, binding.translationText.text.toString())
                        },
                        "Share Verse",
                    )
                )
                dismiss()
            }

            binding.cancelButton.setOnClickListener {
                binding.translateParent.isVisible = true
                binding.previewParent.isVisible = false
            }

            binding.saveButton.setOnClickListener {
                saveIntent.launch(
                    "verve_${versionInfo.second.trimIndent().lowercase().subSequence(0, 3)}_" +
                            "${verse.chapter}_${verse.verseNum}"
                )
            }

            binding.saveToggle.setOnClickListener {
                binding.translateParent.isVisible = false
                binding.previewParent.isVisible = true

                val bitmap = getScreenShot(
                    title = "${versionInfo.second} ${verse.chapter}:${verse.verseNum}",
                    verseText = binding.translationText.text.toString(),
                )
                binding.previewImage.setImageBitmap(bitmap)
            }

            model.isAddedToFavorites(it.id)?.let { fav ->
                binding.favToggle.isChecked = (fav == 1)
                binding.favToggle.setChipIconTintResource(android.R.color.holo_red_dark)
            }

            binding.favToggle.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                if (isChecked) {
                    binding.favToggle.isChecked = true
                    binding.favToggle.setChipIconTintResource(android.R.color.holo_red_light)
                    model.addToFavorites(it.id)
                } else {
                    binding.favToggle.isChecked = false
                    binding.favToggle.setChipIconTintResource(android.R.color.black)
                    model.removeFromFavorites(it.id)
                }
            }
        }
    }

    private fun configureDefaultTranslation() {
        when {
            bibleVersion.isEnglish() -> {
                binding.siswatiChip.isChecked = true
                binding.translationText.text = verse.siswatiVerse
                versionInfo = Pair("SISWATI", verse.siswatiBook)
            }
            else -> {
                binding.nivChip.isChecked = true
                binding.translationText.text = verse.nivVerse
                versionInfo = Pair("NIV", verse.nivBook)
            }
        }
    }

    private fun configureOnChangeVersions(checkedChipId: Int) {
        when (checkedChipId) {
            R.id.nivChip -> {
                versionInfo = Pair("NIV", verse.nivBook)
                binding.translationText.text = verse.nivVerse
            }
            R.id.siswatiChip -> {
                versionInfo = Pair("SISWATI", verse.siswatiBook)
                binding.translationText.text = verse.siswatiVerse
            }
            R.id.kjvChip -> {
                versionInfo = Pair("KJV", verse.nivBook)
                binding.translationText.text = verse.kjvVerse.toString()
            }
            R.id.asvChip -> {
                versionInfo = Pair("ASV", verse.nivBook)
                binding.translationText.text = verse.asvVerse.toString()
            }
            R.id.bbeChip -> {
                versionInfo = Pair("BBE", verse.nivBook)
                binding.translationText.text = verse.bbeVerse.toString()
            }
            R.id.wbtChip -> {
                versionInfo = Pair("WBT", verse.nivBook)
                binding.translationText.text = verse.bbeVerse.toString()
            }
            R.id.yltChip -> {
                versionInfo = Pair("YLT", verse.nivBook)
                binding.translationText.text = verse.yltVerse.toString()
            }
            R.id.zuluChip -> {
                versionInfo = Pair("ZULU", verse.zuluBook)
                binding.translationText.text = verse.zuluVerse.toString()
            }

        }
    }

    private fun configVersionChips(verse: String?, chip: Chip) {
        verse ?: run {
            chip.isVisible = false
        }
    }

    private fun getScreenShot(
        title: String,
        verseText: String
    ): Bitmap {

        val saveView = binding.screenShot.root

        // inflate save view before creating canvas
        binding.screenShot.title.text = title
        binding.screenShot.versionName.text = versionInfo.first
        binding.screenShot.verseText.text = verseText

        val bitmap = Bitmap.createBitmap(
            saveView.measuredWidth,
            saveView.measuredHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        saveView.draw(canvas)

        return bitmap
    }

    companion object {
        private const val VERSE_KEY = "version"
        private const val VERSION_KEY = "version_key"

        fun getInstance(verse: Verse, bibleVersion: BibleVersion): VerseDetailsBottomFragment {
            val instance = VerseDetailsBottomFragment()
            val bundle = Bundle()
            bundle.putParcelable(VERSE_KEY, verse)
            bundle.putSerializable(VERSION_KEY, bibleVersion)
            instance.arguments = bundle
            return instance
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
