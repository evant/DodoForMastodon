/*
 * This file is part of Dodo.
 *
 * Dodo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Dodo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Dodo. If not, see <https://www.gnu.org/licenses/>.
 */
package social.androiddev.common.di

import social.androiddev.common.network.di.networkModule
import social.androiddev.common.persistence.di.persistenceModule
import social.androiddev.common.repository.di.repositoryModule
import social.androiddev.common.repository.timeline.timelineRepoModule
import social.androiddev.domain.authentication.di.domainAuthModule

/**
 * Base Koin module shared across all apps (android, iOS, Desktop)
 */
fun appModule() = listOf(
    platformModule,
    networkModule,
    persistenceModule,
    domainAuthModule,
    repositoryModule,
    timelineRepoModule
)
