package com.peterstev.trivago.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peterstev.domain.models.Result
import com.peterstev.trivago.R
import com.peterstev.trivago.adapters.HomeAdapter
import com.peterstev.trivago.adapters.interfaces.OnClickListener
import com.peterstev.trivago.databinding.FragmentHomeBinding
import com.peterstev.trivago.utilities.Status
import com.peterstev.trivago.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var progress: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeAdapter
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        adapter = HomeAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter
        progress = binding.homeProgress

        binding.homeImgSearchBtn.setOnClickListener {
            val query = binding.homeEtSearch.text.toString()
            if (query.isNotBlank())
                viewModel.searchCharacter(query)
            else Toast.makeText(requireContext(), getString(R.string.search_hint), Toast.LENGTH_SHORT)
                .show()
        }
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.searchResultObservable.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.IDLE -> {
                    binding.homeWelcomeCard.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    val result = it.data!!.results!!
                    if (result.isNullOrEmpty()) showPrompt(getString(R.string.search_not_found))
                    else adapter.updateList(result)
                    progress.visibility = View.GONE
                    binding.homeWelcomeCard.visibility = View.GONE
                }
                Status.LOADING -> {
                    progress.visibility = View.VISIBLE
                    binding.homeWelcomeCard.visibility = View.GONE
                }
                Status.ERROR -> {
                    progress.visibility = View.GONE
                    showPrompt(it.message)
                    binding.homeWelcomeCard.visibility = View.GONE
                }
            }
        }
    }

    private fun showPrompt(message: String) {
        val alert = AlertDialog.Builder(requireContext())
        alert.setMessage(message)
        alert.setCancelable(true)
        alert.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        alert.show()
    }

    override fun onCharacterClick(character: Result) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(character)
        findNavController().navigate(action)
    }

}