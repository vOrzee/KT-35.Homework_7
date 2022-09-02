class Wall(private val ownerID: Int) {
    var posts: MutableList<Post> = mutableListOf()
    fun add(vararg post: Post): List<Post> {
        post.forEach {
            it.initID()
            it.ownerId = ownerID
            posts.add(it)
        }
        return post.toList()
    }

    fun add(post: Post): Post {
        post.initID()
        post.ownerId = ownerID
        posts.add(post)
        return posts[posts.size - 1]
    }

    fun update(post: Post): Boolean {
        for (p: Post in posts) {
            if (p.getID() == post.getID()) {
                p.text = post.text
                p.markedAsAds = post.markedAsAds
                p.isPinned = post.isPinned
                p.friendsOnly = post.friendsOnly
                p.isPinned = post.isPinned
                p.canDelete = post.canDelete
                p.isFavorite = post.isFavorite
                p.comments = post.comments
                p.likes = post.likes
                return true
            }
        }
        return false
    }
}