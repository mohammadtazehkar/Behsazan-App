package com.example.behsaz.utils

object Constants {
    const val FOR_ADD = "forAdd"
    const val FOR_EDIT = "forEdit"
    const val FOR_EDIT_LOCATION = "forEditLocation"
    const val FOR_ADD_LOCATION = "forEditLocation"
    const val FOR_VIEW_LOCATION = "forViewLocation"

}

//region Destinations
object Destinations{
    const val SPLASH_SCREEN = "splash"
    const val SIGN_UP_SCREEN = "signup"
    const val SIGN_IN_SCREEN = "signin"
    //    const val SIGN_IN_ROUTE = "signin/{email}"
    const val HOME_SCREEN = "home"
    const val ADD_SERVICE_SCREEN = "addService"
    const val PROFILE_SCREEN = "profile"
    const val MY_SERVICES_SCREEN = "myServices"
    const val MY_ADDRESSES_SCREEN = "myAddresses"
    const val ADD_ADDRESS_SCREEN = "addAddress"
    const val MESSAGES_SCREEN = "messages"
    const val RULES_SCREEN = "rules"
    const val ABOUT_US_SCREEN = "about"
    const val LOG_OUT_SCREEN = "logout"
    const val MAP_SCREEN = "map"
}
//endregion

//region ArgumentKeys
object ArgumentKeys{
    const val CATEGORY_ID = "categoryId"
    const val CATEGORY_TITLE = "categoryTitle"
    const val ADDRESS_ID = "addressId"
    const val ADDRESS_TITLE = "addressTitle"
    const val ADDRESS = "address"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val FOR_WHAT = "forWhat"
}
//endregion

//region ServerConstants
object ServerConstants{
//    const val BASE_URL = "http://panel.behsazanapp.ir/"
//    const val BASE_URL = "http://localhost:8585/"
//    const val BASE_URL = "https://10.0.2.2:8585/"
    const val BASE_URL = "http://behsazan.mohammadtazehkar.ir/"
//    const val BASE_URL = "http://192.168.142.34:8585/"
    const val SUB_URL_REGISTER = "api/CustomerApp/AddCustomer"
    const val SUB_URL_LOGIN = "api/customerApp/login"
    const val SUB_URL_GROUPS_AND_MESSAGES = "api/CustomerApp/GetLastMessagesAndServiceGroups"
    const val SUB_URL_AGENTS_LIST = "api/CustomerApp/GetAgents"
    const val SUB_URL_PROFILE = "api/CustomerApp/GetCustomer"
    const val SUB_URL_EDIT_PROFILE = "api/CustomerApp/EditCustomer"
    const val SUB_URL_CUSTOMER_SERVICES = "api/CustomerApp/GetCustomerServices"
    const val SUB_URL_CUSTOMER_ADDRESSES = "api/CustomerApp/GetCustomerAddresses"
    const val SUB_URL_ADD_ADDRESS = "api/CustomerApp/AddAddress"
    const val SUB_URL_EDIT_ADDRESS = "api/CustomerApp/EditAddress"
    const val SUB_URL_CUSTOMER_SERVICE_TYPES = "api/CustomerApp/GetServiceTypeByGroupId/"
    const val SUB_URL_MESSAGES = "api/CustomerApp/GetAllMessages/"
    const val SUB_URL_ADD_SERVICE = "api/CustomerApp/AddService"
    const val SUB_URL_SERVICE_DETAILS = "api/CustomerApp/GetServiceDetails/"
    const val SUB_URL_ADD_OPINION_AND_SCORE = "api/CustomerApp/AddScoreAndOpinion"
    const val SUB_URL_GET_RULES = "api/CustomerApp/GetRule"
    const val IMAGE_URL = "http://behsazan.mohammadtazehkar.ir/uploads/"
    const val AUTHORIZATION = "Authorization"
    const val FIRST_NAME = "firstName"
    const val LAST_NAME = "lastName"
    const val PHONE_NUMBER = "phoneNumber"
    const val MOBILE_NUMBER = "mobileNumber"
    const val USERNAME = "username"
    const val PASSWORD = "password"
    const val EMAIL = "email"
    const val REAGENT_TOKEN = "reagentToken"
    const val TITLE = "title"
    const val ADDRESS = "address"
    const val MAP_POINT = "mapPoint"
    const val ID = "id"
    const val CUSTOMER_ADDRESS_ID = "customerAddressId"
    const val SERVICE_TYPE_ID = "serviceTypeId"
    const val USER_DESCRIPTION = "userDescription"
    const val OPINION = "opinion"
    const val SCORE = "score"
}
//endregion

//region JSonStatusCode
object JSonStatusCode{
    const val SUCCESS = 200
    const val EXPIRED_TOKEN = 401
    const val DUPLICATE_USERNAME = 409
    const val INVALID_USERNAME = 404
    const val SERVER_CONNECTION = 404
    const val INTERNET_CONNECTION = 13720818
}
//endregion


