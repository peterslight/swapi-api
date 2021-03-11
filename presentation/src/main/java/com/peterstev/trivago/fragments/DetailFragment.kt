package com.peterstev.trivago.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textview.MaterialTextView
import com.peterstev.domain.models.Film
import com.peterstev.domain.models.Result
import com.peterstev.domain.models.Specie
import com.peterstev.trivago.R
import com.peterstev.trivago.databinding.FragmentDetailBinding
import com.peterstev.trivago.databinding.FragmentDetailFilmItemBinding
import com.peterstev.trivago.utilities.Status
import com.peterstev.trivago.utilities.formatNumber
import com.peterstev.trivago.viewmodels.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var progressSpecie: ProgressBar
    private lateinit var progressFilms: ProgressBar

    private lateinit var specieName: MaterialTextView
    private lateinit var specieLanguage: MaterialTextView
    private lateinit var specieWorld: MaterialTextView
    private lateinit var speciePopulation: MaterialTextView

    private lateinit var filmsContainer: LinearLayoutCompat

    private val viewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val character = requireArguments()[getString(R.string.result)] as Result
        setupViews(character)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private fun setupViews(character: Result) {
        binding.detailName.text = character.name
        binding.detailName.setOnClickListener { onBackPressedCallback.handleOnBackPressed() }

        binding.detailDob.visibility = if (!character.birthYear.isNullOrEmpty()) {
            binding.detailDob.text = character.birthYear
            View.VISIBLE
        } else View.GONE

        binding.detailHeight.visibility = if (!character.height.isNullOrEmpty()) {
            binding.detailHeight.text = "${character.height}cm"
            View.VISIBLE
        } else View.GONE

        progressFilms = binding.detailProgressFilms
        progressSpecie = binding.detailProgressSpecies

        specieLanguage = binding.detailSpecieLanguage
        specieWorld = binding.detailSpecieHomeWorld
        speciePopulation = binding.detailSpecieHomePopulation
        specieName = binding.detailSpecieName

        filmsContainer = binding.filmsContainer

        setupObservers(character)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.filmsObservable.removeObservers(viewLifecycleOwner)
            viewModel.specieObservable.removeObservers(viewLifecycleOwner)
            viewModel.planetObservable.removeObservers(viewLifecycleOwner)
            viewModel.cleanUp()
            findNavController().navigateUp()
        }
    }


    private fun emptyView(): View {
        val view = View(requireContext())
        view.layoutParams = ViewGroup.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            50
        )
        return view
    }

    private fun mapFilms(filmList: List<Film>) {
        if (!filmList.isNullOrEmpty()) {
            binding.filmsCard.visibility = View.VISIBLE
            filmList.forEach { film ->
                val filmBinding = FragmentDetailFilmItemBinding.inflate(layoutInflater)
                filmBinding.detailFilmTitle.text = film.title
                filmBinding.detailFilmDesc.text = film.openingCrawl
                filmBinding.detailFilmProducer.text = "${film.producer} & ${film.director}"
                filmBinding.detailFilmDate.text = film.releaseDate
                filmBinding.detailBtFilmMore.setOnClickListener { readMore(film) }
                filmBinding.root.setOnClickListener { readMore(film) }
                filmsContainer.addView(filmBinding.root)
                filmsContainer.addView(emptyView())
            }
        } else binding.filmsCard.visibility = View.GONE
    }

    private fun readMore(film: Film) {
        val action =
            DetailFragmentDirections.actionDetailFragmentToFullDetailFragment(film)
        findNavController().navigate(action)
    }

    private fun getData(character: Result) {
        viewModel.getFilms(character.films ?: emptyList())
        viewModel.getPlanet(character.homeworld ?: "")
        viewModel.getSpecie(if (!character.species.isNullOrEmpty()) character.species!![0] else "")
    }


    private fun setupObservers(character: Result) {
        viewModel.filmsObservable.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.IDLE -> {
                    //do nothing
                }

                Status.SUCCESS -> {
                    progressFilms.visibility = View.GONE
                    mapFilms(it.data!!)
                }

                Status.LOADING -> {
                    progressFilms.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    progressFilms.visibility = View.GONE
                }
            }
        }
        viewModel.planetObservable.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.IDLE -> {
                    //do nothing
                }

                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    val planet = it.data
                    if (planet != null) {
                        speciePopulation.visibility =
                            if (!planet.population?.formatNumber().isNullOrEmpty()) {
                                speciePopulation.text = planet.population!!.formatNumber()
                                View.VISIBLE
                            } else View.GONE

                        specieWorld.visibility = if (!planet.name.isNullOrEmpty()) {
                            specieWorld.text = planet.name
                            View.VISIBLE
                        } else View.GONE
                        binding.speciesCard.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    showToast(it.message)
                }
            }
        }

        viewModel.specieObservable.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.IDLE -> {
                    //do nothing
                }

                Status.LOADING -> {
                    progressSpecie.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    val specie = it.data
                    mapSpecie(specie)
                    progressSpecie.visibility = View.GONE
                }
                Status.ERROR -> {
                    showToast(it.message)
                    progressSpecie.visibility = View.GONE
                }
            }
        }

        getData(character)
    }

    private fun mapSpecie(specie: Specie?) {
        if (specie != null) {
            binding.speciesCard.visibility = View.VISIBLE
            specieName.visibility = if (!specie.name.isNullOrEmpty()) {
                specieName.text = specie.name
                View.VISIBLE
            } else View.GONE

            specieLanguage.visibility = if (!specie.language.isNullOrEmpty()) {
                specieLanguage.text = specie.language
                View.VISIBLE
            } else View.GONE
        }
    }

    private fun showToast(message: String) {
        Timber.i(message)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}