package com.example.behsaz.data.api

import com.example.behsaz.data.models.home.APIHomeDataResponse
import com.example.behsaz.data.models.message.APIMessageListResponse
import com.example.behsaz.data.models.myAddress.APIAddAddressResponse
import com.example.behsaz.data.models.myAddress.APIMyAddressListResponse
import com.example.behsaz.data.models.myService.APIAddServiceResponse
import com.example.behsaz.data.models.myService.APIGetCategoryListResponse
import com.example.behsaz.data.models.myService.APIMyServiceListResponse
import com.example.behsaz.data.models.profile.APIProfileResponse
import com.example.behsaz.data.models.profile.APIUpdateProfileResponse
import com.example.behsaz.data.models.rules.APIGetRulesResponse
import com.example.behsaz.data.models.signin.APISignInResponse
import com.example.behsaz.data.models.signup.APISignUpResponse
import com.example.behsaz.utils.ServerConstants.ADDRESS
import com.example.behsaz.utils.ServerConstants.AUTHORIZATION
import com.example.behsaz.utils.ServerConstants.CUSTOMER_ADDRESS_ID
import com.example.behsaz.utils.ServerConstants.EMAIL
import com.example.behsaz.utils.ServerConstants.FIRST_NAME
import com.example.behsaz.utils.ServerConstants.ID
import com.example.behsaz.utils.ServerConstants.LAST_NAME
import com.example.behsaz.utils.ServerConstants.MAP_POINT
import com.example.behsaz.utils.ServerConstants.MOBILE_NUMBER
import com.example.behsaz.utils.ServerConstants.PASSWORD
import com.example.behsaz.utils.ServerConstants.PHONE_NUMBER
import com.example.behsaz.utils.ServerConstants.REAGENT_TOKEN
import com.example.behsaz.utils.ServerConstants.SERVICE_TYPE_ID
import com.example.behsaz.utils.ServerConstants.SUB_URL_ADD_ADDRESS
import com.example.behsaz.utils.ServerConstants.SUB_URL_ADD_SERVICE
import com.example.behsaz.utils.ServerConstants.SUB_URL_CUSTOMER_ADDRESSES
import com.example.behsaz.utils.ServerConstants.SUB_URL_CUSTOMER_SERVICES
import com.example.behsaz.utils.ServerConstants.SUB_URL_EDIT_ADDRESS
import com.example.behsaz.utils.ServerConstants.SUB_URL_EDIT_PROFILE
import com.example.behsaz.utils.ServerConstants.SUB_URL_GET_RULES
import com.example.behsaz.utils.ServerConstants.SUB_URL_GROUPS_AND_MESSAGES
import com.example.behsaz.utils.ServerConstants.SUB_URL_LOGIN
import com.example.behsaz.utils.ServerConstants.SUB_URL_PROFILE
import com.example.behsaz.utils.ServerConstants.SUB_URL_REGISTER
import com.example.behsaz.utils.ServerConstants.TITLE
import com.example.behsaz.utils.ServerConstants.USERNAME
import com.example.behsaz.utils.ServerConstants.USER_DESCRIPTION
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface APIService {

    @FormUrlEncoded
    @POST(SUB_URL_LOGIN)
    suspend fun signIn(
        @Field(USERNAME) username: String,
        @Field(PASSWORD) password: String
    ): Response<APISignInResponse>

    @FormUrlEncoded
    @POST(SUB_URL_REGISTER)
    suspend fun signUp(
        @Field(FIRST_NAME) firstName: String,
        @Field(LAST_NAME) lastName: String,
        @Field(PHONE_NUMBER) phoneNumber: String,
        @Field(MOBILE_NUMBER) mobileNumber: String,
        @Field(USERNAME) username: String,
        @Field(PASSWORD) password: String,
        @Field(EMAIL) email: String,
        @Field(REAGENT_TOKEN) reagentToken: String
    ): Response<APISignUpResponse>

//    @FormUrlEncoded
    @GET(SUB_URL_PROFILE)
    suspend fun profile(
        @Header(AUTHORIZATION) token: String,
    ): Response<APIProfileResponse>

    @FormUrlEncoded
    @POST(SUB_URL_EDIT_PROFILE)
    suspend fun updateProfile(
        @Header(AUTHORIZATION) token: String,
        @Field(FIRST_NAME) firstName: String,
        @Field(LAST_NAME) lastName: String,
        @Field(PHONE_NUMBER) phoneNumber: String,
        @Field(MOBILE_NUMBER) mobileNumber: String,
        @Field(USERNAME) username: String,
        @Field(PASSWORD) password: String,
        @Field(EMAIL) email: String,
    ): Response<APIUpdateProfileResponse>

    @GET(SUB_URL_CUSTOMER_SERVICES)
    suspend fun getMyServiceList(
        @Header(AUTHORIZATION) token: String,
    ): Response<APIMyServiceListResponse>

    @GET(SUB_URL_CUSTOMER_ADDRESSES)
    suspend fun getMyAddressList(
        @Header(AUTHORIZATION) token: String,
    ): Response<APIMyAddressListResponse>

    @FormUrlEncoded
    @POST(SUB_URL_ADD_ADDRESS)
    suspend fun addAddress(
        @Header(AUTHORIZATION) token: String,
        @Field(TITLE) title: String,
        @Field(ADDRESS) address: String,
        @Field(MAP_POINT) mapPoint: String
    ): Response<APIAddAddressResponse>

    @FormUrlEncoded
    @POST(SUB_URL_EDIT_ADDRESS)
    suspend fun editAddress(
        @Header(AUTHORIZATION) token: String,
        @Field(ID) id: String,
        @Field(TITLE) title: String,
        @Field(ADDRESS) address: String,
        @Field(MAP_POINT) mapPoint: String
    ): Response<APIAddAddressResponse>

    @GET(SUB_URL_GET_RULES)
    suspend fun getRules():Response<APIGetRulesResponse>

    @GET(SUB_URL_GROUPS_AND_MESSAGES)
    suspend fun getHomeData():Response<APIHomeDataResponse>

    @FormUrlEncoded
    @POST(SUB_URL_ADD_SERVICE)
    suspend fun addService(
        @Header(AUTHORIZATION) token: String,
        @Field(MAP_POINT) mapPoint: String,
        @Field(ADDRESS) address: String,
        @Field(CUSTOMER_ADDRESS_ID) customerAddressId: String,
        @Field(SERVICE_TYPE_ID) serviceTypeId : String,
        @Field(USER_DESCRIPTION) userDescription : String
    ): Response<APIAddServiceResponse>

    @GET
    suspend fun getCategoryList(
        @Url url: String,
    ): Response<APIGetCategoryListResponse>

    @GET
    suspend fun getMessagesList(
        @Url url: String,
    ): Response<APIMessageListResponse>
}