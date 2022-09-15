import attachments.*
import org.junit.Assert

object WallService {
    var posts = emptyArray<Post>()
    var comments = emptyArray<Comment>()

    fun createComment(postId: Int, comment: Comment): Comment {
        if (posts.filter { it.getID() == postId }.size != 1)
            throw PostNotFoundException("Такого поста не существует")
        else {
            comments += comment
            posts.filter { it.getID() == postId }[0].add(comment)
        }
        return comments.last()
    }

    fun clear() {
        posts = emptyArray()
        comments = emptyArray()
    }

    fun add(ownerID: Int, vararg content: Post): Array<Post> {
        content.forEach {
            if (it.publish(ownerID)) posts += it
            else throw PublishedBeforeException("Один или несколько постов уже были опубликованы")
        }
        return arrayOf(*content)
    }

    fun add(ownerID: Int, content: Post): Post {
        if (content.publish(ownerID)) posts += content
        else throw PublishedBeforeException("Этот пост уже был опубликован")
        return posts.last()
    }

    fun update(content: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (post.getID() == content.getID()) {
                posts[index] = posts[index].copy(content)
                return true
            }
        }
        return false
    }

    fun getAttachments(post: Post): Array<Attachment>? {
        for (p in posts) {
            if (p.getID() == post.getID()) {
                return post.attachments
            }
        }
        return null
    }
}