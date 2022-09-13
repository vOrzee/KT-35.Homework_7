object WallService {
    var posts = emptyArray<Post>()

    fun clear() {
        posts = emptyArray()
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
}