package com.example.legodataapp

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials

fun Login(
    onLoginSuccess: (Credentials) -> Unit,
    context: Context
) {
    val account = Auth0(
        "UPFDhEvWpZk1qlInaCcpP2EjqK57E4Yr",
        "dev-8ntdq55wioisdbtk.us.auth0.com"
    )

    WebAuthProvider
        .login(account)
        .withScheme("app")
        .start(context, object : Callback<Credentials, AuthenticationException> {
            override fun onSuccess(result: Credentials) {
                onLoginSuccess(result)
            }
            override fun onFailure(error: AuthenticationException) {
            }
        })
}