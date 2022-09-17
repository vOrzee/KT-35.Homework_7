object Enumerator { //счётчик может быть не один
    var countPosts: Int = 0
    var countComment: Long = 0

    fun clear() {
        /* Основной задачей счётчика является обеспечение уникальности значения,
            поэтому его сброс нежелателен в реальных условиях
         */
        countPosts = 0
        countComment = 0
    }
}