package com.remember.network.retrofit

import com.remember.network.models.auth.AuthorizationDataResponse
import com.remember.network.models.auth.ConfirmSignupRequest
import com.remember.network.models.auth.RefreshTokenRequest
import com.remember.network.models.auth.SigninRequest
import com.remember.network.models.auth.SignupRequest
import com.remember.network.models.auth.VerifyEmailRequest
import com.remember.network.models.auth.VerifyUsernameRequest
import com.remember.network.models.comment.CommentResponse
import com.remember.network.models.comment.CommentsPaginationResponse
import com.remember.network.models.comment.CreateCommentRequest
import com.remember.network.models.device.DeviceRequest
import com.remember.network.models.invite.AnswerInviteRequest
import com.remember.network.models.invite.AnswerInviteResponse
import com.remember.network.models.invite.CreateInviteAnswerResponse
import com.remember.network.models.invite.CreateInviteRequest
import com.remember.network.models.invite.InvitesPaginationResponse
import com.remember.network.models.memory_line.CreateMemoryLineRequest
import com.remember.network.models.memory_line.MemoryLineDetailResponse
import com.remember.network.models.memory_line.MemoryLineOwnerResponse
import com.remember.network.models.memory_line.MemoryLinePaginationResponse
import com.remember.network.models.memory_line.MemoryLineResponse
import com.remember.network.models.memory_line.UpdateMemoryLineRequest
import com.remember.network.models.moment.CreateMomentResponse
import com.remember.network.models.moment.MomentBodyRequest
import com.remember.network.models.moment.MomentResponse
import com.remember.network.models.moment.MomentsPaginationResponse
import com.remember.network.models.notification.DefaultNotificationsResponse
import com.remember.network.models.participant.ParticipantMemoryLineResponse
import com.remember.network.models.participant.ParticipantPaginationResponse
import com.remember.network.models.profile.ProfileResponse
import com.remember.network.models.profile.UpdateProfileRequest
import com.remember.network.models.profile.UpdateProfileImageRequest
import com.remember.network.models.search.SearchPaginationResponse
import com.remember.network.models.terms.TermResponse
import com.remember.network.models.type.CreateMemoryLineTypeRequest
import com.remember.network.models.type.MemoryLineTypePaginationResponse
import com.remember.network.models.type.UpdateMemoryLineTypeRequest
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RememberApi {
    // auth
    @POST("accounts/")
    fun signupAsync(
        @Body body: SignupRequest
    ) : Deferred<Unit>

    @POST("account/confirmsignup")
    fun confirmSignupAsync(
        @Body body: ConfirmSignupRequest
    ) : Deferred<String>

    @POST("accounts/login/")
    fun signinAsync(
        @Body body: SigninRequest
    ) : Deferred<AuthorizationDataResponse>

    @POST("accounts/login/refresh/")
    fun refreshTokenAsync(
        @Body body: RefreshTokenRequest
    ) : Deferred<AuthorizationDataResponse>

    // account

    @GET("terms")
    fun termsAsync() : Deferred<TermResponse>

    @PATCH("accounts/terms/accept/")
    fun acceptTermsAsync() : Deferred<Unit>

    @GET("accounts/me")
    fun profileAsync() : Deferred<ProfileResponse>

    @POST("accounts/verify_username/")
    fun verifyUsername(
        @Body verifyUsername: VerifyUsernameRequest
    ) : Deferred<Boolean>

    @POST("accounts/verify_email/")
    fun verifyEmail(
        @Body verifyEmail: VerifyEmailRequest
    ) : Deferred<Boolean>

    @PATCH("accounts/{id}/")
    fun updateProfile(
        @Path("id") id: String,
        @Body updateProfile: UpdateProfileRequest
    ) : Deferred<ProfileResponse>

    @PATCH("accounts/{id}/")
    fun updateProfileWithImage(
        @Path("id") id: String,
        @Body updateProfile: UpdateProfileImageRequest
    ) : Deferred<ProfileResponse>

    // types

    @GET("type/")
    fun memoryLinesTypesAsync(
        @Query("page") page: Int
    ) : Deferred<MemoryLineTypePaginationResponse>

    @POST("type/")
    fun createMemoryLinesTypesAsync(
        @Body createMemoryLineTypeRequest: CreateMemoryLineTypeRequest
    ) : Deferred<Unit>

    @PUT("type/many/")
    fun updateMemoryLinesTypesAsync(
        @Body types: List<UpdateMemoryLineTypeRequest>
    ) : Deferred<Unit>

    // memory line
    @GET("memory-lines/")
    fun memoryLineByTag(
        @Query("type") type: String,
        @Query("page") page: Int
    ) : Deferred<MemoryLinePaginationResponse>

    @POST("memory-lines/")
    fun createMemoryLineAsync(
        @Body body: CreateMemoryLineRequest
    ) : Deferred<MemoryLineResponse>

    @PATCH("memory-lines/{id-memory-line}/")
    fun updateMemoryLineNameAsync(
        @Path("id-memory-line") idMemoryLine: String,
        @Body body: UpdateMemoryLineRequest
    ) : Deferred<MemoryLineResponse>

    @DELETE("memory-lines/{id-memory-line}/")
    fun deleteMemoryLineAsync(
        @Path("id-memory-line") idMemoryLine: String
    ) : Deferred<Boolean>

    @GET("memory-lines/{id-memory-line}/participants/")
    fun participantsMemoryLine(
        @Path("id-memory-line") idMemoryLine: String
    ) : Deferred<ParticipantPaginationResponse>

    @GET("memory-lines/{id-memory-line}/owner/")
    fun ownerMemoryLine(
        @Path("id-memory-line") idMemoryLine: String
    ) : Deferred<MemoryLineOwnerResponse>

    @GET("memory-lines/{id-memory-line}/")
    fun detailMemoryline(
        @Path("id-memory-line") idMemoryLine: String
    ) : Deferred<MemoryLineDetailResponse>

    // moment
    @POST("memory-lines/{id-memory-line}/moments/create/")
    fun createMomentAsync(
        @Path("id-memory-line") idMemoryLine: String,
        @Body momentBody: MomentBodyRequest
    ) : Deferred<CreateMomentResponse>

    @GET("memory-lines/{id-memory-line}/moments/")
    fun momentsPagination(
        @Path("id-memory-line") idMemoryLine: String,
        @Query("page") page: Int
    ) : Deferred<MomentsPaginationResponse>

    @DELETE("moments/{id-moment}/")
    fun deleteMomentAsync(
        @Path("id-moment") idMoment: String
    ) : Deferred<Boolean>

    @GET("moments/{id-moment}/")
    fun momentDetailAsync(
        @Path("id-moment") idMoment: String
    ) : Deferred<MomentResponse>

    // comments
    @GET( "moments/{id-moment}/comments/")
    fun commentsMomentAsync(
        @Path("id-moment") idMoment: String,
        @Query("page") page: Int
    ) : Deferred<CommentsPaginationResponse>

    @POST("moments/{id-moment}/comments/create/")
    fun insertCommentMomentAsync(
        @Path("id-moment") idMoment: String,
        @Body comment: CreateCommentRequest
    ) : Deferred<CommentResponse>

    // invites

    @POST("invites/{id-invite}/accept/")
    fun respondInviteAsync(
        @Path("id-invite") idInvite: String,
        @Body answer: AnswerInviteRequest
    ) : Deferred<AnswerInviteResponse>

    @GET("invites/")
    fun invitesAsync() : Deferred<InvitesPaginationResponse>

    @POST("invites/")
    fun createInviteAsync(
        @Body invite: CreateInviteRequest
    ) : Deferred<CreateInviteAnswerResponse>

    @POST("invites/many/")
    fun createInviteManyAsync(
        @Body invites: List<CreateInviteRequest>
    ) : Deferred<Any>

    // notification

    @GET("notification/")
    fun notificationsAsync() : Deferred<DefaultNotificationsResponse>

   // search

    @GET("accounts/")
    fun searchProfile(
        @Query("search") username: String
    ) : Deferred<SearchPaginationResponse>

    // history

    @GET("history/")
    fun memoryLineHistoryAsync(): Deferred<List<MomentResponse>>

    @GET("history/participants/")
    fun memoryLineParticipantsHistoryAsync(): Deferred<List<ParticipantMemoryLineResponse>>

    // push
    @POST("device/add/")
    fun addDeviceAsync(
        @Body request: DeviceRequest
    ): Deferred<Any>

}
