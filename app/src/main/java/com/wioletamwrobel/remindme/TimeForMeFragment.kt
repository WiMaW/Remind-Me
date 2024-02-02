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
import com.wioletamwrobel.remindme.databinding.FragmentTimeForMeBinding


class TimeForMeFragment : Fragment() {

    private lateinit var binding: FragmentTimeForMeBinding
    private val timeForMePreferences by lazy {
        requireContext().getSharedPreferences(
            "time_for_me",
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimeForMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayValues()
        setUpClickListeners()
    }

    private fun displayValues() {
        binding.textViewInputBookToRead.text = timeForMePreferences.getString(BOOK_TO_READ, null)
        binding.textViewInputFoodToTry.text = timeForMePreferences.getString(FOOD_TO_TRY, null)
        binding.textViewInputMovieToWatch.text =
            timeForMePreferences.getString(MOVIE_TO_WATCH, null)
        binding.textViewInputMusicToListen.text =
            timeForMePreferences.getString(MUSIC_TO_LISTEN, null)
    }

    private fun setUpClickListeners() {
        binding.cardViewBookToRead.setOnClickListener { showDialog(BOOK_TO_READ) }
        binding.cardViewFoodToTry.setOnClickListener { showDialog(FOOD_TO_TRY) }
        binding.cardViewMovieToWatch.setOnClickListener { showDialog(MOVIE_TO_WATCH) }
        binding.cardViewMusicToListen.setOnClickListener { showDialog(MUSIC_TO_LISTEN) }
    }

    private fun showDialog(key: String) {
        val dialogBinding = AlertEditValueBinding.inflate(requireActivity().layoutInflater)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(key)
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                timeForMePreferences.edit {
                    putString(key, dialogBinding.textInput.text.toString())
                }
                displayValues()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    companion object {
        const val BOOK_TO_READ = "Book to read"
        const val FOOD_TO_TRY = "Food to try"
        const val MOVIE_TO_WATCH = "Movie to watch"
        const val MUSIC_TO_LISTEN = "Music to listen"

    }
}


