/*
 * This file is part of Dodo.
 *
 * Dodo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Dodo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Dodo. If not, see <https://www.gnu.org/licenses/>.
 */
package social.androiddev.common.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * https://docs.joinmastodon.org/entities/notification/
 */
@Serializable
data class Notification(
    // required attributes
    @SerialName("id") val id: String,
    @SerialName("type") val type: NotificationType,
    @SerialName("created_at") val createdAt: String,
    @SerialName("account") val account: Account,

    // optional attributes
    @SerialName("status") val status: Status,
)

enum class NotificationType {
    follow, follow_request, mention, reblog, favourite, poll, status
}
