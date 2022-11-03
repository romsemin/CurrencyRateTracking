package com.example.currencyratetracking.ui.favourite

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
import com.example.currencyratetracking.utils.Currency
import com.example.currencyratetracking.utils.SortOption

class FavouriteRatesFragment : Fragment() {

    private var _ratesSpinnerAdapter: ArrayAdapter<String>? = null
    private val ratesSpinnerAdapter: ArrayAdapter<String> get() = _ratesSpinnerAdapter!!

    private var _sortSpinnerAdapter: ArrayAdapter<String>? = null
    private val sortSpinnerAdapter: ArrayAdapter<String>  get() = _sortSpinnerAdapter!!

    private var _binding: FragmentFavouriteRatesBinding? = null
    private val binding: FragmentFavouriteRatesBinding get() = _binding!!

    private var _adapter: FavouriteRatesAdapter? = null
    private val adapter: FavouriteRatesAdapter get() = _adapter!!

    private val favouriteRatesViewModel: RatesViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavouriteRatesBinding.inflate(inflater, container, false)
        _adapter = FavouriteRatesAdapter()

        _ratesSpinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Currency.getCodes(requireContext())
        )

        _sortSpinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            SortOption.getSortOptions(requireContext())
        )

        val recyclerView = binding.favouriteRatesRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favouriteRatesSpinner.adapter = ratesSpinnerAdapter

        binding.favouriteRatesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    favouriteRatesViewModel.getFilteredFavouriteRates(ratesSpinnerAdapter.getItem(p2).toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    return
                }
            }

        binding.favouriteSortSpinner.adapter = sortSpinnerAdapter

        binding.favouriteSortSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    favouriteRatesViewModel.getFavouriteRates(SortOption.getSortOption(p2))
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    return
                }
            }

        favouriteRatesViewModel.favouriteRatesLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
        _ratesSpinnerAdapter = null
        _sortSpinnerAdapter = null
    }
}
