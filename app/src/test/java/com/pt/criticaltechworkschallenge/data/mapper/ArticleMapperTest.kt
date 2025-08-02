package com.pt.criticaltechworkschallenge.data.mapper

import com.pt.criticaltechworkschallenge.data.remote.dto.ArticleDto
import com.pt.criticaltechworkschallenge.data.remote.dto.SourceDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ArticleMapperTest {

    @Test
    fun `mapping with complete data works correctly`() {
        val articleDto = ArticleDto(
            source = SourceDto(id = "techcrunch", name = "TechCrunch"),
            author = "John Doe",
            title = "Test Title",
            description = "Test Description",
            url = "https://example.com",
            urlToImage = "https://example.com/image.jpg",
            publishedAt = "2025-08-02T12:00:00Z",
            content = "Test content."
        )

        val articleDomain = articleDto.toDomain()

        assertEquals("TechCrunch", articleDomain.sourceName)
        assertEquals("John Doe", articleDomain.author)
        assertEquals("Test Title", articleDomain.title)
        assertEquals("Test Description", articleDomain.description)
        assertEquals("https://example.com", articleDomain.url)
        assertEquals("https://example.com/image.jpg", articleDomain.urlToImage)
        assertEquals("2025-08-02T12:00:00Z", articleDomain.publishedAt)
        assertEquals("Test content.", articleDomain.content)
    }

    @Test
    fun `mapping with null data uses fallbacks correctly`() {
        val articleDto = ArticleDto(
            source = SourceDto(id = null, name = "Some Source"),
            author = null,
            title = "Another Title",
            description = null,
            url = "https://example.com/2",
            urlToImage = null,
            publishedAt = "2025-08-02T13:00:00Z",
            content = null
        )

        val articleDomain = articleDto.toDomain()

        assertEquals("Some Source", articleDomain.sourceName)
        assertNull(articleDomain.author)
        assertEquals("Another Title", articleDomain.title)
        assertEquals("", articleDomain.description)
        assertNull(articleDomain.urlToImage)
        assertEquals("", articleDomain.content)
    }
}