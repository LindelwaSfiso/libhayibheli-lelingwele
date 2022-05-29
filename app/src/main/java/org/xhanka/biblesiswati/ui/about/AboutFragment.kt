package org.xhanka.biblesiswati.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.Utils

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contactUs: TextView = view.findViewById(R.id.contactUs)
        contactUs.setOnClickListener {
            AlertDialog.Builder(it.context)
                .setTitle("Contact us")
                .setMessage("Any feedback is appreciated. Send us an email on how we can improve.")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Send email") { _, _ ->
                    startActivity(sendEmailIntent())
                }.show()
        }
    }

    private fun sendEmailIntent(): Intent {
        return Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(Utils.CONTACT_US_EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, Utils.EMAIL_SUBJECT)
        }
    }
}