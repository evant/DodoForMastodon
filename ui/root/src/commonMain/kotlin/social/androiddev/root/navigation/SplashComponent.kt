/*
 * This file is part of Dodo.
 *
 * Dodo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Dodo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Dodo. If not, see <https://www.gnu.org/licenses/>.
 */
package social.androiddev.root.navigation

/**
 * The base component describing all business logic needed for the splash screen
 */
interface SplashComponent {

    /**
     * Callback invoked when the logged-in user should be taken to the timeline screen
     */
    fun navigateToTimeline()

    /**
     * Callback invoked when the logged-out user should be taken to the landing screen
     */
    fun navigateToLanding()
}
