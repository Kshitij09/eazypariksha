package com.kk.eazypariksha.model.home

object HomeList {
    val list: List<HomeListModel> = listOf(
        HomeListModel("Add Exam", "plus.square.fill.on.square.fill", "AddExamViewController"),
        HomeListModel("Drafts", "doc.fill.badge.ellipsis", "AddExamViewController"),
        HomeListModel("Upcoming Exams", "clock.arrow.2.circlepath", "AddQuestionsViewController"),
        HomeListModel("result", "checkmark.seal.fill","AddExamViewController"),
        HomeListModel("Search Students", "text.magnifyingglass","AddExamViewController")
    )
}


data class HomeListModel(
    val title: String,
    val image: String,
    val type: String
)
