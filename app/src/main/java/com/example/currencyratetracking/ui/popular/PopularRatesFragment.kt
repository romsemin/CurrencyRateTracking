package com.example.currencyratetracking.ui.popular

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
import com.example.currencyratetracking.utils.Currency
import com.example.currencyratetracking.datamodels.FavouriteRateDB
import com.example.currencyratetracking.datamodels.Rate
import com.example.currencyratetracking.datamodels.RateApiResponse
import com.example.currencyratetracking.databinding.FragmentPopularRatesBinding
import com.example.currencyratetracking.ui.RatesViewModel
import com.example.currencyratetracking.utils.ApiState
import com.example.currencyratetracking.utils.SortOption
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularRatesFragment : Fragment() {

    private var _ratesSpinnerAdapter: ArrayAdapter<String>? = null
    private val ratesSpinnerAdapter: ArrayAdapter<String> get() = _ratesSpinnerAdapter!!

    private var _sortSpinnerAdapter: ArrayAdapter<String>? = null
    private val sortSpinnerAdapter: ArrayAdapter<String> get() = _sortSpinnerAdapter!!

    private var _binding: FragmentPopularRatesBinding? = null
    private val binding: FragmentPopularRatesBinding get() = _binding!!

    private var _adapter: PopularRatesAdapter? = null
    private val adapter: PopularRatesAdapter get() = _adapter!!

    private val popularRatesViewModel: RatesViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPopularRatesBinding.inflate(inflater, container, false)

        _adapter = PopularRatesAdapter(PopularRatesAdapter.OnClickListener {
            popularRatesViewModel.insert(
                FavouriteRateDB(
                    code = it.code,
                    rate = it.rate
                )
            )
        })

        _ratesSpinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Currency.getCodes(requireContext())
        )
        binding.ratesSpinner.adapter = ratesSpinnerAdapter

        _sortSpinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            SortOption.getSortOptions(requireContext())
        )
        binding.sortSpinner.adapter = sortSpinnerAdapter

        val recyclerView = binding.popularRatesRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ratesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                popularRatesViewModel.getRates(ratesSpinnerAdapter.getItem(p2).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }
        }

        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    popularRatesViewModel.ratesStateFlow.collect {
                        when (it) {
                            is ApiState.Success -> {
                                popularRatesViewModel.ratesLiveData.postValue(
                                    mapApiResponseToAdapterData(
                                        it.data,
                                        SortOption.getSortOption(p2)
                                    )
                                )
                            }
                            else -> {}
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }
        }

        popularRatesViewModel.ratesLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        observeRates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
        _ratesSpinnerAdapter = null
        _sortSpinnerAdapter = null
    }

    private fun observeRates() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            popularRatesViewModel.ratesStateFlow.collect {
                when (it) {
                    is ApiState.Success -> {
                        popularRatesViewModel.ratesLiveData.postValue(
                            mapApiResponseToAdapterData(
                                it.data)
                        )
                        binding.loading.visibility = View.GONE
                        binding.error.visibility = View.GONE
                        binding.popularRatesRecyclerView.visibility = View.VISIBLE
                    }
                    is ApiState.Loading -> {
                        binding.popularRatesRecyclerView.visibility = View.GONE
                        binding.loading.visibility = View.VISIBLE
                        binding.error.visibility = View.GONE
                    }
                    is ApiState.Failure -> {
                        binding.loading.visibility = View.GONE
                        binding.error.text = it.msg.localizedMessage
                        binding.error.visibility = View.VISIBLE
                    }
                    is ApiState.Empty -> {
                        binding.popularRatesRecyclerView.visibility = View.INVISIBLE
                        binding.loading.visibility = View.GONE
                        binding.error.visibility = View.GONE
                    }
                }
            }
        }
    }

    fun mapApiResponseToAdapterData(
        response: RateApiResponse,
        sortOption: SortOption? = SortOption.NONE
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
            else -> adapterList
        }
    }
}