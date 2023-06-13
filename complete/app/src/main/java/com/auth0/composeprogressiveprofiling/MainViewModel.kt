package com.auth0.composeprogressiveprofiling

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.management.ManagementException
import com.auth0.android.management.UsersAPIClient
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile


private const val TAG = "MainViewModel"

class MainViewModel: ViewModel() {

    var appJustLaunched by mutableStateOf(true)
    var userIsAuthenticated by mutableStateOf(false)
    var user by mutableStateOf(User())

    var showProgressiveProfilingButton by mutableStateOf(false)
    var progressiveProfilingQuestion by mutableStateOf("")
    var progressiveProfilingQuestionField = ""
    var progressiveProfilingAnswer by mutableStateOf("")

    var credentials: Credentials? = null
    var appMetadata = mapOf<String, Any>()
    var userMetadata = mapOf<String, Any>()

    private lateinit var account: Auth0
    private lateinit var context: Context


    fun setContext(activityContext: Context) {
        context = activityContext
        account = Auth0(
            context.getString(R.string.com_auth0_client_id),
            context.getString(R.string.com_auth0_domain)
        )
    }

    fun login() {
        WebAuthProvider
            .login(account)
            .withScheme(context.getString(R.string.com_auth0_scheme))
            .withAudience("https://${context.getString(R.string.com_auth0_domain)}/api/v2/")
            .withScope("openid profile email read:current_user update:current_user_metadata")
            .start(context, object : Callback<Credentials, AuthenticationException> {

                override fun onFailure(error: AuthenticationException) {
                    // The user either pressed the “Cancel” button
                    // on the Universal Login screen or something
                    // unusual happened.
                    Log.e(TAG, "Error occurred in login(): $error")
                }

                override fun onSuccess(result: Credentials) {
                    // The user successfully logged in.
                    user = User(result.idToken)
                    userIsAuthenticated = true
                    appJustLaunched = false
                    credentials = result
                    getMetadata()
                }

            })
    }

    fun logout() {
        WebAuthProvider
            .logout(account)
            .withScheme(context.getString(R.string.com_auth0_scheme))
            .start(context, object : Callback<Void?, AuthenticationException> {

                override fun onFailure(error: AuthenticationException) {
                    // For some reason, logout failed.
                    Log.e(TAG, "Error occurred in logout(): $error")
                }

                override fun onSuccess(result: Void?) {
                    // The user successfully logged out.
                    user = User()
                    userIsAuthenticated = false
                    credentials = null
                }

            })
    }

    fun getMetadata() {
        var accessToken = ""
        credentials?.let {
            accessToken = it.accessToken
        } ?: run {
            return
        }

        val usersClient = UsersAPIClient(account, accessToken)
        usersClient
            .getProfile(user.id)
            .start(object : Callback<UserProfile, ManagementException> {

                override fun onFailure(error: ManagementException) {
                    Log.e(TAG, "Error occurred in getMetadata(): $error")
                }

                override fun onSuccess(result: UserProfile) {
                    // Get user metadata
                    userMetadata = result.getUserMetadata()
                    appMetadata = result.getAppMetadata()
                    setProgressiveProfilingStatus()
                    setProgressiveProfilingQuestion()
                }

            })

    }

    fun getProgressiveProfilingMapObjectOrNull(): Map<String, Any>? {
        return appMetadata.getOrDefault(
            key = "progressive_profiling",
            defaultValue = null
        ) as Map<String, Any>?
    }

    fun setProgressiveProfilingStatus() {
        val progressiveProfilingMap = getProgressiveProfilingMapObjectOrNull()
        progressiveProfilingMap?.let {
            progressiveProfilingQuestionField = progressiveProfilingMap.getOrDefault(
                key = "answer_field",
                defaultValue = ""
            ) as String
            val answer = userMetadata.getOrDefault(
                key = progressiveProfilingQuestionField,
                defaultValue = ""
            )
            showProgressiveProfilingButton = (answer == "")
        } ?: run {
            showProgressiveProfilingButton = false
        }
    }

    fun setProgressiveProfilingQuestion() {
        val progressiveProfilingMap = getProgressiveProfilingMapObjectOrNull()
        progressiveProfilingMap?.let {
            progressiveProfilingQuestion = progressiveProfilingMap.getOrDefault(
                key = "question",
                defaultValue = ""
            ) as String
        } ?: run {
            progressiveProfilingQuestion = ""
        }
    }

    fun saveProgressiveProfilingAnswer() {
        var accessToken = ""
        credentials?.let {
            accessToken = it.accessToken
        } ?: run {
            return
        }

        val usersClient = UsersAPIClient(account, accessToken)
        usersClient
            .updateMetadata(
                user.id,
                mapOf(progressiveProfilingQuestionField to progressiveProfilingAnswer))
            .start(object : Callback<UserProfile, ManagementException> {

                override fun onFailure(error: ManagementException) {
                    Log.e(TAG, "Error occurred in saveProgressiveProfilingAnswer(): $error")
                }

                override fun onSuccess(result: UserProfile) {
                    getMetadata()
                }

            })
    }

}