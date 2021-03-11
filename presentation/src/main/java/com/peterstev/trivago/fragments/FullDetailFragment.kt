package com.peterstev.trivago.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.peterstev.domain.models.Film
import com.peterstev.trivago.R
import com.peterstev.trivago.databinding.FragmentDetailFilmBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class FullDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailFilmBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val film = requireArguments()[getString(R.string.film)] as Film
        setupViews(film)
    }
    private fun setupViews(film: Film) {
        binding.detailFilmTitle.text = film.title
        binding.detailFilmDate.text = film.releaseDate
        binding.detailFilmDesc.text = film.openingCrawl
        binding.detailFilmProducer.text = "${film.producer} & ${film.director}"
        binding.detailFilmTitle.setOnClickListener { findNavController().navigateUp() }
    }
}