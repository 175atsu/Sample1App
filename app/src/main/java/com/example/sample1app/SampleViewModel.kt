package com.example.sample1app

import androidx.lifecycle.ViewModel

class SampleViewModel: ViewModel() {


    @Inject
    lateinit var companyListData: MutableLiveData<MutableList<Company>>
    @Inject
    lateinit var companyList: MutableList<Company>





    var id: String? = null
    var name: String? = null
    var image: String? = null
}