import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.Date


data class Post(
    val fromID: Int,
    var text: String,
    var markedAsAds: Boolean = false,
    var isPinned: Boolean = false,
    var friendsOnly: Boolean = false,
    var postType: PostType = PostType.POST,
    var canDelete: Boolean = false,
    var isFavorite: Boolean = false
) {
    private var id: Int? = null
    private var ownerID: Int? = null
    private var date: Int? = null
    private var comments: MutableList<Comments> = mutableListOf()
    private var likes: MutableList<Likes> = mutableListOf()

    companion object {
        private var totalID: Int = 0
        fun copy(postOriginal: Post, postChanged: Post): Post {
            val postAfter: Post = postChanged
            postAfter.date = postOriginal.date
            postAfter.id = postOriginal.id
            postAfter.ownerID = postOriginal.ownerID
            postAfter.comments = postOriginal.comments
            postAfter.likes = postOriginal.likes
            return postAfter
        }
    }

    fun publish(ownerID: Int): Boolean = if (id == null) {
        totalID += 1
        id = totalID
        date = (currentTimeMillis() / 1000).toInt()
        this.ownerID = ownerID
        true
    } else false

    fun getOwnerID() = ownerID
    fun getID() = id
    fun getDate(): String = if (date!=null) SimpleDateFormat("dd.MM.yyyy в HH:mm:ss").format(Date(date!!.toLong() * 1000))
                            else "Запись ещё не опубликована"
    fun getDateUnixTime() = date
}