import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.Date

data class Post(
    val ownerId: Int,
    val fromID: Int,
    var text: String,
    var markedAsAds: Boolean = false,
    var isPinned: Boolean = false,
    var friendsOnly: Boolean = false,
    var postType: PostType = PostType.POST
) {
    private var id: Int = -1
    private var date = 0
    var canDelete = false
    var isFavorite = false
    var comments: MutableList<Comments> = mutableListOf() // ArrayList
    var likes: MutableList<Likes> = mutableListOf() // ArrayList

    init {
        id += 1
        date = (currentTimeMillis() / 1000).toInt()
    }

    fun getID() = id
    fun getDate(): String = SimpleDateFormat("dd.MM.yyyy в HH:mm:ss").format(Date(date.toLong() * 1000))
}