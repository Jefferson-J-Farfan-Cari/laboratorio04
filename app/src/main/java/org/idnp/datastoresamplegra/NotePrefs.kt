package org.idnp.datastoresamplegra

import android.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.idnp.datastoresamplegra.models.Settings
import org.idnp.datastoresamplegra.models.Signature

class NotePrefs(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun saveSignature(signature: Signature) {
        dataStore.edit { preferences ->
            preferences[PERIOD_A] = signature.period_a
            preferences[SCHOOL] = signature.school
            preferences[CODE] = signature.code
            preferences[NAME_S] = signature.name
            preferences[PERIOD_s] = signature.period_s
            preferences[LAST] = signature.last

        }
    }

    val getSignature: Flow<Signature>
        get() = dataStore.data.map { preferences ->
            Signature(
                preferences[PERIOD_A] ?: "No period",
                preferences[SCHOOL] ?: "No school",
                preferences[CODE] ?: "No code",
                preferences[NAME_S] ?: "No name",
                preferences[PERIOD_s]?: "No period",
                preferences[LAST] ?: "No last"
            )
        }

    suspend fun saveSettings(settings: Settings) {
        dataStore.edit { preferences ->
            preferences[COLOR] = settings.color
            preferences[FONT] = settings.font
            preferences[SIZE_LABEL] = settings.size_label
            preferences[SIZE_BOX] = settings.size_box
        }
    }

    val getSettings: Flow<Settings>
        get() = dataStore.data.map { preferences ->
            Settings(
                preferences[COLOR] ?: Color.LTGRAY.toString(),
                preferences[FONT] ?: 24.4F,
                preferences[SIZE_LABEL] ?: 30.5F,
                preferences[SIZE_BOX] ?: 25.5F
            )
        }

    companion object {
        val PREFS_NAME = "PREFS_NAME"
        // SIGNATURE
        private val PERIOD_A = stringPreferencesKey("key_app_signature_period_a")
        private val SCHOOL = stringPreferencesKey("key_app_signature_school")
        private val CODE = stringPreferencesKey("key_app_signature_code")
        private val NAME_S = stringPreferencesKey("key_app_signature_name")
        private val PERIOD_s = stringPreferencesKey("key_app_signature_period_s")
        private val LAST = stringPreferencesKey("key_app_signature_last")
        // SETTINGS
        private val COLOR = stringPreferencesKey("key_app_signature_color")
        private val FONT = floatPreferencesKey("key_app_signature_font")
        private val SIZE_LABEL = floatPreferencesKey("key_app_signature_size_label")
        private val SIZE_BOX = floatPreferencesKey("key_app_signature_size_box")
    }
}
