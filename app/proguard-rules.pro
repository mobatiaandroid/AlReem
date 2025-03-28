# Keep JetBrains annotations
-keep class org.jetbrains.annotations.** { *; }

# Keep OkHttp classes (if still facing issues)
-keep class okhttp3.** { *; }
-keep class com.squareup.okhttp.** { *; }

# Keep Picasso classes (if needed)
-keep class com.squareup.picasso.** { *; }

# Keep Retrofit API interfaces
-keep interface * { *; }

# Keep Retrofit and OkHttp3 classes
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }

# Keep generic type arguments for Retrofit
-keepattributes Signature
-keepattributes *Annotation*

# Prevent obfuscation of class names for Gson serialization/deserialization
-keep class com.google.gson.** { *; }

# Keep fields of model classes
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Firebase
-dontwarn com.google.firebase.**
-keep class com.google.firebase.** { *; }
-keepattributes *Annotation*
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# Play Services
-dontwarn com.google.android.gms.**
-keep class com.google.android.gms.** { *; }

# Glide
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep class com.bumptech.glide.** { *; }
-dontwarn com.bumptech.glide.**

# AndroidX Security Crypto
-keep class androidx.security.** { *; }
-dontwarn androidx.security.**

# BouncyCastle Security Libraries
-keep class org.bouncycastle.** { *; }
-dontwarn org.bouncycastle.**

# Conscrypt
-keep class org.conscrypt.** { *; }
-dontwarn org.conscrypt.**

# OpenJSSE
-keep class org.openjsse.** { *; }
-dontwarn org.openjsse.**

# Material Calendar View
-keep class com.applandeo.** { *; }
-dontwarn com.applandeo.**

# Android PDF Viewer
-keep class com.github.barteksc.pdfviewer.** { *; }
-dontwarn com.github.barteksc.pdfviewer.**

# PRDownloader
-keep class com.mindorks.android.prdownloader.** { *; }
-dontwarn com.mindorks.android.prdownloader.**

# ShortcutBadger
-keep class me.leolin.shortcutbadger.** { *; }
-dontwarn me.leolin.shortcutbadger.**

# Elegant Number Button
-keep class com.cepheuen.elegantnumberbutton.** { *; }
-dontwarn com.cepheuen.elegantnumberbutton.**

# RootBeer Library
-keep class com.scottyab.rootbeer.** { *; }
-dontwarn com.scottyab.rootbeer.**

# Tooltips Library
-keep class com.ryanharter.android.tooltips.** { *; }
-dontwarn com.ryanharter.android.tooltips.**

# Signature Pad
-keep class com.github.gcacace.signaturepad.** { *; }
-dontwarn com.github.gcacace.signaturepad.**

# Payment SDK (Network International)
-keep class com.network.** { *; }
-dontwarn com.network.**

# Samsung Payment SDK
-keep class com.network.samsungpay.** { *; }
-dontwarn com.network.samsungpay.**

# Mockito (For Testing)
-dontwarn org.mockito.**
-keep class org.mockito.** { *; }

# General Keep Rules
-keepattributes *Annotation*
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keepclassmembers class * {
    @androidx.annotation.Keep <methods>;
}

