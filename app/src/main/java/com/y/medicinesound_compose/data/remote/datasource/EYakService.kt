package com.y.medicinesound_compose.data.remote.datasource

import com.y.medicinesound_compose.data.remote.dto.EYakResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// DECODING KEY
private const val SERVICE_KEY = "PL0dac515cDOUshijeLobFFiVPAjOIzC2YlxN0ewrilBop3oZOquTgmyZm2dwVUCv69vG3XBMdqnVkARzXkygA=="
// BASE_URL = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList"
// base_url?serviceKey={service_key} & type = json & numOfRows = 3 & itemName = {item_name}
interface EYakService {

    @GET("getDrbEasyDrugList")
    suspend fun getEYakList(
        @Query("serviceKey") serviceKey : String = SERVICE_KEY,
        @Query("type") type : String = "json",
        @Query("numOfRows") numOfRows : Int = 50,
        @Query("itemName") itemName : String,
    ) : Response<EYakResponse>

}