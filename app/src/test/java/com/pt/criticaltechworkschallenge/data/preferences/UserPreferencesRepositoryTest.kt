package com.pt.criticaltechworkschallenge.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@ExperimentalCoroutinesApi
class UserPreferencesRepositoryTest {

    @get:Rule
    val temporaryFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private lateinit var testDataStore: DataStore<Preferences>
    private lateinit var repository: UserPreferencesRepository

    private val testScope = TestScope(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        testDataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { temporaryFolder.newFile("test_prefs.preferences_pb") }
        )
        repository = UserPreferencesRepository(testDataStore)
    }

    @Test
    fun `when no preference is saved, it should return the default source`() = testScope.runTest {
        val result = repository.selectedSource.first()

        assertEquals("techcrunch", result.first)
        assertEquals("TechCrunch", result.second)
    }

    @Test
    fun `when a preference is saved, the Flow should emit the new source`() = testScope.runTest {
        val sourceId = "bloomberg"
        val sourceName = "Bloomberg"

        repository.saveSourcePreference(sourceId, sourceName)

        val result = repository.selectedSource.first()

        assertEquals(sourceId, result.first)
        assertEquals(sourceName, result.second)
    }

    @Test
    fun `saveSourcePreference should call the edit method on DataStore`() = testScope.runTest {
        val sourceId = "abc-news"
        val sourceName = "ABC News"

        repository.saveSourcePreference(sourceId, sourceName)

        val savedPrefs = testDataStore.data.first()
        assertEquals(sourceId, savedPrefs[UserPreferencesRepository.Keys.SELECTED_SOURCE_ID])
        assertEquals(sourceName, savedPrefs[UserPreferencesRepository.Keys.SELECTED_SOURCE_NAME])
    }
}