-keep class com.nas.alreem.activity.absence.model.AbsenceListModel { *; }
-keep class com.nas.alreem.activity.absence.model.AbsenceListResponseModel { *; }
-keep class com.nas.alreem.activity.absence.model.AbsenceRequestListModel { *; }
-keep class com.nas.alreem.activity.absence.model.EarlyPickupListArray { *; }
-keep class com.nas.alreem.activity.absence.model.EarlyPickupModel { *; }
-keep class com.nas.alreem.activity.absence.model.EarlyPickupResponseArray { *; }
-keep class com.nas.alreem.activity.absence.model.ListAbsenceApiModel { *; }
-keep class com.nas.alreem.activity.absence.model.ListPickupApiModel { *; }
-keep class com.nas.alreem.activity.absence.model.PickupListModel { *; }
-keep class com.nas.alreem.activity.absence.model.RequestLeaveApiModel { *; }
-keep class com.nas.alreem.activity.absence.model.RequestLeaveModel { *; }
-keep class com.nas.alreem.activity.absence.model.RequestPickupApiModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAAttendanceModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCACancelModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCACancelRequestModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCACancelResponseModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAchoiceModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCADetailModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAEditModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAEditRequestModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAInfoRequestModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAInfoResponseModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCaInformationList { *; }
-keep class com.nas.alreem.activity.cca.model.CCAListRequestModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAListResponseModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAReadStatusRequestModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAReservetRequestModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAReviewAfterSubmissionModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAReviewRequestModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAReviewReservedResponseModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAReviewResevedModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCAReviewResponseModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCASubmitResponseModel { *; }
-keep class com.nas.alreem.activity.cca.model.CCASumbitRequestModel { *; }
-keep class com.nas.alreem.activity.cca.model.ExternalProvidersRequestModel { *; }
-keep class com.nas.alreem.activity.cca.model.ExternalProvidersResponseModel { *; }
-keep class com.nas.alreem.activity.cca.model.WeekListModel { *; }
-keep class com.nas.alreem.activity.communication.commingup.model.ComingUpResponseModel { *; }
-keep class com.nas.alreem.activity.communication.information.model.InformationResponseModel { *; }
-keep class com.nas.alreem.activity.communication.newesletters.model.NewsLetterModel { *; }
-keep class com.nas.alreem.activity.communication.newesletters.model.NewsletterResponseArrayModel { *; }
-keep class com.nas.alreem.activity.communication.newesletters.model.NewsletterResponseModel { *; }
-keep class com.nas.alreem.activity.communication.socialmedia.model.SocialMediaModel { *; }
-keep class com.nas.alreem.activity.communication.socialmedia.model.SocialMediaResponseArrayModel { *; }
-keep class com.nas.alreem.activity.gallery.model.AlbumApiModel { *; }
-keep class com.nas.alreem.activity.gallery.model.AlbumImageModel { *; }
-keep class com.nas.alreem.activity.gallery.model.AlbumResponseArrayModel { *; }
-keep class com.nas.alreem.activity.gallery.model.AlbumResponseModel { *; }
-keep class com.nas.alreem.activity.gallery.model.PhotosApiModel { *; }
-keep class com.nas.alreem.activity.gallery.model.PhotosModel { *; }
-keep class com.nas.alreem.activity.gallery.model.PhotosResponseArrayModel { *; }
-keep class com.nas.alreem.activity.gallery.model.PhotosResponseModel { *; }
-keep class com.nas.alreem.activity.gallery.model.VideoModel { *; }
-keep class com.nas.alreem.activity.gallery.model.VideoResponseArrayModel { *; }
-keep class com.nas.alreem.activity.gallery.model.VideoResponseModel { *; }
-keep class com.nas.alreem.activity.home.model.ReEnrollSubmitAPIModel { *; }
-keep class com.nas.alreem.activity.login.model.ForgetPasswordResponseModel { *; }
-keep class com.nas.alreem.activity.login.model.LoginResponseArrayModel { *; }
-keep class com.nas.alreem.activity.login.model.LoginResponseModel { *; }
-keep class com.nas.alreem.activity.login.model.SignUpResponseModel { *; }
-keep class com.nas.alreem.activity.lost_card.model.GetShopCartResponseModel { *; }
-keep class com.nas.alreem.activity.lost_card.model.ListModelHistory { *; }
-keep class com.nas.alreem.activity.lost_card.model.LostCardHistoryResponseModel { *; }
-keep class com.nas.alreem.activity.lost_card.model.LostCardIntructionResponseModel { *; }
-keep class com.nas.alreem.activity.lost_card.model.ResponseHistory { *; }
-keep class com.nas.alreem.activity.lost_card.model.ResponseInstrutions { *; }
-keep class com.nas.alreem.activity.lost_card.model.ResponseStudent { *; }
-keep class com.nas.alreem.activity.lost_card.model.ShopCartResModel { *; }
-keep class com.nas.alreem.activity.lost_card.model.ShopCartResponseModel { *; }
-keep class com.nas.alreem.activity.lost_card.model.ShopHistoryModel { *; }
-keep class com.nas.alreem.activity.lost_card.model.StudentLostCardResponseModel { *; }
-keep class com.nas.alreem.activity.notifications.model.MessageDetailApiModel { *; }
-keep class com.nas.alreem.activity.notifications.model.MessageDetailModel { *; }
-keep class com.nas.alreem.activity.notifications.model.MessageDetailNotificationModel { *; }
-keep class com.nas.alreem.activity.notifications.model.MessageDetailResponseModel { *; }
-keep class com.nas.alreem.activity.parent_engagement.model.ChatterBoxResponseModel { *; }
-keep class com.nas.alreem.activity.parent_engagement.model.ClassRepresentativeListResponseModel { *; }
-keep class com.nas.alreem.activity.parent_engagement.model.ParentAssociationEventItemsModel { *; }
-keep class com.nas.alreem.activity.parent_engagement.model.ParentAssociationEventResponseModel { *; }
-keep class com.nas.alreem.activity.parent_engagement.model.ParentAssociationResponseModel { *; }
-keep class com.nas.alreem.activity.parent_engagement.model.ParentAssociationEventsModel { *; }
-keep class com.nas.alreem.activity.parent_engagement.model.TermsCalendarModel { *; }
-keep class com.nas.alreem.activity.parent_engagement.model.VolunteerSubmitResponseModel { *; }
-keep class com.nas.alreem.activity.parent_meetings.model.PtaConfirmApiModel { *; }
-keep class com.nas.alreem.activity.parent_meetings.model.PtaConfirmModel { *; }
-keep class com.nas.alreem.activity.parent_meetings.model.PtaDatesApiModel { *; }
-keep class com.nas.alreem.activity.parent_meetings.model.PtaDatesModel { *; }
-keep class com.nas.alreem.activity.parent_meetings.model.PtaInsertApiModel { *; }
-keep class com.nas.alreem.activity.parent_meetings.model.PtaInsertModel { *; }
-keep class com.nas.alreem.activity.parent_meetings.model.PtaListApiModel { *; }
-keep class com.nas.alreem.activity.parent_meetings.model.PtaListModel { *; }
-keep class com.nas.alreem.activity.parent_meetings.model.PtaReviewListResponseModel { *; }
-keep class com.nas.alreem.activity.parent_meetings.model.PtaTimeSlotList { *; }
-keep class com.nas.alreem.activity.parent_meetings.model.ReviewListModel { *; }
-keep class com.nas.alreem.activity.payments.model.payment_gateway.PayGateResModel { *; }
-keep class com.nas.alreem.activity.payments.model.payment_gateway.PaymentGatewayModel { *; }
-keep class com.nas.alreem.activity.payments.model.payment_gateway.PaymentGatewayApiModel { *; }
-keep class com.nas.alreem.activity.payments.model.payment_submit.PaymentSubmitApiModel { *; }
-keep class com.nas.alreem.activity.payments.model.payment_submit.PaymentSubmitModel { *; }
-keep class com.nas.alreem.activity.payments.model.payment_submit.PaySuccDetailResModel { *; }
-keep class com.nas.alreem.activity.payments.model.payment_token.PaymentTokenApiModel { *; }
-keep class com.nas.alreem.activity.payments.model.payment_token.PaymentTokenModel { *; }
-keep class com.nas.alreem.activity.payments.model.payment_token.PayTokenResModel { *; }
-keep class com.nas.alreem.activity.permission_slip.model.PermissionResApiModel { *; }
-keep class com.nas.alreem.activity.permission_slip.model.PermissionResponseModel { *; }
-keep class com.nas.alreem.activity.primary.model.ComingUpDataModell { *; }
-keep class com.nas.alreem.activity.primary.model.ComingUpResponseArrayModel { *; }
-keep class com.nas.alreem.activity.primary.model.ComingUpResponseModel { *; }
-keep class com.nas.alreem.activity.settings.model.TermsOfServiceModel { *; }
-keep class com.nas.alreem.activity.settings.model.TermsOfServiceResponseArrayModel { *; }
-keep class com.nas.alreem.activity.settings.model.TermsOfServiceResponseModel { *; }
-keep class com.nas.alreem.activity.shop_new.model.AddToCartShopApiModel { *; }
-keep class com.nas.alreem.activity.shop_new.model.ItemsListModel_new { *; }
-keep class com.nas.alreem.activity.shop_new.model.ItemsModelShop { *; }
-keep class com.nas.alreem.activity.shop_new.model.ItemsResponseModelNew { *; }
-keep class com.nas.alreem.activity.shop_new.model.OrdersModelShop { *; }
-keep class com.nas.alreem.activity.shop_new.model.PaymentShopWalletHistoryModel { *; }
-keep class com.nas.alreem.activity.shop_new.model.ResponseShopHistory { *; }
-keep class com.nas.alreem.activity.shop_new.model.ResponseShopItemHistory { *; }
-keep class com.nas.alreem.activity.shop_new.model.ResponseShopStudent { *; }
-keep class com.nas.alreem.activity.shop_new.model.ShopCartRemoveApiModel { *; }
-keep class com.nas.alreem.activity.shop_new.model.ShopCartUpdateApiModel { *; }
-keep class com.nas.alreem.activity.shop_new.model.ShopHistoryItemResponseModel { *; }
-keep class com.nas.alreem.activity.shop_new.model.ShopHistoryResponseModel { *; }
-keep class com.nas.alreem.activity.shop_new.model.ShopItemHistoryModel { *; }
-keep class com.nas.alreem.activity.shop_new.model.ShopItemsApiModel { *; }
-keep class com.nas.alreem.activity.shop_new.model.ShopModel { *; }
-keep class com.nas.alreem.activity.shop_new.model.StudentShopCardResponseModel { *; }
-keep class com.nas.alreem.activity.staff_directory.model.DepartmentStaffsModel { *; }
-keep class com.nas.alreem.activity.staff_directory.model.ListStaffDetailApiModel { *; }
-keep class com.nas.alreem.activity.staff_directory.model.ListStaffDetailModel { *; }
-keep class com.nas.alreem.activity.staff_directory.model.StaffCatListResponseModel { *; }
-keep class com.nas.alreem.activity.staff_directory.model.StaffCatResponseArrayModel { *; }
-keep class com.nas.alreem.activity.staff_directory.model.StaffDeptListModel { *; }
-keep class com.nas.alreem.activity.staff_directory.model.StaffDetailResponseArrayModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveyApiModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveyDetailApiModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveyDetailDataModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveyDetailResponseArrayModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveyDetailResponseModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveyListDataModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveyListResponseArrayModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveyListResponseModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveyOfferedAnswersModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveyQuestionsModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveyResponseArrayModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveyResponseModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveySubmitApiModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveySubmitDataModel { *; }
-keep class com.nas.alreem.activity.survey.model.SurveySubmitResponseModel { *; }
-keep class com.nas.alreem.activity.canteen.model.add_orders.CanteenItemsApiModel { *; }
-keep class com.nas.alreem.activity.canteen.model.add_orders.CategoryListModel { *; }
-keep class com.nas.alreem.activity.canteen.model.add_orders.CatItemsListModel { *; }
-keep class com.nas.alreem.activity.canteen.model.add_orders.CatListModel { *; }
-keep class com.nas.alreem.activity.canteen.model.add_orders.CatResponseModel { *; }
-keep class com.nas.alreem.activity.canteen.model.add_orders.ItemImageModel { *; }
-keep class com.nas.alreem.activity.canteen.model.add_orders.ItemsListModel { *; }
-keep class com.nas.alreem.activity.canteen.model.add_orders.ItemsResponseModel { *; }
-keep class com.nas.alreem.activity.canteen.model.add_to_cart.AddToCartCanteenApiModel { *; }
-keep class com.nas.alreem.activity.canteen.model.add_to_cart.AddToCartCanteenModel { *; }
-keep class com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartRemoveApiModel { *; }
-keep class com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartRemoveModel { *; }
-keep class com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartUpdateApiModel { *; }
-keep class com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartUpdateModel { *; }
-keep class com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartApiModel { *; }
-keep class com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartListmodel { *; }
-keep class com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartModel { *; }
-keep class com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartResModel { *; }
-keep class com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartResponseModel { *; }
-keep class com.nas.alreem.activity.canteen.model.canteen_cart.CartItemsListModel { *; }
-keep class com.nas.alreem.activity.canteen.model.canteen_cart.ItemsModel { *; }
-keep class com.nas.alreem.activity.canteen.model.canteen_cart.OrdersModel { *; }
-keep class com.nas.alreem.activity.canteen.model.information.InfoCanteenModel { *; }
-keep class com.nas.alreem.activity.canteen.model.information.InfoListModel { *; }
-keep class com.nas.alreem.activity.canteen.model.information.InfoResponseModel { *; }
-keep class com.nas.alreem.activity.canteen.model.myorders.CancelCanteenPreOrderApiModel { *; }
-keep class com.nas.alreem.activity.canteen.model.myorders.CancelCanteenPreorderItemId { *; }
-keep class com.nas.alreem.activity.canteen.model.myorders.PreorderitemImage_list { *; }
-keep class com.nas.alreem.activity.canteen.model.myorders.Preorderitems_list { *; }
-keep class com.nas.alreem.activity.canteen.model.myorders.PreOrdersListModel { *; }
-keep class com.nas.alreem.activity.canteen.model.myorders.PreOrdersModel { *; }
-keep class com.nas.alreem.activity.canteen.model.myorders.PreOrdersResponseModel { *; }
-keep class com.nas.alreem.activity.canteen.model.myorders.UpdateCanteenPreorderItemApiModel { *; }
-keep class com.nas.alreem.activity.canteen.model.order_history.OrderCanteenPreOrderItems { *; }
-keep class com.nas.alreem.activity.canteen.model.order_history.OrderHistoryApiModel { *; }
-keep class com.nas.alreem.activity.canteen.model.order_history.OrderHistoryDataModel { *; }
-keep class com.nas.alreem.activity.canteen.model.order_history.OrderHistoryResponseModel { *; }
-keep class com.nas.alreem.activity.canteen.model.order_history.OrderHstoryResponseArrayModel { *; }
-keep class com.nas.alreem.activity.canteen.model.payment_history.FullPaymentListModel { *; }
-keep class com.nas.alreem.activity.canteen.model.payment_history.InstallmentListModel { *; }
-keep class com.nas.alreem.activity.canteen.model.payment_history.PayListModel { *; }
-keep class com.nas.alreem.activity.canteen.model.payment_history.PaymentHisModel { *; }
-keep class com.nas.alreem.activity.canteen.model.payment_history.PaymentHisResponseModel { *; }
-keep class com.nas.alreem.activity.canteen.model.payment_history.PaymentHistoryListModel { *; }
-keep class com.nas.alreem.activity.canteen.model.preorder.CanteenPreorderApiModel { *; }
-keep class com.nas.alreem.activity.canteen.model.preorder.CanteenPreorderModel { *; }
-keep class com.nas.alreem.activity.canteen.model.topup.WalletAmountApiModel { *; }
-keep class com.nas.alreem.activity.canteen.model.topup.WalletAmountModel { *; }
-keep class com.nas.alreem.activity.canteen.model.topup.WalletTopResModel { *; }
-keep class com.nas.alreem.activity.canteen.model.wallet.WalletBalanceApiModel { *; }
-keep class com.nas.alreem.activity.canteen.model.wallet.WalletBalanceModel { *; }
-keep class com.nas.alreem.activity.canteen.model.wallet.WalletResModel { *; }
-keep class com.nas.alreem.activity.canteen.model.wallethistory.CreditHisListModel { *; }
-keep class com.nas.alreem.activity.canteen.model.wallethistory.WalletHisResModel { *; }
-keep class com.nas.alreem.activity.canteen.model.wallethistory.WalletHistoryApiModel { *; }
-keep class com.nas.alreem.activity.canteen.model.wallethistory.WalletHistoryModel { *; }
-keep class com.nas.alreem.fragment.about_us.model.AboutUsDataModel { *; }
-keep class com.nas.alreem.fragment.about_us.model.AboutusList { *; }
-keep class com.nas.alreem.fragment.about_us.model.AboutUsResponseArrayModel { *; }
-keep class com.nas.alreem.fragment.about_us.model.AboutUsResponseModel { *; }
-keep class com.nas.alreem.fragment.absence.model.AbsenceRequestListDetailModel { *; }
-keep class com.nas.alreem.fragment.absence.model.EarlyPickupListModel { *; }
-keep class com.nas.alreem.fragment.calendar.model.CalendarAPIModel { *; }
-keep class com.nas.alreem.fragment.calendar.model.CalendarArrayModel { *; }
-keep class com.nas.alreem.fragment.calendar.model.CalendarArrayModelUSe { *; }
-keep class com.nas.alreem.fragment.calendar.model.CalendarDataModel { *; }
-keep class com.nas.alreem.fragment.calendar.model.CalendarDetailsModel { *; }
-keep class com.nas.alreem.fragment.calendar.model.CalendarDetailsModelUse { *; }
-keep class com.nas.alreem.fragment.calendar.model.CalendarResponseModel { *; }
-keep class com.nas.alreem.fragment.calendar.model.TermCalendarDataModel { *; }
-keep class com.nas.alreem.fragment.calendar.model.TermCalendarListModel { *; }
-keep class com.nas.alreem.fragment.calendar.model.TermCalendarResponseModel { *; }
-keep class com.nas.alreem.fragment.canteen.model.CanteenBannerDataModel { *; }
-keep class com.nas.alreem.fragment.canteen.model.CanteenBannerResponseArrayModel { *; }
-keep class com.nas.alreem.fragment.canteen.model.CanteenBannerResponseModel { *; }
-keep class com.nas.alreem.fragment.canteen.model.CanteenSendEmailApiModel { *; }
-keep class com.nas.alreem.fragment.cca.model.BannerResponseArrayDataModel { *; }
-keep class com.nas.alreem.fragment.cca.model.BannerResponseModelCCa { *; }
-keep class com.nas.alreem.fragment.communication.model.CommunicationBannerResponseModel { *; }
-keep class com.nas.alreem.fragment.communication.model.CommunicationDataModel { *; }
-keep class com.nas.alreem.fragment.communication.model.CommunicationResponseArrayModel { *; }
-keep class com.nas.alreem.fragment.communication.model.CommunicationResponseModel { *; }
-keep class com.nas.alreem.fragment.communication.model.SocialMediaResponseModel { *; }
-keep class com.nas.alreem.fragment.contact_us.model.ContactUsModel { *; }
-keep class com.nas.alreem.fragment.contact_us.model.ContactUsResponseArrayModel { *; }
-keep class com.nas.alreem.fragment.contact_us.model.ContactUsResponseModel { *; }
-keep class com.nas.alreem.fragment.gallery.model.ThumnailImageModel { *; }
-keep class com.nas.alreem.fragment.gallery.model.ThumnailResponseArrayModel { *; }
-keep class com.nas.alreem.fragment.gallery.model.ThumnailResponseModel { *; }
-keep class com.nas.alreem.fragment.home.model.BadgeResponseModel { *; }
-keep class com.nas.alreem.fragment.home.model.BannerResponseArrayModel { *; }
-keep class com.nas.alreem.fragment.home.model.BannerResponseModel { *; }
-keep class com.nas.alreem.fragment.home.model.BannerResponseNoticeModel { *; }
-keep class com.nas.alreem.fragment.intention.model.IntentionApiModel { *; }
-keep class com.nas.alreem.fragment.intention.model.IntentionApiSubmit { *; }
-keep class com.nas.alreem.fragment.intention.model.IntentionInfoArray { *; }
-keep class com.nas.alreem.fragment.intention.model.IntentionInfoResponseArray { *; }
-keep class com.nas.alreem.fragment.intention.model.IntentionListAPIResponseModel { *; }
-keep class com.nas.alreem.fragment.intention.model.IntentionResponseModel { *; }
-keep class com.nas.alreem.fragment.intention.model.IntentionStatusApiModel { *; }
-keep class com.nas.alreem.fragment.intention.model.IntentionStatusArray { *; }
-keep class com.nas.alreem.fragment.intention.model.IntentionstatusResponseArray { *; }
-keep class com.nas.alreem.fragment.intention.model.IntentionStatusResponseModel { *; }
-keep class com.nas.alreem.fragment.intention.model.IntentionSubmitModel { *; }
-keep class com.nas.alreem.fragment.notifications.model.NotificationApiModel { *; }
-keep class com.nas.alreem.fragment.notifications.model.NotificationModel { *; }
-keep class com.nas.alreem.fragment.notifications.model.NotificationResponseArrayModel { *; }
-keep class com.nas.alreem.fragment.notifications.model.NotificationResponseModel { *; }
-keep class com.nas.alreem.fragment.parent_meetings.model.ListStaffPtaApiModel { *; }
-keep class com.nas.alreem.fragment.parent_meetings.model.ListStaffPtaModel { *; }
-keep class com.nas.alreem.fragment.parent_meetings.model.StaffInfoDetail { *; }
-keep class com.nas.alreem.fragment.parents_essentials.model.ParentEssentialDataResponseModel { *; }
-keep class com.nas.alreem.fragment.parents_essentials.model.ParentsEssentialDataModel { *; }
-keep class com.nas.alreem.fragment.parents_essentials.model.ParentsEssentialResponseArrayModel { *; }
-keep class com.nas.alreem.fragment.parents_essentials.model.ParentsEssentialResponseModel { *; }
-keep class com.nas.alreem.fragment.parents_essentials.model.ParentsEssentialSubMenuModel { *; }
-keep class com.nas.alreem.fragment.payments.model.PaymentDataModel { *; }
-keep class com.nas.alreem.fragment.payments.model.PaymentResponseArrayModel { *; }
-keep class com.nas.alreem.fragment.payments.model.PaymentResponseModel { *; }
-keep class com.nas.alreem.fragment.payments.model.SendEmailApiModel { *; }
-keep class com.nas.alreem.fragment.performing_arts.model.PrimaryModel { *; }
-keep class com.nas.alreem.fragment.performing_arts.model.PerformingArtsmodel { *; }
-keep class com.nas.alreem.fragment.performing_arts.model.PrimaryModel { *; }
-keep class com.nas.alreem.fragment.performing_arts.model.SecondaryModel { *; }
-keep class com.nas.alreem.fragment.performing_arts.model.PrimaryModel { *; }
-keep class com.nas.alreem.fragment.permission_slip.model.GeneralFormModel { *; }
-keep class com.nas.alreem.fragment.permission_slip.model.GeneralSlipResponseArray { *; }
-keep class com.nas.alreem.fragment.permission_slip.model.PermissionGeneralListApiModel { *; }
-keep class com.nas.alreem.fragment.permission_slip.model.PermissionSlipListApiModel { *; }
-keep class com.nas.alreem.fragment.permission_slip.model.PermissionSlipListModel { *; }
-keep class com.nas.alreem.fragment.permission_slip.model.PermissionSlipModel { *; }
-keep class com.nas.alreem.fragment.permission_slip.model.PermissionSlipResponseArray { *; }
-keep class com.nas.alreem.fragment.primary.model.PrimaryDataModel { *; }
-keep class com.nas.alreem.fragment.primary.model.PrimaryFileModel { *; }
-keep class com.nas.alreem.fragment.primary.model.PrimaryResponseArrayModel { *; }
-keep class com.nas.alreem.fragment.primary.model.PrimaryResponseModel { *; }
-keep class com.nas.alreem.fragment.reports.model.ReportApiModel { *; }
-keep class com.nas.alreem.fragment.reports.model.ReportDetailModel { *; }
-keep class com.nas.alreem.fragment.reports.model.ReportListDetailModel { *; }
-keep class com.nas.alreem.fragment.reports.model.ReportListModel { *; }
-keep class com.nas.alreem.fragment.reports.model.ReportResponseArray { *; }
-keep class com.nas.alreem.fragment.settings.model.ChangePasswordApiModel { *; }
-keep class com.nas.alreem.fragment.student_information.model.StudentInfoApiModel { *; }
-keep class com.nas.alreem.fragment.student_information.model.StudentInfoDetail { *; }
-keep class com.nas.alreem.fragment.student_information.model.StudentInfoModel { *; }
-keep class com.nas.alreem.fragment.student_information.model.StudentInfoResponseArray { *; }
-keep class com.nas.alreem.fragment.time_table_new.model.DayModelNew { *; }
-keep class com.nas.alreem.fragment.time_table_new.model.FieldApiListModelNew { *; }
-keep class com.nas.alreem.fragment.time_table_new.model.FieldModelNew { *; }
-keep class com.nas.alreem.fragment.time_table_new.model.PeriodModelNew { *; }
-keep class com.nas.alreem.fragment.time_table_new.model.RangeApiModelNew { *; }
-keep class com.nas.alreem.fragment.time_table_new.model.RangeModelNew { *; }
-keep class com.nas.alreem.fragment.time_table_new.model.TimeTableApiDataModelNew { *; }
-keep class com.nas.alreem.fragment.time_table_new.model.TimeTableApiListModelNew { *; }
-keep class com.nas.alreem.fragment.time_table_new.model.TimeTableModelNew { *; }
-keep class com.nas.alreem.fragment.time_table_new.model.TimeTableResponseArrayNew { *; }








































































































































































