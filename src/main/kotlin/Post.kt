import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.Date


data class Post(
    val fromID: Int,
    var text: String,
    var markedAsAds: Boolean = false,
    var isPinned: Boolean = false,
    var friendsOnly: Boolean = false,
    var postType: PostType = PostType.POST
) {
    companion object {
        private var totalID: Int = 0
    }

    var ownerId: Int? = null
    private var id: Int? = null
    private var date = 0
    var canDelete = false
    var isFavorite = false
    var comments: MutableList<Comments> = mutableListOf() // ArrayList
    var likes: MutableList<Likes> = mutableListOf() // ArrayList

    init {
        date = (currentTimeMillis() / 1000).toInt()
    }

    fun initID() {
        totalID += 1
        id = totalID
    }

    fun getID() = id
    fun getDate(): String = SimpleDateFormat("dd.MM.yyyy в HH:mm:ss").format(Date(date.toLong() * 1000))
}