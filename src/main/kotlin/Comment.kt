data class Comment(val fromID: Int, val postID: Int, var textMessage: String) {
    private val id: Int

    companion object {
        private var count: Int = 0
    }

    init {
        count += 1
        id = count
    }

}