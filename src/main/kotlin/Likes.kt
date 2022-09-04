class Likes {
    companion object {
        private var count: Int = 0
    }
    var userLikes: Boolean = false
    var canLike: Boolean = true
    var canPublish: Boolean = true

    init {
        count += 1
    }
}