import attachments.*
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.Date


data class Post(
    val fromId: Int,
    var text: String,
    val createdBy: Int = 0,
    val replyOwnerId: Int = 0,
    val replyPostId: Int = 0,
    var friendsOnly: Boolean = false,
    val comments: Comments = Comments(0, true, true, true, true),
    var copyright: String = "Netology",
    val likes: Likes = Likes(0, false, true, true),
    val reposts: Reposts = Reposts(0, false),
    val views: Views = Views(0),
    var postType: PostType = PostType.POST,
    var postSource: PostSource = PostSource("Any info"),
    val geo: Geo = Geo(),
    val signerId: Int? = null,
    var canPin: Boolean = true,
    var canDelete: Boolean = true,
    var canEdit: Boolean = true,
    var isPinned: Boolean = false,
    var markedAsAds: Boolean = false,
    var isFavorite: Boolean = false,
    var postponedId: Int? = null,
    val attachments: Array<Attachment>? = null
) {
    private var id: Int? = null
    private var ownerId: Int? = null
    private var date: Int? = null

    override fun toString(): String {
        return "Post ID: $id was published ${this.getDate()} in wall user ID: $ownerId " + super.toString()
    }

    companion object {
        private var totalID: Int = 0
    }

    fun copy(postChanged: Post): Post {
        val postAfter: Post = postChanged
        postAfter.date = this.date
        postAfter.id = this.id
        postAfter.ownerId = this.ownerId
        return postAfter
    }

    fun publish(ownerID: Int): Boolean = if (id == null) {
        totalID += 1
        id = totalID
        date = (currentTimeMillis() / 1000).toInt()
        this.ownerId = ownerID
        true
    } else false

    fun getOwnerID() = ownerId
    fun getID() = id
    fun getDate(): String =
        if (date != null) SimpleDateFormat("dd.MM.yyyy в HH:mm:ss").format(Date(date!!.toLong() * 1000))
        else "Запись ещё не опубликована"

    fun getDateUnixTime() = date
}

data class Comments(
    val count: Int,
    val canPost: Boolean,
    val groupsCanPost: Boolean,
    val canClose: Boolean,
    val canOpen: Boolean
)

data class Likes(
    val count: Int,
    val userLikes: Boolean,
    val canLike: Boolean,
    val canPublish: Boolean
)

data class Reposts(
    val count: Int,
    val userReposted: Boolean
)

data class Views(
    val count: Int
)

data class PostSource(
    val info: String = "Some info"
)

data class Geo(
    val type: String = "Some place",
    val coordinates: String = "Nothing",
    val place: Any? = null
)
