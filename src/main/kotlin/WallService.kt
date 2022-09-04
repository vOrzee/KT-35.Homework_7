class WallService(private val ownerID: Int) {
    var posts: MutableList<Post> = mutableListOf()

    fun add(vararg post: Post): List<Post> {
        post.forEach {
            if (it.publish(ownerID)) posts.add(it)
            else throw Exception("Один или несколько постов уже были опубликованы")
        }
        return post.toList()
    }

    fun add(post: Post): Post {
        if (post.publish(ownerID)) posts.add(post)
        else throw Exception("Этот пост уже был опубликован")
        return posts[posts.size - 1]
    }

    fun update(post: Post): Boolean {
        posts.forEach {
            if (it.getID() == post.getID()) {
                posts[posts.indexOf(it)] = Post.copy(posts[posts.indexOf(it)],post)
                return true
            }
        }
        return false
    }
}