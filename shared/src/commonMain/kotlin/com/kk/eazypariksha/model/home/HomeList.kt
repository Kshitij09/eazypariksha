package com.kk.eazypariksha.model.home

object HomeList {
    val list: List<HomeListModel> = listOf(
        HomeListModel("Add Exam", "plus.square.fill.on.square.fill"),
        HomeListModel("Drafts", "doc.fill.badge.ellipsis"),
        HomeListModel("Upcoming Exams", "clock.arrow.2.circlepath"),
        HomeListModel("result", "checkmark.seal.fill"),
        HomeListModel("Search Students", "text.magnifyingglass")
    )
}


data class HomeListModel(
    val title: String,
    val image: String
)
