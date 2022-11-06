package com.example.currencyratetracking.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencyratetracking.R
import com.example.currencyratetracking.util.Code
import com.example.currencyratetracking.datamodel.Rate
import com.example.currencyratetracking.databinding.FragmentPopularRatesBinding
import com.example.currencyratetracking.ui.RatesViewModel
import com.example.currencyratetracking.util.ApiState
import com.example.currencyratetracking.util.SortOption
import com.example.currencyratetracking.datamodel.RatesApiResponse
import com.example.currencyratetracking.ui.RatesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularRatesFragment : Fragment() {

    private var _rateSpinnerAdapter: ArrayAdapter<String>? = null
    private val rateSpinnerAdapter: ArrayAdapter<String> get() = _rateSpinnerAdapter!!

    private var _sortSpinnerAdapter: ArrayAdapter<String>? = null
    private val sortSpinnerAdapter: ArrayAdapter<String> get() = _sortSpinnerAdapter!!

    private var _binding: FragmentPopularRatesBinding? = null
    private val binding: FragmentPopularRatesBinding get() = _binding!!

    private var _adapter: RatesAdapter? = null
    private val adapter: RatesAdapter get() = _adapter!!

    private val ratesViewModel: RatesViewModel by hiltNavGraphViewModels(R.id.rates_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularRatesBinding.inflate(inflater, container, false)
        _adapter = RatesAdapter(RatesAdapter.OnClickListener {
            ratesViewModel.insert(
                Rate(it.code, it.rate)
            )
        })

        _rateSpinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Code.getCodes(requireContext())
        )
        binding.popularRatesRateSpinner.adapter = rateSpinnerAdapter

        _sortSpinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            SortOption.getSortOptions(requireContext())
        )
        binding.popularRatesSortSpinner.adapter = sortSpinnerAdapter

        val recyclerView = binding.popularRatesRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.popularRatesRateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                ratesViewModel.getRates(rateSpinnerAdapter.getItem(p2).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }
        }

        binding.popularRatesSortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    ratesViewModel.ratesStateFlow.collect {

                        if (it is ApiState.Success) {
                            ratesViewModel.ratesLiveData.postValue(
                                mapApiResponseToAdapterData(
                                    it.data,
                                    SortOption.getSortOption(p2)
                                )
                            )
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }
        }

        ratesViewModel.ratesLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        observeRates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
        _rateSpinnerAdapter = null
        _sortSpinnerAdapter = null
    }

    private fun observeRates() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            ratesViewModel.ratesStateFlow.collect {
                when (it) {
                    is ApiState.Success -> {
                        ratesViewModel.ratesLiveData.postValue(
                            mapApiResponseToAdapterData(
                                it.data,
                            SortOption.BY_CODE_ASC)
                        )
                        binding.loading.visibility = View.GONE
                        binding.error.visibility = View.GONE
                    }
                    is ApiState.Loading -> {
                        binding.loading.visibility = View.VISIBLE
                    }
                    is ApiState.Failure -> {
                        binding.error.text = it.message.localizedMessage
                        binding.error.visibility = View.VISIBLE
                    }
                    is ApiState.Empty -> {}
                }
            }
        }
    }

    fun mapApiResponseToAdapterData(
        response: RatesApiResponse,
        sortOption: SortOption
    ): List<Rate> {
        val adapterList = mutableListOf<Rate>()
        for (rate in response.rates) {
            adapterList.add(Rate("${response.base}/${rate.key}", rate.value))
        }
        return when (sortOption) {
            SortOption.BY_RATE_ASC -> adapterList.sortedBy { it.rate }
            SortOption.BY_RATE_DESC -> adapterList.sortedByDescending { it.rate }
            SortOption.BY_CODE_ASC -> adapterList.sortedBy { it.code }
            SortOption.BY_CODE_DESC -> adapterList.sortedByDescending { it.code }
        }
    }
}