package com.remember.network.retrofit

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
import com.remember.network.models.memory_line.ParticipantResponse
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
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.Calls
import retrofit2.mock.MockRetrofit
import java.lang.reflect.Type

class RememberApiMock(
    private val mockRetrofit: MockRetrofit,
    private val gson: Gson,
    private val context: Context
) : RememberApi {

    private val delegate: BehaviorDelegate<RememberApi> = mockRetrofit.create(
        RememberApi::class.java)

    override fun signupAsync(body: SignupRequest): Deferred<Unit> {
        val response = Calls.response(Unit)
        return delegate.returning(response).signupAsync(body)
    }

    override fun confirmSignupAsync(body: ConfirmSignupRequest): Deferred<String> {
        val response =
            Calls.response(gson.loadObjectFromJson<String>(context, "auth/confirm_signup.json"))
        return delegate.returning(response).confirmSignupAsync(body)
    }

    override fun signinAsync(body: SigninRequest): Deferred<AuthorizationDataResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<AuthorizationDataResponse>(context, "auth/signin.json"))
        return delegate.returning(response).signinAsync(body)
    }

    override fun refreshTokenAsync(body: RefreshTokenRequest): Deferred<AuthorizationDataResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<AuthorizationDataResponse>(context, "auth/refresh.json"))
        return delegate.returning(response).refreshTokenAsync(body)
    }

    override fun profileAsync(): Deferred<ProfileResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<ProfileResponse>(context, "account/profile.json"))
        return delegate.returning(response).profileAsync()
    }

    override fun acceptTermsAsync(): Deferred<Unit> {
        val response = Calls.response(Unit)
        return delegate.returning(response).acceptTermsAsync()
    }

    override fun termsAsync(): Deferred<TermResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<TermResponse>(context, "terms/terms.json"))
        return delegate.returning(response).termsAsync()
    }

    override fun verifyUsername(verifyUsername: VerifyUsernameRequest): Deferred<Boolean> {
        val response = Calls.response(true)
        return delegate.returning(response).verifyUsername(verifyUsername)
    }

    override fun verifyEmail(verifyEmail: VerifyEmailRequest): Deferred<Boolean> {
        val response = Calls.response(true)
        return delegate.returning(response).verifyEmail(verifyEmail)
    }

    override fun updateProfile(id: String, updateProfile: UpdateProfileRequest): Deferred<ProfileResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<ProfileResponse>(context, "account/update_profile.json"))
        return delegate.returning(response).updateProfile(id, updateProfile)
    }

    override fun updateProfileWithImage(id: String, updateProfile: UpdateProfileImageRequest): Deferred<ProfileResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<ProfileResponse>(context, "account/update_profile.json"))
        return delegate.returning(response).updateProfileWithImage(id, updateProfile)
    }

    override fun memoryLinesTypesAsync(page: Int): Deferred<MemoryLineTypePaginationResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<MemoryLineTypePaginationResponse>(context, "types/memory_line_types.json"))
        return delegate.returning(response).memoryLinesTypesAsync(page)
    }

    override fun createMemoryLinesTypesAsync(createMemoryLineTypeRequest: CreateMemoryLineTypeRequest): Deferred<Unit> {
        val response = Calls.response(Unit)
        return delegate.returning(response).createMemoryLinesTypesAsync(createMemoryLineTypeRequest)
    }

    override fun updateMemoryLinesTypesAsync(types: List<UpdateMemoryLineTypeRequest>): Deferred<Unit> {
        val response = Calls.response(Unit)
        return delegate.returning(response).updateMemoryLinesTypesAsync(types)
    }


    override fun memoryLineByTag(
        type: String,
        page: Int
    ): Deferred<MemoryLinePaginationResponse> {
        val response = Calls.response(
            gson.loadObjectFromJson<MemoryLinePaginationResponse>(
                context,
                "memory-line/memory_line_public.json"
            )
        )
        return delegate.returning(response).memoryLineByTag(type, page)
    }

    override fun createMemoryLineAsync(body: CreateMemoryLineRequest): Deferred<MemoryLineResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<MemoryLineResponse>(context, "memory-line/create_memory_line.json"))
        return delegate.returning(response).createMemoryLineAsync(body)
    }

    override fun updateMemoryLineNameAsync(
        idMemoryLine: String,
        body: UpdateMemoryLineRequest
    ): Deferred<MemoryLineResponse> {
        val response = Calls.response(
            gson.loadObjectFromJson<MemoryLineResponse>(context, "memory-line/update_memory_line_name.json")
        )
        return delegate.returning(response).updateMemoryLineNameAsync(idMemoryLine, body)
    }

    override fun deleteMemoryLineAsync(idMemoryLine: String): Deferred<Boolean> {
        val response = Calls.response(true)
        return delegate.returning(response).deleteMemoryLineAsync(idMemoryLine)
    }

    override fun participantsMemoryLine(idMemoryLine: String): Deferred<ParticipantPaginationResponse> {
        val response =
            Calls.response(
                gson.loadObjectFromJson<ParticipantPaginationResponse>(
                    context, "memory-line/participants_memory_line.json"
                )
            )
        return delegate.returning(response).participantsMemoryLine(idMemoryLine)
    }

    override fun ownerMemoryLine(idMemoryLine: String): Deferred<MemoryLineOwnerResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<MemoryLineOwnerResponse>(context, "memory-line/memory_line_owner.json"))
        return delegate.returning(response).ownerMemoryLine(idMemoryLine)
    }

    override fun detailMemoryline(idMemoryLine: String): Deferred<MemoryLineDetailResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<MemoryLineDetailResponse>(context, "memory-line/detail_memory_line.json"))
        return delegate.returning(response).detailMemoryline(idMemoryLine)
    }

    override fun createMomentAsync(
        idMemoryLine: String,
        momentBody: MomentBodyRequest
    ): Deferred<CreateMomentResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<CreateMomentResponse>(context, "moment/create_moment.json"))
        return delegate.returning(response).createMomentAsync(idMemoryLine, momentBody)
    }

    override fun momentsPagination(
        idMemoryLine: String,
        page: Int
    ): Deferred<MomentsPaginationResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<MomentsPaginationResponse>(context, "moment/moments_pagination.json"))
        return delegate.returning(response).momentsPagination(idMemoryLine, page)
    }

    override fun deleteMomentAsync(idMoment: String): Deferred<Boolean> {
        val response = Calls.response(true)
        return delegate.returning(response).deleteMomentAsync(idMoment)
    }

    override fun momentDetailAsync(idMoment: String): Deferred<MomentResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<MomentResponse>(context, "moment/moment_detail.json"))
        return delegate.returning(response).momentDetailAsync(idMoment)
    }

    override fun commentsMomentAsync(idMoment: String, page: Int): Deferred<CommentsPaginationResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<CommentsPaginationResponse>(context, "comments/comments_moment.json"))
        return delegate.returning(response).commentsMomentAsync(idMoment, page)
    }

    override fun insertCommentMomentAsync(
        idMoment: String,
        comment: CreateCommentRequest
    ): Deferred<CommentResponse> {
        val response =
            Calls.response(gson.loadObjectFromJson<CommentResponse>(context, "comments/insert_comment_moment.json"))
        return delegate.returning(response).insertCommentMomentAsync(idMoment, comment)
    }

    override fun respondInviteAsync(
        idInvite: String,
        answer: AnswerInviteRequest
    ): Deferred<AnswerInviteResponse> {
        val response = Calls.response(
            gson.loadObjectFromJson<AnswerInviteResponse>(
                context,
                "invites/respond_invite.json"
            )
        )
        return delegate.returning(response).respondInviteAsync(idInvite, answer)
    }

    override fun invitesAsync(): Deferred<InvitesPaginationResponse> {
        val response = Calls.response(
            gson.loadObjectFromJson<InvitesPaginationResponse>(
                context,
                "invites/invites.json"
            )
        )
        return delegate.returning(response).invitesAsync()
    }

    override fun createInviteAsync(
        invite: CreateInviteRequest
    ): Deferred<CreateInviteAnswerResponse> {
        val response = Calls.response(
            gson.loadObjectFromJson<CreateInviteAnswerResponse>(
                context,
                "invites/create_invite.json"
            )
        )
        return delegate.returning(response).createInviteAsync(invite)
    }

    override fun createInviteManyAsync(invites: List<CreateInviteRequest>): Deferred<Any> {
        val response = Calls.response(Unit)
        return delegate.returning(response).createInviteManyAsync(invites)
    }

    override fun notificationsAsync(): Deferred<DefaultNotificationsResponse> {
        val response = Calls.response(
            gson.loadObjectFromJson<DefaultNotificationsResponse>(
                context,
                "notification/notifications_default.json"
            )
        )
        return delegate.returning(response).notificationsAsync()
    }

    override fun searchProfile(username: String): Deferred<SearchPaginationResponse> {
        val response = Calls.response(
            gson.loadObjectFromJson<SearchPaginationResponse>(context, "search/search_profile.json")
        )
        return delegate.returning(response).searchProfile(username)
    }

    override fun memoryLineHistoryAsync(): Deferred<List<MomentResponse>> {
        val response = Calls.response(
            gson.loadListFromJson<List<MomentResponse>>(
                context,
                "history/memory_line_history.json",
                genericType<List<MomentResponse>>()
            )
        )
        return delegate.returning(response).memoryLineHistoryAsync()
    }

    override fun memoryLineParticipantsHistoryAsync(): Deferred<List<ParticipantMemoryLineResponse>> {
        val response = Calls.response(
            gson.loadListFromJson<List<ParticipantResponse>>(
                context,
                "history/participants_history.json",
                genericType<List<ParticipantMemoryLineResponse>>()
            )
        )
        return delegate.returning(response).memoryLineParticipantsHistoryAsync()
    }

    override fun addDeviceAsync(request: DeviceRequest): Deferred<Any> {
        val response = Calls.response(1)
        return delegate.returning(response).memoryLineParticipantsHistoryAsync()
    }

    private inline fun <reified T> Gson.loadObjectFromJson(context: Context, file: String) : T {
        val json = context.resources.assets.open(file)
            .bufferedReader()
            .readText()
        return fromJson(json, T::class.java)
    }

    inline fun <reified T> genericType() = object: TypeToken<T>() {}.type

    private inline fun <reified T> Gson.loadListFromJson(context: Context, file: String, type: Type) : List<T> {
        val json = context.resources.assets.open(file)
            .bufferedReader()
            .readText()
        return fromJson(json, type)
    }
}