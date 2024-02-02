package com.wioletamwrobel.remindme

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wioletamwrobel.remindme.databinding.AlertEditValueBinding
import com.wioletamwrobel.remindme.databinding.FragmentTimeForOthersBinding

class TimeForOthersFragment : Fragment() {

    private lateinit var binding: FragmentTimeForOthersBinding
    private val timeForOthersPreferences by lazy {
        requireContext().getSharedPreferences(
            "time_for_others",
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimeForOthersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayValues()
        setUpClickListeners()
    }

    private fun displayValues() {
        binding.textViewInputPersonToCall.text =
            timeForOthersPreferences.getString(PERSON_TO_CALL, null)
        binding.textViewInputFamilyTimeToSpend.text = timeForOthersPreferences.getString(
            FAMILY_TIME_TO_SPEND, null
        )
        binding.textViewInputPersonToMeet.text =
            timeForOthersPreferences.getString(PERSON_TO_MEET, null)
        binding.textViewInputPlaceToVisit.text =
            timeForOthersPreferences.getString(PLACE_TO_VISIT, null)
    }

    private fun setUpClickListeners() {
        binding.cardViewFamilyTimeToSpend.setOnClickListener { showDialog(FAMILY_TIME_TO_SPEND) }
        binding.cardViewPersonToCall.setOnClickListener { showDialog(PERSON_TO_CALL) }
        binding.cardViewPersonToMeet.setOnClickListener { showDialog(PERSON_TO_MEET) }
        binding.cardViewPlaceToVisit.setOnClickListener { showDialog(PLACE_TO_VISIT) }
    }

    private fun showDialog(key: String) {
        val dialogBinding = AlertEditValueBinding.inflate(requireActivity().layoutInflater)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(key)
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                timeForOthersPreferences.edit {
                    putString(key, dialogBinding.textInput.text.toString())
                }
                    displayValues()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    companion object {
        const val PERSON_TO_CALL = "Person to call"
        const val FAMILY_TIME_TO_SPEND = "Family time to spend"
        const val PERSON_TO_MEET = "Person to meet"
        const val PLACE_TO_VISIT = "Place to visit"
    }
}