package org.xhanka.biblesiswati.ui.downloader

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.xhanka.biblesiswati.common.DATA
import org.xhanka.biblesiswati.common.Utils
import org.xhanka.biblesiswati.databinding.FragmentInstallExtraVersionBinding
import org.xhanka.biblesiswati.ui.downloader.adapter.InstallExtraAdapter
import org.xhanka.biblesiswati.ui.downloader.model.ImportDb
import org.xhanka.biblesiswati.ui.downloader.service.ImportVersionService
import org.xhanka.biblesiswati.ui.downloader.service.ImportVersionService.Companion.ACTION_DONE_DOWNLOAD
import org.xhanka.biblesiswati.ui.downloader.service.ImportVersionService.Companion.ACTION_SEND_PROGRESS
import org.xhanka.biblesiswati.ui.downloader.service.ImportVersionService.Companion.ACTION_START_DOWNLOAD
import org.xhanka.biblesiswati.ui.downloader.service.ImportVersionService.Companion.PROGRESS_KEY
import kotlin.math.ceil

@AndroidEntryPoint
class InstallExtraVersionFragment : Fragment() {
    private var _binding: FragmentInstallExtraVersionBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<InstallExtraVersionViewModel>()

    // consider downloading files from a remote server
    private lateinit var getOtherVersionsLauncher: ActivityResultLauncher<String>

    private val progressReceiver = ImportBroadCastReceiver()

    private var _progress: MutableLiveData<Triple<Int, Int, String>> = MutableLiveData()
    private val progress: LiveData<Triple<Int, Int, String>> = _progress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getOtherVersionsLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let { uri ->
                val inputStream = requireActivity().contentResolver.openInputStream(uri)
                val reader = inputStream?.bufferedReader()

                val inputJson = reader?.use { buffer -> buffer.readText() }
                reader?.close()

                // todo: validate import here, check if it conforms to required data,
                // NOTE: this may not be required if user downloads from a reliable source which
                // we'll maintain, check dbName, then check if all rows are 31104

                val gson = Gson().fromJson(inputJson, ImportDb::class.java)
                if (viewModel.isInstalled(Utils.mapDbVersionsUpper(gson.dbName).versionId)) {
                    Toast.makeText(context, "Version already exists!", Toast.LENGTH_LONG).show()
                    return@let
                }

                binding.dummy.text = String.format("Preparing to import version: ${gson.dbName}")
                binding.progressBar.max = gson.verses.size

                requireActivity().startService(
                    Intent(context, ImportVersionService::class.java).apply {
                        action = ACTION_START_DOWNLOAD
                        // Log.d("TAG", inputJson?.length.toString())
                        DATA.JSON.seText(inputJson)
                    }
                )

            } ?: run {
                context?.let { context1 ->
                    Toast.makeText(
                        context1,
                        "You didn't select any file!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        requireActivity().registerReceiver(progressReceiver, IntentFilter(ACTION_SEND_PROGRESS))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstallExtraVersionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context, DividerItemDecoration.VERTICAL
            )
        )

        val adapter = InstallExtraAdapter()

        viewModel.allVersion.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.recyclerView.adapter = adapter
        binding.progressBar.isIndeterminate = false

        binding.importDatabase.setOnClickListener {
            getOtherVersionsLauncher.launch("application/*")
        }

        progress.observe(viewLifecycleOwner) {
            binding.progressBar.max = it.first
            binding.progressBar.progress = it.second
            val percentage = ceil((it.second.toDouble() / it.first.toDouble()) * 100)

            binding.dummy.text = String.format("Importing ${it.third}..... $percentage %%")
        }
    }

    @Suppress("UNCHECKED_CAST")
    inner class ImportBroadCastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                if (it.action == ACTION_SEND_PROGRESS) {
                    val progress = it.getSerializableExtra(PROGRESS_KEY) as Triple<Int, Int, String>
                    _progress.postValue(progress)
                }
                if (it.action == ACTION_DONE_DOWNLOAD) {
                    binding.dummy.text =
                        String.format("Done importing ${progress.value?.third ?: "database"}")
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    override fun onDestroy() {
        requireActivity().unregisterReceiver(progressReceiver)
        super.onDestroy()
        _binding = null
    }
}