class Likes(val fromID:Int, val postID:Int) {
    companion object {
        private var count: Int = 0
    }

    init {
        count += 1
    }

}