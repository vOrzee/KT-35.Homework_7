class Wall {
    var posts: MutableList<Post> = mutableListOf()
    fun add(post: Post) {
        posts.add(post)
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