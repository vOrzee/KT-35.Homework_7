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
) : FillerContent<Comment> {
    private var id: Int? = null
    private var ownerId: Int? = null
    private var date: Int? = null
    private var commentsList: MutableList<Comment> = mutableListOf()
    override fun add(vararg content: Comment): List<Comment> {
        content.forEach {
            if (!commentsList.contains(it)) commentsList.add(it)
            else throw PublishedBeforeException("Один или несколько комментариев уже были опубликованы")
        }
        return content.toList()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Post) this.id == other.id
        else false
    }

    override fun add(content: Comment): Comment {
        if (!commentsList.contains(content)) commentsList.add(content)
        else throw PublishedBeforeException("Этот комментарий уже был опубликован")
        return commentsList.last()
    }

    override fun update(content: Comment): Boolean {
        commentsList.forEach {
            if (it.getID() == content.getID()) {
                commentsList[commentsList.indexOf(it)] = content
                return true
            }
        }
        return false
    }

    fun print(): String {
        return "Post ID: $id was published ${this.getDate()} in wall user ID: $ownerId " + this.toString()
    }

    companion object {
        private var totalID: Int = 0
    }

    fun fillOutOf(postChanged: Post): Post {
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
        if (date != null) SimpleDateFormat("dd.MM.yyyy в HH:mm:ss").format(Date(((date ?: 0) * 1000).toLong()))
        else "Запись ещё не опубликована"

    fun getDateUnixTime() = date
    override fun hashCode(): Int {
        var result = fromId
        result = 29 * result + text.hashCode()
        result = 29 * result + createdBy
        result = 29 * result + replyOwnerId
        result = 29 * result + replyPostId
        result = 29 * result + friendsOnly.hashCode()
        result = 29 * result + comments.hashCode()
        result = 29 * result + copyright.hashCode()
        result = 29 * result + likes.hashCode()
        result = 29 * result + reposts.hashCode()
        result = 29 * result + views.hashCode()
        result = 29 * result + postType.hashCode()
        result = 29 * result + postSource.hashCode()
        result = 29 * result + geo.hashCode()
        result = 29 * result + (signerId ?: 0)
        result = 29 * result + canPin.hashCode()
        result = 29 * result + canDelete.hashCode()
        result = 29 * result + canEdit.hashCode()
        result = 29 * result + isPinned.hashCode()
        result = 29 * result + markedAsAds.hashCode()
        result = 29 * result + isFavorite.hashCode()
        result = 29 * result + (postponedId ?: 0)
        result = 29 * result + (attachments?.contentHashCode() ?: 0)
        result = 29 * result + (id ?: 0)
        result = 29 * result + (ownerId ?: 0)
        result = 29 * result + (date ?: 0)
        result = 29 * result + commentsList.hashCode()
        return result
    }
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
