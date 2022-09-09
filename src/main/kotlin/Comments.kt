class Comments(val fromID:Int, val postID:Int, var textMessage:String) {
    companion object {
        private var count: Int = 0
    }

    init {
        count += 1
    }

}