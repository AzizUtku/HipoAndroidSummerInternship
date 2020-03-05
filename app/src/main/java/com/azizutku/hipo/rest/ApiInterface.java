package com.azizutku.hipo.rest;

import com.azizutku.hipo.models.Company;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {

    @GET("/artizco/a957d4e0af6f9d35048808e7200ea076/raw/aedca21e45b094a7ba3e36d775a3928e10674355/hipo.json/")
    Call<Company> getCompany();

}
