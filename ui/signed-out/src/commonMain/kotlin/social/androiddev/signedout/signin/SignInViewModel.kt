/*
 * This file is part of Dodo.
 *
 * Dodo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Dodo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Dodo. If not, see <https://www.gnu.org/licenses/>.
 */
package social.androiddev.signedout.signin

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import social.androiddev.domain.authentication.model.ApplicationOAuthToken
import social.androiddev.domain.authentication.usecase.CreateAccessToken
import social.androiddev.domain.authentication.usecase.GetSelectedApplicationOAuthToken
import social.androiddev.signedout.util.encode
import java.net.URI
import kotlin.coroutines.CoroutineContext

// TODO Add tests
internal class SignInViewModel(
    mainContext: CoroutineContext,
    private val getSelectedApplicationOAuthToken: GetSelectedApplicationOAuthToken,
    private val createAccessToken: CreateAccessToken,
    private val onSignedIn: () -> Unit,
) : InstanceKeeper.Instance {

    // The scope survives Android configuration changes
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _state = MutableStateFlow(createInitialState())
    val state: StateFlow<SignInComponent.State> = _state.asStateFlow()

    private fun createInitialState() =
        SignInComponent.State(oauthAuthorizeUrl = "", redirectUri = "", server = "")

    init {
        scope.launch {
            val token = getSelectedApplicationOAuthToken()
            _state.update {
                it.copy(
                    server = token.server,
                    redirectUri = token.redirectUri,
                    oauthAuthorizeUrl = createOAuthAuthorizeUrl(token),
                )
            }
        }
    }

    private fun createOAuthAuthorizeUrl(token: ApplicationOAuthToken): String {
        val b = StringBuilder().apply {
            append("https://${token.server}")
            append("/oauth/authorize?client_id=${token.clientId}")
            append("&scope=${"read write follow push".encode()}")
            append("&redirect_uri=${token.redirectUri.encode()}")
            append("&response_type=code")
        }
        return b.toString()
    }

    fun onErrorFromOAuth(error: String) {
        displayErrorWithDuration(error)
    }

    private fun displayErrorWithDuration(error: String) {
        _state.update { it.copy(error = error) }
        scope.launch {
            delay(3_000)
            _state.update { it.copy(error = null) }
        }
    }

    fun shouldCancelLoadingUrl(url: String): Boolean {
        val uri = URI(url)
        val query = uri.query

        if (!url.contains(_state.value.redirectUri)) {
            return false
        }

        if (query.isNullOrEmpty()) {
            return false
        }

        return when {
            query.contains("error=") -> {
                val error = query.replace("error=", "")
                displayErrorWithDuration(error)
                true
            }

            query.contains("code=") -> {
                val code = query.replace("code=", "")
                scope.launch {
                    val success = createAccessToken(
                        authCode = code,
                        server = _state.value.server
                    )
                    if (success) {
                        onSignedIn()
                    } else {
                        displayErrorWithDuration("An error occurred.")
                    }
                }
                true
            }

            else -> false
        }
    }

    override fun onDestroy() {
        scope.cancel() // Cancel the scope when the instance is destroyed
    }
}
