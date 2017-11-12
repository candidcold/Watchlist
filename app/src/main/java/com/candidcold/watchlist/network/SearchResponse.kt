package com.candidcold.watchlist.network


data class SearchResponse(val page: Int,
                          val results: List<SearchResult>,
                          val total_results: Int,
                          val total_pages: Int)

data class SearchResult(val id: Int,
                        val poster_path: String?,
                        val profile_path: String?,
                        val media_type: String,
                        val name: String?,
                        val title: String?,
                        val popularity: Double)