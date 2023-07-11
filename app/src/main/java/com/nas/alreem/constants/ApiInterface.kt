package com.nas.alreem.rest

import com.google.gson.JsonObject
import com.nas.alreem.activity.canteen.model.TimeExceedModel
import com.nas.alreem.activity.canteen.model.add_orders.CanteenItemsApiModel
import com.nas.alreem.activity.canteen.model.add_orders.CatListModel
import com.nas.alreem.activity.canteen.model.add_orders.ItemsListModel
import com.nas.alreem.activity.canteen.model.add_to_cart.*
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartApiModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartModel
import com.nas.alreem.activity.canteen.model.myorders.CancelCanteenPreOrderApiModel
import com.nas.alreem.activity.canteen.model.myorders.CancelCanteenPreorderItemId
import com.nas.alreem.activity.canteen.model.myorders.PreOrdersModel
import com.nas.alreem.activity.canteen.model.myorders.UpdateCanteenPreorderItemApiModel
import com.nas.alreem.activity.canteen.model.order_history.OrderHistoryApiModel
import com.nas.alreem.activity.canteen.model.order_history.OrderHistoryResponseModel
import com.nas.alreem.activity.canteen.model.preorder.CanteenPreorderApiModel
import com.nas.alreem.activity.canteen.model.preorder.CanteenPreorderModel
import com.nas.alreem.activity.canteen.model.topup.WalletAmountApiModel
import com.nas.alreem.activity.canteen.model.topup.WalletAmountModel
import com.nas.alreem.activity.canteen.model.wallet.WalletBalanceApiModel
import com.nas.alreem.activity.canteen.model.wallet.WalletBalanceModel
import com.nas.alreem.activity.canteen.model.wallethistory.WalletHistoryApiModel
import com.nas.alreem.activity.canteen.model.wallethistory.WalletHistoryModel
import com.nas.alreem.activity.cca.model.*
import com.nas.alreem.activity.gallery.model.*
import com.nas.alreem.activity.login.model.ForgetPasswordResponseModel
import com.nas.alreem.activity.login.model.LoginResponseModel
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.notifications.model.MessageDetailApiModel
import com.nas.alreem.activity.notifications.model.MessageDetailModel
import com.nas.alreem.activity.payments.model.InfoCanteenModel
import com.nas.alreem.activity.payments.model.PayCategoryModel
import com.nas.alreem.activity.payments.model.PaymentCategoriesApiModel
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.activity.payments.model.payment_gateway.PaymentGatewayApiModel
import com.nas.alreem.activity.payments.model.payment_gateway.PaymentGatewayModel
import com.nas.alreem.activity.payments.model.payment_submit.PaymentSubmitApiModel
import com.nas.alreem.activity.payments.model.payment_submit.PaymentSubmitModel
import com.nas.alreem.activity.payments.model.payment_token.PaymentTokenApiModel
import com.nas.alreem.activity.payments.model.payment_token.PaymentTokenModel
import com.nas.alreem.activity.permission_slip.model.PermissionResApiModel
import com.nas.alreem.activity.permission_slip.model.PermissionResponseModel
import com.nas.alreem.activity.primary.model.ComingUpResponseModel
import com.nas.alreem.activity.settings.model.TermsOfServiceResponseModel
import com.nas.alreem.activity.survey.model.*
import com.nas.alreem.fragment.about_us.model.AboutUsResponseModel
import com.nas.alreem.fragment.calendar.model.CalendarAPIModel
import com.nas.alreem.fragment.calendar.model.CalendarResponseModel
import com.nas.alreem.fragment.calendar.model.TermCalendarResponseModel
import com.nas.alreem.fragment.canteen.model.CanteenBannerResponseModel
import com.nas.alreem.fragment.cca.model.BannerResponseModelCCa
import com.nas.alreem.fragment.contact_us.model.ContactUsResponseModel
import com.nas.alreem.fragment.gallery.model.ThumnailResponseModel
import com.nas.alreem.fragment.home.model.BannerResponseModel
import com.nas.alreem.fragment.notifications.model.NotificationApiModel
import com.nas.alreem.fragment.notifications.model.NotificationResponseModel
import com.nas.alreem.fragment.parents_essentials.model.ParentsEssentialResponseModel
import com.nas.alreem.fragment.payments.model.PaymentResponseModel
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import com.nas.alreem.fragment.permission_slip.model.PermissionSlipListApiModel
import com.nas.alreem.fragment.permission_slip.model.PermissionSlipModel
import com.nas.alreem.fragment.primary.model.PrimaryResponseModel
import com.nas.alreem.fragment.settings.model.ChangePasswordApiModel

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    /******************SIGN UP*********************/
    @POST("parent-signup")
    @FormUrlEncoded
    fun signup(
        @Field("email") email: String
    ): Call<SignUpResponseModel>

    /******************SIGN UP*********************/
    @POST("parent-login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device_type") device_type: String,
        @Field("device_id") device_id: String,
        @Field("device_identifier") device_identifier: String
    ): Call<LoginResponseModel>

    /******************FORGOT PASSWORD*********************/
    @POST("forgot-password")
    @FormUrlEncoded
    fun forgetPassword(
        @Field("email") email: String
    ): Call<ForgetPasswordResponseModel>


    /*************NOTIFICATION_LIST****************/
    @POST("notification/list")
    @Headers("Content-Type: application/json")
    fun notificationList(
        @Body  notificationListApiModel: NotificationApiModel,
        @Header("Authorization") token:String
    ): Call<NotificationResponseModel>

    /*************NOTIFICATION DETAIL****************/
    @POST("notification/details")
    @Headers("Content-Type: application/json")
    fun notifictaionDetail(
        @Body  newsLetterDetailApi: MessageDetailApiModel,
        @Header("Authorization") token:String
    ): Call<MessageDetailModel>

    /*************PRIMARY_LIST****************/
    @POST("departmentprimary")
    @Headers("Content-Type: application/json")
    fun primaryList(
    ): Call<PrimaryResponseModel>
    /*************EARLY_YEARS_LIST****************/
    @POST("departmentearly")
    @Headers("Content-Type: application/json")
    fun earlyYearsList(
    ): Call<PrimaryResponseModel>

    /*************SECONDARY_LIST****************/
    @POST("departmentsecondary")
    @Headers("Content-Type: application/json")
    fun secondaryList(
    ): Call<PrimaryResponseModel>

    /*************PRIMARY_COMING_UP****************/
    @POST("primary_coming_up ")
    @Headers("Content-Type: application/json")
    fun primaryComingUp(
    ): Call<ComingUpResponseModel>

    /*************EARLY_COMING_UP****************/
    @POST("early_coming_up ")
    @Headers("Content-Type: application/json")
    fun earlyComingUp(
    ): Call<ComingUpResponseModel>

    /*************SECONDARY_COMING_UP****************/
    @POST("secondary_coming_up")
    @Headers("Content-Type: application/json")
    fun secondaryComingUp(
    ): Call<ComingUpResponseModel>

    /*************TERMS_OF_SERVICE****************/
    @POST("terms_of_service ")
    @Headers("Content-Type: application/json")
    fun termsOfService(
        @Header("Authorization") token:String
    ): Call<TermsOfServiceResponseModel>

    /*************Survey_list****************/
    @GET("survey_list")
    @Headers("Content-Type: application/json")
    fun surveyList(
        @Header("Authorization") token:String
    ): Call<SurveyListResponseModel>


    /*************Survey_detail****************/
    @POST("survey_details")
    @Headers("Content-Type: application/json")
    fun surveyDetail(
        @Body  SurveyDetailApi: SurveyDetailApiModel,
        @Header("Authorization") token:String
    ): Call<SurveyDetailResponseModel>

    /*************Surveys****************/
    @POST("surveys")
    @Headers("Content-Type: application/json")
    fun survey(
        @Body  SurveyApi: SurveyApiModel,
        @Header("Authorization") token:String
    ): Call<SurveyResponseModel>

    /*************Survey_submit****************/
    @POST("survey_submit")
    @Headers("Content-Type: application/json")
    fun surveysubmit(
        @Body  SurveysubmitApi: SurveySubmitApiModel,
        @Header("Authorization") token:String
    ): Call<SurveySubmitResponseModel>


    /*************CHANGE_PASSWORD****************/
    @POST("change-password")
    @Headers("Content-Type: application/json")
    fun changePassword(
        @Body  changePasswordApiModel: ChangePasswordApiModel,
        @Header("Authorization") token:String
    ): Call<ForgetPasswordResponseModel>
    /*************ALBUMS****************/
    @POST("gallery_albums")
    @Headers("Content-Type: application/json")
    fun albums(
        @Body  albumApi: AlbumApiModel,
        @Header("Authorization") token:String
    ): Call<AlbumResponseModel>

    /*************VIDDEO LIST****************/
    @POST("gallery_videos")
    @Headers("Content-Type: application/json")
    fun videos(
        @Body  albumApi: AlbumApiModel,
        @Header("Authorization") token:String
    ): Call<VideoResponseModel>

    /*************ALBUMS****************/
    @POST("gallery_images")
    @Headers("Content-Type: application/json")
    fun photos(
        @Body  albumApi: PhotosApiModel,
        @Header("Authorization") token:String
    ): Call<PhotosResponseModel>

    /*************DELETE_ACCOUNT****************/
    @GET("delete-account")
    @Headers("Content-Type: application/json")
    fun deleteAccount(
        @Header("Authorization") token:String
    ): Call<ForgetPasswordResponseModel>

    /*************CONTACT_US****************/
    @POST("contact_us")
    @Headers("Content-Type: application/json")
    fun contactUs(
    ): Call<ContactUsResponseModel>

    /*************ABOUT_US****************/
    @GET("about_us")
    @Headers("Content-Type: application/json")
    fun aboutUs(
    ): Call<AboutUsResponseModel>

    /*************HOME_BANNER****************/
    @POST("banner_images")
    @Headers("Content-Type: application/json")
    fun bannerImages(
    ): Call<BannerResponseModel>


    /*************PAYMENT_BANNER****************/
    @GET("payment_banner ")
    @Headers("Content-Type: application/json")
    fun paymentBanner(
        @Header("Authorization") token:String
    ): Call<PaymentResponseModel>

    /*************PAYMENT_BANNER****************/
    @GET("parent_essential ")
    @Headers("Content-Type: application/json")
    fun parentsEssential(
        @Header("Authorization") token:String
    ): Call<ParentsEssentialResponseModel>


    /*************STUDENT_LIST****************/
    @POST("student/list ")
    @Headers("Content-Type: application/json")
    fun studentList(
        @Header("Authorization") token:String
    ): Call<StudentListModel>

    /*************SEND_EMAIL_TO_STAFF****************/
    @POST("send_email_to_staff ")
    @Headers("Content-Type: application/json")
    fun sendEmailStaff(
        @Body  sendEmailApi: SendEmailApiModel,
        @Header("Authorization") token:String
    ): Call<SignUpResponseModel>

    /*************PAYMENT_CATEGORY****************/
    @POST("get_payment_categories")
    @Headers("Content-Type: application/json")
    fun payment_categories(
        @Body  paymentCategories: PaymentCategoriesApiModel,
        @Header("Authorization") token:String
    ): Call<PayCategoryModel>

    //payment token
    @POST("network_payment_gateway_access_token")
    @Headers("Content-Type: application/json")
    fun payment_token(
        @Body  paymentCategories: PaymentTokenApiModel,
        @Header("Authorization") token:String
    ): Call<PaymentTokenModel>

    //payment gateway
    @POST("network_payment_gateway_creating_an_order")
    @Headers("Content-Type: application/json")
    fun payment_gateway(
        @Body  paymentGateway: PaymentGatewayApiModel,
        @Header("Authorization") token:String
    ): Call<PaymentGatewayModel>

    //payment success
    @POST("submit_payment")
    @Headers("Content-Type: application/json")
    fun submit_payment(
        @Body  paymentGateway: PaymentSubmitApiModel,
        @Header("Authorization") token:String
    ): Call<PaymentSubmitModel>

    //gallery thumnail
    @POST("thumbnail_images")
    @Headers("Content-Type: application/json")
    fun galleryThumbNail(
        @Header("Authorization") token:String
    ): Call<ThumnailResponseModel>

    /*************CALENDAR****************/
    @POST("calendar")
    @Headers("Content-Type: application/json")
    fun calendar(
      //  @Body calendarModel: CalendarAPIModel,
        @Header("Authorization") token: String
    ): Call<CalendarResponseModel>

    @POST("term_calendar")
    @Headers("Content-Type: application/json")
    fun termCalendar(
        @Header("Authorization") token: String
    ): Call<TermCalendarResponseModel>


    @GET("payment_information")
    @Headers("Content-Type: application/json")
    fun getPaymentInformation(
        //@Body  ptaConfirmationModel: PtaConfirmationApiModel,
        @Header("Authorization") token:String
    ): Call<InfoCanteenModel>


    @GET("canteen_banner")
    @Headers("Content-Type: application/json")
    fun get_canteen_banner(
        //@Body  ptaConfirmationModel: PtaConfirmationApiModel,
        @Header("Authorization") token:String
    ): Call<CanteenBannerResponseModel>

    @POST("get_canteen_preorder_history")
    @Headers("Content-Type: application/json")
    fun canteen_order_history(
        @Body  orderHistory: OrderHistoryApiModel,
        @Header("Authorization") token:String
    ): Call<OrderHistoryResponseModel>

    @POST("get_canteen_preorder")
    @Headers("Content-Type: application/json")
    fun canteen_myorder_history(
        @Body  orderHistory: OrderHistoryApiModel,
        @Header("Authorization") token:String
    ): Call<PreOrdersModel>

    @POST("cancel_canteen_preorder")
    @Headers("Content-Type: application/json")
    fun cancelCanteenPreOrder(
        @Body  orderHistory: CancelCanteenPreOrderApiModel,
        @Header("Authorization") token:String
    ): Call<CanteenPreorderModel>

    @POST("cancel_canteen_preorder_item")
    @Headers("Content-Type: application/json")
    fun cancelCanteenPreOrderItem(
        @Body  orderHistory: CancelCanteenPreorderItemId,
        @Header("Authorization") token:String
    ): Call<CanteenPreorderModel>

    @POST("edit_canteen_preorder_item")
    @Headers("Content-Type: application/json")
    fun updateCanteenPreOrderItem(
        @Body  orderHistory: UpdateCanteenPreorderItemApiModel,
        @Header("Authorization") token:String
    ): Call<CanteenPreorderModel>



    @GET("canteen_information")
    @Headers("Content-Type: application/json")
    fun getCanteenInformation(
        //@Body  ptaConfirmationModel: PtaConfirmationApiModel,
        @Header("Authorization") token:String
    ): Call<com.nas.alreem.activity.canteen.model.information.InfoCanteenModel>

    @GET("get_canteen_categories")
    @Headers("Content-Type: application/json")
    fun get_canteen_categories(
        //@Body  ptaConfirmationModel: PtaConfirmationApiModel,
        @Header("Authorization") token:String
    ): Call<CatListModel>

    //canteen items
    @POST("get_canteen_items")
    @Headers("Content-Type: application/json")
    fun get_canteen_items(
        @Body  canteenItems: CanteenItemsApiModel,
        @Header("Authorization") token:String
    ): Call<ItemsListModel>

    @POST("add_to_canteen_cart")
    @Headers("Content-Type: application/json")
    fun add_to_canteen_cart(
        @Body  addToCartCanteen: AddToCartCanteenApiModel,
        @Header("Authorization") token:String
    ): Call<AddToCartCanteenModel>

    @POST("get_canteen_cart")
    @Headers("Content-Type: application/json")
    fun get_canteen_cart(
        @Body  canteenCart: CanteenCartApiModel,
        @Header("Authorization") token:String
    ): Call<CanteenCartModel>

    @POST("update_canteen_cart")
    @Headers("Content-Type: application/json")
    fun update_canteen_cart(
        @Body  updatecanteenCart: CanteenCartUpdateApiModel,
        @Header("Authorization") token:String
    ): Call<CanteenCartUpdateModel>

    @POST("remove_canteen_cart")
    @Headers("Content-Type: application/json")
    fun remove_canteen_cart(
        @Body  removecanteenCart: CanteenCartRemoveApiModel,
        @Header("Authorization") token:String
    ): Call<CanteenCartRemoveModel>

    @POST("get_wallet_balance")
    @Headers("Content-Type: application/json")
    fun get_wallet_balance(
        @Body  walletbalance: WalletBalanceApiModel,
        @Header("Authorization") token:String
    ): Call<WalletBalanceModel>
    @POST("canteen_preorder")
    @Headers("Content-Type: application/json")
    fun canteen_preorder(
        @Body  canteenpreorder: CanteenPreorderApiModel,
        @Header("Authorization") token:String
    ): Call<CanteenPreorderModel>

    @POST("get_wallet_history")
    @Headers("Content-Type: application/json")
    fun get_wallet_history(
        @Body  wallethistory: WalletHistoryApiModel,
        @Header("Authorization") token:String
    ): Call<WalletHistoryModel>

    @GET("time_exceed_status")
    @Headers("Content-Type: application/json")
    fun time_exceed_status(
        @Header("Authorization") token:String
    ): Call<TimeExceedModel>

    @POST("wallet_topup")
    @Headers("Content-Type: application/json")
    fun wallet_topup(
        @Body  walletamount: WalletAmountApiModel,
        @Header("Authorization") token:String
    ): Call<WalletAmountModel>

    /*************PERMISSIONSLIP LIST****************/
    @POST("permission-slips")
    @Headers("Content-Type: application/json")
    fun permissnslipList(
        @Body  permissionSlipListModel: PermissionSlipListApiModel,
        @Header("Authorization") token:String
    ): Call<PermissionSlipModel>

    /*************PERMISSION SLIP RESPONSE****************/
    @POST("permission-slip-status-update")
    @Headers("Content-Type: application/json")
    fun permsnlistResponse(
        @Body  pickupListApiModel: PermissionResApiModel,
        @Header("Authorization") token:String
    ): Call<PermissionResponseModel>


    @GET("cca-banner")
    @Headers("Content-Type: application/json")
    fun getBanner(
        @Header("Authorization") token: String
    ): Call<BannerResponseModelCCa>

    /* CCA INFO*/
    @POST("cca-informations")
    @Headers("Content-Type: application/json")
    fun getCCAInfo(
        @Body body: CCAInfoRequestModel,
        @Header("Authorization") token: String
    )
            : Call<CCAInfoResponseModel>

    /* CCA INFO*/

    @POST("cca-details")
    @Headers("Content-Type: application/json")
    fun getCCAList(
        @Body body: CCAListRequestModel,
        @Header("Authorization") token: String
    ): Call<CCAListResponseModel>

    /*CCA SUBMIT*/
    @POST("cca-submit")
    @Headers("Content-Type: application/json")
    fun ccaSubmit(
        @Body body: CCASumbitRequestModel,
        @Header("Authorization") token: String
    ): Call<CCASubmitResponseModel>

    @POST("cca-reviews")
    @Headers("Content-Type: application/json")
    fun ccaReview(
        @Body body: CCAReviewRequestModel,
        @Header("Authorization") token: String
    ): Call<CCAReviewResponseModel>

    @POST("cca-selection-cancel")
    @Headers("Content-Type: application/json")
    fun ccaCancel(
        @Body body: CCACancelRequestModel,
        @Header("Authorization") token: String
    ): Call<CCACancelResponseModel>


    @POST("external_providers")
    @Headers("Content-Type: application/json")
    fun getExternalProviders(
        @Body body: ExternalProvidersRequestModel,
        @Header("Authorization") token: String
    ): Call<ExternalProvidersResponseModel>
}