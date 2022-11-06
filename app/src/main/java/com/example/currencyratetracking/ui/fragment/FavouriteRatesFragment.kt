package com.example.currencyratetracking.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.currencyratetracking.R
import com.example.currencyratetracking.databinding.FragmentFavouriteRatesBinding
import com.example.currencyratetracking.ui.RatesViewModel
import com.example.currencyratetracking.ui.RatesAdapter
import com.example.currencyratetracking.util.Code
import com.example.currencyratetracking.util.SortOption

class FavouriteRatesFragment : Fragment() {

    private var _rateSpinnerAdapter: ArrayAdapter<String>? = null
    private val rateSpinnerAdapter: ArrayAdapter<String> get() = _rateSpinnerAdapter!!

    private var _sortSpinnerAdapter: ArrayAdapter<String>? = null
    private val sortSpinnerAdapter: ArrayAdapter<String>  get() = _sortSpinnerAdapter!!

    private var _binding: FragmentFavouriteRatesBinding? = null
    private val binding: FragmentFavouriteRatesBinding get() = _binding!!

    private var _adapter: RatesAdapter? = null
    private val adapter: RatesAdapter get() = _adapter!!

    private val ratesViewModel: RatesViewModel by hiltNavGraphViewModels(R.id.rates_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteRatesBinding.inflate(inflater, container, false)
        _adapter = RatesAdapter()

        _rateSpinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Code.getCodes(requireContext())
        )
        binding.favouriteRatesRateSpinner.adapter = rateSpinnerAdapter

        _sortSpinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            SortOption.getSortOptions(requireContext())
        )
        binding.favouriteRatesSortSpinner.adapter = sortSpinnerAdapter

        val recyclerView = binding.favouriteRatesRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favouriteRatesRateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    ratesViewModel.getRatesDBByCode(rateSpinnerAdapter.getItem(p2).toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    return
                }
            }

        binding.favouriteRatesSortSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    ratesViewModel.getRatesDB(SortOption.getSortOption(p2))
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    return
                }
            }

        ratesViewModel.ratesDBLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
        _rateSpinnerAdapter = null
        _sortSpinnerAdapter = null
    }
}
