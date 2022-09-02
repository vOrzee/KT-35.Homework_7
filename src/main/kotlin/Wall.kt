class Wall(ownerID:Int) {
    var posts: MutableList<Post> = mutableListOf()
    fun add(vararg post: Post) {
        post.forEach {
            posts.add(it)
        }
    }
    fun add(post: Post):Post {
        posts.add(post)
        return posts[posts.size-1]
    }

    fun update(vararg post: Post): Boolean {
        for (p: Post in posts) {
            post.forEach {
                if (p.getID() == it.getID()) {
                    p.text = it.text
                    p.markedAsAds = it.markedAsAds
                    p.isPinned = it.isPinned
                    p.friendsOnly = it.friendsOnly
                    p.isPinned = it.isPinned
                    p.canDelete = it.canDelete
                    p.isFavorite = it.isFavorite
                    p.comments = it.comments
                    p.likes = it.likes
                    return true
                }
            }
        }
        return false
    }
}