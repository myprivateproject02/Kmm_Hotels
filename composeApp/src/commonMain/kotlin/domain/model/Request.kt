package domain.model

data class Request(
    val comments: String,
    val created_at: String,
    val desk: Desk,
    val fulfilled_by: Fulfilled,
    val hotel: Hotel,
    val id: Int,
    val name: String,
    val unit: String,
    val updated_at: String
)