package com.nas.alreem.constants

import com.google.gson.JsonObject
import com.nas.alreem.activity.absence.model.AbsenceListModel
import com.nas.alreem.activity.absence.model.EarlyPickupModel
import com.nas.alreem.activity.absence.model.ListAbsenceApiModel
import com.nas.alreem.activity.absence.model.ListPickupApiModel
import com.nas.alreem.activity.absence.model.PickupListModel
import com.nas.alreem.activity.absence.model.RequestLeaveApiModel
import com.nas.alreem.activity.absence.model.RequestLeaveModel
import com.nas.alreem.activity.absence.model.RequestPickupApiModel
import com.nas.alreem.activity.bus_service.model.DetailsResponseModel
import com.nas.alreem.activity.bus_service.model.RegularBusSubmitModel
import com.nas.alreem.activity.bus_service.model.StudentDetailsModel
import com.nas.alreem.activity.bus_service.requestservice.model.RequestBusServiceListModel
import com.nas.alreem.activity.bus_service.requestservice.model.RequestServiceApiModel
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
import com.nas.alreem.activity.communication.information.model.InformationResponseModel
import com.nas.alreem.activity.communication.newesletters.model.NewsletterResponseModel
import com.nas.alreem.activity.gallery.model.*
import com.nas.alreem.activity.home.model.ReEnrollSubmitAPIModel
import com.nas.alreem.activity.login.model.ForgetPasswordResponseModel
import com.nas.alreem.activity.login.model.LoginResponseModel
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.lost_card.model.GetShopCartResponseModel
import com.nas.alreem.activity.lost_card.model.LostCardHistoryResponseModel
import com.nas.alreem.activity.lost_card.model.LostCardIntructionResponseModel
import com.nas.alreem.activity.lost_card.model.ShopHistoryModel
import com.nas.alreem.activity.lost_card.model.StudentLostCardResponseModel
import com.nas.alreem.activity.notifications.model.MessageDetailApiModel
import com.nas.alreem.activity.notifications.model.MessageDetailModel
import com.nas.alreem.activity.parent_engagement.model.ChatterBoxResponseModel
import com.nas.alreem.activity.parent_engagement.model.ClassRepresentativeListResponseModel
import com.nas.alreem.activity.parent_engagement.model.ParentAssociationEventResponseModel
import com.nas.alreem.activity.parent_engagement.model.ParentAssociationResponseModel
import com.nas.alreem.activity.parent_engagement.model.VolunteerSubmitResponseModel
import com.nas.alreem.activity.parent_meetings.model.PtaConfirmApiModel
import com.nas.alreem.activity.parent_meetings.model.PtaConfirmModel
import com.nas.alreem.activity.parent_meetings.model.PtaDatesApiModel
import com.nas.alreem.activity.parent_meetings.model.PtaDatesModel
import com.nas.alreem.activity.parent_meetings.model.PtaInsertApiModel
import com.nas.alreem.activity.parent_meetings.model.PtaInsertModel
import com.nas.alreem.activity.parent_meetings.model.PtaListApiModel
import com.nas.alreem.activity.parent_meetings.model.PtaListModel
import com.nas.alreem.activity.parent_meetings.model.PtaReviewListResponseModel
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
import com.nas.alreem.activity.shop.model.PaymentGatewayApiModelShop
import com.nas.alreem.activity.shop_new.model.ShopHistoryItemResponseModel
import com.nas.alreem.activity.shop_new.model.AddToCartShopApiModel
import com.nas.alreem.activity.shop_new.model.ItemsListModel_new
import com.nas.alreem.activity.shop_new.model.ShopCartRemoveApiModel
import com.nas.alreem.activity.shop_new.model.ShopCartUpdateApiModel
import com.nas.alreem.activity.shop_new.model.ShopHistoryResponseModel
import com.nas.alreem.activity.shop_new.model.ShopItemsApiModel
import com.nas.alreem.activity.shop_new.model.StudentShopCardResponseModel
import com.nas.alreem.activity.staff_directory.model.ListStaffDetailApiModel
import com.nas.alreem.activity.staff_directory.model.ListStaffDetailModel
import com.nas.alreem.activity.staff_directory.model.StaffCatListResponseModel
import com.nas.alreem.activity.survey.model.*
import com.nas.alreem.fragment.about_us.model.AboutUsResponseModel
import com.nas.alreem.fragment.bus_service.model.BannerModel
import com.nas.alreem.fragment.bus_service.model.BusserviceResponseModel
import com.nas.alreem.fragment.bus_service.model.RequestBusServiceModelSubmit
import com.nas.alreem.fragment.calendar.model.CalendarResponseModel
import com.nas.alreem.fragment.calendar.model.TermCalendarResponseModel
import com.nas.alreem.fragment.canteen.model.CanteenBannerResponseModel
import com.nas.alreem.fragment.cca.model.BannerResponseModelCCa
import com.nas.alreem.fragment.communication.model.CommunicationBannerResponseModel
import com.nas.alreem.fragment.communication.model.CommunicationResponseModel
import com.nas.alreem.fragment.communication.model.SocialMediaResponseModel
import com.nas.alreem.fragment.contact_us.model.ContactUsResponseModel
import com.nas.alreem.fragment.gallery.model.ThumnailResponseModel
import com.nas.alreem.fragment.home.model.BadgeResponseModel
import com.nas.alreem.fragment.home.model.BannerResponseModel
import com.nas.alreem.fragment.home.re_enrollment.EnrollmentHelpResponseModel
import com.nas.alreem.fragment.home.re_enrollment.EnrollmentSaveResponseModel
import com.nas.alreem.fragment.home.re_enrollment.ReEnrollmentFormResponseModel
import com.nas.alreem.fragment.home.re_enrollment.ReEnrollmentStatusResponseModel
import com.nas.alreem.fragment.intention.model.IntentionApiModel
import com.nas.alreem.fragment.intention.model.IntentionApiSubmit
import com.nas.alreem.fragment.intention.model.IntentionListAPIResponseModel
import com.nas.alreem.fragment.intention.model.IntentionStatusApiModel
import com.nas.alreem.fragment.intention.model.IntentionStatusResponseModel
import com.nas.alreem.fragment.notifications.model.NotificationApiModel
import com.nas.alreem.fragment.notifications.model.NotificationResponseModel
import com.nas.alreem.fragment.parent_meetings.model.ListStaffPtaApiModel
import com.nas.alreem.fragment.parent_meetings.model.ListStaffPtaModel
import com.nas.alreem.fragment.parents_essentials.model.ParentsEssentialResponseModel
import com.nas.alreem.fragment.payments.model.PaymentResponseModel
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import com.nas.alreem.fragment.performing_arts.model.PerformingResponseModel
import com.nas.alreem.fragment.permission_slip.model.GeneralFormModel
import com.nas.alreem.fragment.permission_slip.model.PermissionSlipListApiModel
import com.nas.alreem.fragment.permission_slip.model.PermissionSlipModel
import com.nas.alreem.fragment.primary.model.PrimaryResponseModel
import com.nas.alreem.fragment.reports.model.ReportApiModel
import com.nas.alreem.fragment.reports.model.ReportListModel
import com.nas.alreem.fragment.settings.model.ChangePasswordApiModel
import com.nas.alreem.fragment.student_information.model.StudentInfoApiModel
import com.nas.alreem.fragment.student_information.model.StudentInfoModel
import com.nas.alreem.fragment.time_table.model.apimodel.TimeTableApiDataModel
import com.nas.alreem.fragment.time_table.model.apimodel.TimeTableApiModel
import com.nas.alreem.fragment.time_table_new.model.TimeTableApiDataModelNew
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
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

    /*************TIMETABLE****************/
    @POST("timetable")
    @Headers("Content-Type: application/json")
    fun timetable(
        @Body  calendarBody: TimeTableApiModel,
        @Header("Authorization") token:String,
        ): Call<TimeTableApiDataModel>
    @POST("timetable-isams")
    @Headers("Content-Type: application/json")
    fun timetable_isams(
        @Body  calendarBody: TimeTableApiModel,
        @Header("Authorization") token:String,
    ): Call<TimeTableApiDataModelNew>

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
    @POST("network_payment_gateway_creating_an_order")
    @Headers("Content-Type: application/json")
    fun payment_gateway_shop(
        @Body  paymentGateway: PaymentGatewayApiModelShop,
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

    @POST("request-early-pickup")
    @Headers("Content-Type: application/json")
    fun pickupRequest(
        @Body  requestPickupApiModel: RequestPickupApiModel,
        @Header("Authorization") token:String
    ): Call<EarlyPickupModel>

    @POST("list-early-pickup")
    @Headers("Content-Type: application/json")
    fun pickUplist(
        @Body listPickupApiModel: ListPickupApiModel,
        @Header("Authorization") token: String
    ): Call<PickupListModel>

    @POST("request-leave")
    @Headers("Content-Type: application/json")
    fun leaveRequest(
        @Body  requestleaveApiModel: RequestLeaveApiModel,
        @Header("Authorization") token:String
    ): Call<RequestLeaveModel>

    @POST("list-leave")
    @Headers("Content-Type: application/json")
    fun absencelist(
        @Body  absenceApiModel: ListAbsenceApiModel,
        @Header("Authorization") token:String
    ): Call<AbsenceListModel>

    @GET("staff-departments")
    @Headers("Content-Type: application/json")
    fun staff_depatrtments(
        @Header("Authorization") token:String
    ): Call<StaffCatListResponseModel>

    @POST("department-staff-list")
    @Headers("Content-Type: application/json")
    fun staff_detail_list(
        @Body  staffListApiModel: ListStaffDetailApiModel,
        @Header("Authorization") token:String
    ): Call<ListStaffDetailModel>

    @POST("staff-list")
    @Headers("Content-Type: application/json")
    fun staff_list_pta(
        @Body  staffListPtaApiModel: ListStaffPtaApiModel,
        @Header("Authorization") token:String
    ): Call<ListStaffPtaModel>


    @POST("pta-allotted-dates")
    @Headers("Content-Type: application/json")
    fun pta_allotted_dates(
        @Body  ptaDatesApiModel: PtaDatesApiModel,
        @Header("Authorization") token:String
    ): Call<PtaDatesModel>

    @POST("pta-list")
    @Headers("Content-Type: application/json")
    fun pta_list(
        @Body  ptaListApiModel: PtaListApiModel,
        @Header("Authorization") token:String
    ): Call<PtaListModel>

    @POST("pta-insert")
    @Headers("Content-Type: application/json")
    fun pta_insert(
        @Body  ptaInsertApiModel: PtaInsertApiModel,
        @Header("Authorization") token:String
    ): Call<PtaInsertModel>

    @POST("pta-confirmation")
    @Headers("Content-Type: application/json")
    fun pta_confirm(
        @Body  ptaConfirmApiModel: PtaConfirmApiModel,
        @Header("Authorization") token:String
    ): Call<PtaConfirmModel>

    @GET("pta-review-list")
    @Headers("Content-Type: application/json")
    fun ptaReviewList(
        @Header("Authorization") token:String
    ): Call<PtaReviewListResponseModel>

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

    /*************General_Forms****************/
    @POST("general-forms")
    @Headers("Content-Type: application/json")
    fun generalforms
    (
        @Body  permissionSlipListModel: PermissionSlipListApiModel,
        @Header("Authorization") token:String
    ): Call<GeneralFormModel>
    @GET("cca-banner")
    @Headers("Content-Type: application/json")
    fun getBanner(
        @Header("Authorization") token: String
    ): Call<BannerResponseModelCCa>

    @POST("bus_service_info_banner_images")
    @Headers("Content-Type: application/json")
    fun getBusServiceBanner(
        @Header("Authorization") token: String
    ): Call<BannerModel>

    /* CCA INFO*/
    @POST("cca-informations")
    @Headers("Content-Type: application/json")
    fun getCCAInfo(
        @Body body: CCAInfoRequestModel,
        @Header("Authorization") token: String
    )
            : Call<CCAInfoResponseModel>
  @GET("bus-service-information")
    @Headers("Content-Type: application/json")
    fun getBusServiceInfo(

        @Header("Authorization") token: String
    )
            : Call<InfoCanteenModel>

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

    /*CCA readstatus*/
    @POST("cca-read-status-update")
    @Headers("Content-Type: application/json")
    fun readstatusupdate(
        @Body body: CCAReadStatusRequestModel,
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

    @POST("cca-edit")
    @Headers("Content-Type: application/json")
    fun ccaedit(
        @Body body: CCAEditRequestModel,
        @Header("Authorization") token: String
    ): Call<CCASubmitResponseModel>

    @POST("cca-reserve")
    @Headers("Content-Type: application/json")
    fun ccareserve(
        @Body body: CCAReservetRequestModel,
        @Header("Authorization") token: String
    ): Call<CCASubmitResponseModel>

    @POST("cca-reserve-cancel")
    @Headers("Content-Type: application/json")
    fun ccareservecancel(
        @Body body: CCACancelModel,
        @Header("Authorization") token: String
    ): Call<CCASubmitResponseModel>

    @POST("external-providers")
    @Headers("Content-Type: application/json")
    fun getExternalProviders(
        @Body body: ExternalProvidersRequestModel,
        @Header("Authorization") token: String
    ): Call<ExternalProvidersResponseModel>

    @POST("progress-reports")
    @Headers("Content-Type: application/json")
    fun reportList(
        @Body reportListModel: ReportApiModel,
        @Header("Authorization") token:String
    ): Call<ReportListModel>
    @POST("student/info")
    @Headers("Content-Type: application/json")
    fun studentInfo(
        @Body  studentbody: StudentInfoApiModel,
        @Header("Authorization") token:String
    ): Call<StudentInfoModel>

    @POST("intension")
    @Headers("Content-Type: application/json")
    fun intension(
        @Body intentionbody: IntentionApiModel,
        @Header("Authorization") token: String
    ): Call<IntentionListAPIResponseModel>

    @POST("intension-status")
    @Headers("Content-Type: application/json")
    fun intensionstatus
    (
        @Body  intentionbody: IntentionStatusApiModel,
        @Header("Authorization") token:String
    ): Call<IntentionStatusResponseModel>

    @POST("intension-status-update")
    @Headers("Content-Type: application/json")
    fun intensionstatusupdate
                (
        @Body  intentionbody: IntentionApiSubmit,
        @Header("Authorization") token:String
    ): Call<StudentInfoModel>

    @POST("list-bus-service")
    @Headers("Content-Type: application/json")
    fun listbusservice
                (
        @Body absenceApiModel: ListAbsenceApiModel,
        @Header("Authorization") token:String
    ): Call<BusserviceResponseModel>

    @POST("list-bus-service-request")
    @Headers("Content-Type: application/json")
    fun requestBusServiceList
                (
        @Body absenceApiModel: ListAbsenceApiModel,
        @Header("Authorization") token:String
    ): Call<RequestBusServiceListModel>

    @POST("request-bus-service")
    @Headers("Content-Type: application/json")
    fun requestbusservice(
        @Body requestBusservicemodelsubmit: RequestBusServiceModelSubmit,
        @Header("Authorization") token:String
    ): Call<EarlyPickupModel>

    @POST("bus-service-request")
    @Headers("Content-Type: application/json")
    fun requestService(
        @Body requestBusservicemodelsubmit: RequestServiceApiModel,
        @Header("Authorization") token:String
    ): Call<EarlyPickupModel>
    @POST("get_lost_card_instruction")
    @Headers("Content-Type: application/json")
    fun get_lost_card_instruction(
        @Header("Authorization") token:String
    ): Call<LostCardIntructionResponseModel>
    @POST("student_lost_card_history")
    @Headers("Content-Type: application/json")
    fun student_lost_card_history(
        @Body  studentbody: StudentInfoApiModel,
        @Header("Authorization") token:String
    ): Call<LostCardHistoryResponseModel>

    @POST("student_lost_card")
    @Headers("Content-Type: application/json")
    fun student_lost_card(
        @Header("Authorization") token: String,
        @Body json: JsonObject
    ): Call<StudentLostCardResponseModel>

    @GET("get_shop_categories")
    @Headers("Content-Type: application/json")
    fun get_shop_categories(
        //@Body  ptaConfirmationModel: PtaConfirmationApiModel,
        @Header("Authorization") token:String
    ): Call<CatListModel>

    @POST("get_shop_items")
    @Headers("Content-Type: application/json")
    fun get_shop_items(
        @Body  canteenItems: ShopItemsApiModel,
        @Header("Authorization") token:String
    ): Call<ItemsListModel_new>

    @POST("add_to_shop_cart")
    @Headers("Content-Type: application/json")
    fun add_to_shop_cart(
        @Body  addToCartCanteen: AddToCartShopApiModel,
        @Header("Authorization") token:String
    ): Call<AddToCartCanteenModel>

    @POST("get_shop_cart")
    @Headers("Content-Type: application/json")
    fun get_shop_cart(
        @Body  canteenCart: CanteenCartApiModel,
        @Header("Authorization") token:String
    ): Call<GetShopCartResponseModel>

    @POST("update_shop_cart")
    @Headers("Content-Type: application/json")
    fun update_shop_cart(
        @Body  updatecanteenCart: ShopCartUpdateApiModel,
        @Header("Authorization") token:String
    ): Call<CanteenCartUpdateModel>

    @POST("remove_shop_cart")
    @Headers("Content-Type: application/json")
    fun remove_shop_cart(
        @Body  removecanteenCart: ShopCartRemoveApiModel,
        @Header("Authorization") token:String
    ): Call<CanteenCartRemoveModel>
    @POST("get_shop_order")
    @Headers("Content-Type: application/json")
    fun get_shop_order(
        @Body  canteenpreorder: CanteenPreorderApiModel,
        @Header("Authorization") token:String
    ): Call<CanteenPreorderModel>


    @GET("get_enrollment_status")
    @Headers("Content-Type: application/json")
    fun getenrollstatus(@Header("Authorization") token: String): Call<ReEnrollmentStatusResponseModel>

    @POST("get_enrollment_form")
    @Headers("Content-Type: application/json")
    fun getenrollform(
        @Header("Authorization") token: String,
    ): Call<ReEnrollmentFormResponseModel>

    @POST("save_re_enrollment")
    @Headers("Content-Type: application/json")
    fun getenrollsave(
        @Header("Authorization") token: String,
        @Body savereenrollmentModel: ReEnrollSubmitAPIModel
    ): Call<EnrollmentSaveResponseModel>

    @POST("re_enrollment_help")
    @Headers("Content-Type: application/json")
    fun getenrollhelp(
        @Header("Authorization") token: String,
        @Body json: JsonObject?
    ): Call<EnrollmentHelpResponseModel>

    @POST("performing_arts")
    @Headers("Content-Type: application/json")
    fun performing_arts(
        @Header("Authorization") token: String
    ): Call<PerformingResponseModel>

    @POST("communication_banner_images")
    @Headers("Content-Type: application/json")
    fun communication_banner_images(
        @Header("Authorization") token: String
    ): Call<CommunicationBannerResponseModel>

    @POST("communication_info")
    @Headers("Content-Type: application/json")
    fun communication_info(
        @Header("Authorization") token: String
    ): Call<InformationResponseModel>

    @POST("whole_school_coming_up")
    @Headers("Content-Type: application/json")
    fun whole_school_coming_up(
        @Header("Authorization") token: String
    ): Call<com.nas.alreem.activity.communication.commingup.model.ComingUpResponseModel>

    @POST("newsletter_categories")
    @Headers("Content-Type: application/json")
    fun newsletter_categories(
        @Header("Authorization") token: String
    ): Call<NewsletterResponseModel>
    @POST("newsletter")
    @Headers("Content-Type: application/json")
    fun newsletter(
        @Header("Authorization") token: String,
        @Body json: JsonObject?
    ): Call<CommunicationResponseModel>
    @POST("social_media")
    @Headers("Content-Type: application/json")
    fun social_media(
        @Header("Authorization") token: String
    ): Call<SocialMediaResponseModel>

    @POST("shop-order-submit")
    @Headers("Content-Type: application/json")
    fun shop_order_submit
    (
        @Header("Authorization") token: String,
        @Body json: JsonObject
    ): Call<StudentShopCardResponseModel>

    @POST("get_shop_orders_history")
    @Headers("Content-Type: application/json")
    fun get_shop_orders_history(
        @Body  studentbody: ShopHistoryModel,
        @Header("Authorization") token:String
    ): Call<ShopHistoryResponseModel>

    @POST("get_shop_items_history")
    @Headers("Content-Type: application/json")
    fun get_shop_items_history(
        @Body  studentbody: ShopHistoryModel,
        @Header("Authorization") token:String
    ): Call<ShopHistoryItemResponseModel>
    @GET("shop_banner")
    @Headers("Content-Type: application/json")
    fun shop_banner(

        @Header("Authorization") token:String
    ): Call<CanteenBannerResponseModel>

    @POST("badge_counts")
    @Headers("Content-Type: application/json")
    fun badge_counts(

        @Header("Authorization") token:String
    ): Call<BadgeResponseModel>



    @POST("status_changeAPI")
    @Headers("Content-Type: application/json")
    fun status_changeAPI(
        @Header("Authorization") token: String?,
        @Body json: JsonObject?
    ): Call<StudentShopCardResponseModel>

    @POST("chatter_box")
    @Headers("Content-Type: application/json")
    fun chatter_box(
        //@Body  ptaConfirmationModel: PtaConfirmationApiModel,
        @Header("Authorization") token:String
    ): Call<ChatterBoxResponseModel>

    @POST("parents_association")
    @Headers("Content-Type: application/json")
    fun parents_association(
        //@Body  ptaConfirmationModel: PtaConfirmationApiModel,
        @Header("Authorization") token:String
    ): Call<ParentAssociationResponseModel>


    @POST("class_representative")
    @Headers("Content-Type: application/json")
    fun class_representative(
        //@Body  ptaConfirmationModel: PtaConfirmationApiModel,
        @Header("Authorization") token:String
    ): Call<ClassRepresentativeListResponseModel>

    @POST("parent_assoc_events")
    @Headers("Content-Type: application/json")
    fun parent_assoc_events(
        @Header("Authorization") token:String,
        @Body json: JsonObject?
    ): Call<ParentAssociationEventResponseModel>

    @POST("parent_assoc_events_attending_or_not_new")
    @Headers("Content-Type: application/json")
    fun parent_assoc_events_attending_or_not_new(
        @Header("Authorization") token:String,
        @Body json: JsonObject?
    ): Call<VolunteerSubmitResponseModel>

    @POST("student_details")
    @Headers("Content-Type: application/json")
    fun student_details(
        @Header("Authorization") token:String,
        @Body  studentdetailsmodel: StudentDetailsModel,
    ): Call<DetailsResponseModel>
    @Multipart
    @POST("request_for_bus_service")
    @Headers("Content-Type: application/json")
    fun request_for_bus_service(
        @Header("Authorization") token:String,
        @Part("student_id") student_id: RequestBody?,
        @Part("pickup") pickup: RequestBody?,
        @Part("drop") drop: RequestBody?,
        @Part("unique_id") unique_id: RequestBody?,
        @Part("class_name") class_name: RequestBody?,
        @Part("parent1_name") parent1_name: RequestBody?,
        @Part("parent1_email") parent1_email: RequestBody?,
        @Part("parent1_relationship") parent1_relationship: RequestBody?,
        @Part("parent1_mobile") parent1_mobile: RequestBody?,
        @Part("parent1_additionaltelephone") parent1_additionaltelephone: RequestBody?,
        @Part("parent1_country") parent1_country: RequestBody?,
        @Part("parent1_address") parent1_address: RequestBody?,
        @Part("term") term: RequestBody?,
        @Part("requested_date") requested_date: RequestBody?,
        @Part("device_type") device_type: RequestBody?,
        @Part("device_name") device_name: RequestBody?,
        @Part("app_version") app_version: RequestBody?,

       // @Body  regularbusSubmitmodel: RegularBusSubmitModel,
        @Part image: MultipartBody.Part?
    ): Call<ResponseBody>

}