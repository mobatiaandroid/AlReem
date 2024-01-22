package com.nas.alreem.fragment.performing_arts.model



class PerformingResponseModel  {
    private var status: String? = null
    private var responseArray: ResponseData? = null
    fun getResponsecode(): String? {
        return status
    }

    fun setResponsecode(responsecode: String?) {
        this.status = responsecode
    }

    fun getResponse(): ResponseData? {
        return responseArray
    }

    fun setResponse(response: ResponseData?) {
        this.responseArray = response
    }


    class ResponseData {
        lateinit var banner_image: String
        var data: ArrayList<SecondaryModel>? = null


    }

}