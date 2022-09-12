data class Like(val fromID:Int, val postID:Int) {
    private val id:Int
    companion object {
        private var count: Int = 0
    }

    init {
        count += 1
        id = count
    }

}