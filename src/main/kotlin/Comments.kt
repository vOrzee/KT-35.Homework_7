class Comments {
    companion object {
        private var count: Int = 0
    }
    var canPost: Boolean = true
    var groupsCanPost: Boolean = true
    var canClose: Boolean = false
    var canOpenBoolean = false

    init {
        count += 1
    }
}