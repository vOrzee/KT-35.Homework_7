class Likes {
    /*
    Информация о лайках к записи, объект с полями:

        count (integer) — число пользователей, которым понравилась запись;
        user_likes* (integer, [0,1]) — наличие отметки «Мне нравится» от текущего пользователя (1 — есть, 0 — нет);
        can_like* (integer, [0,1]) — информация о том, может ли текущий пользователь поставить отметку «Мне нравится» (1 — может, 0 — не может);
        can_publish* (integer, [0,1]) — информация о том, может ли текущий пользователь сделать репост записи (1 — может, 0 — не может).
     */
    private var count: Int = 0
    var userLikes: Boolean = false
    var canLike: Boolean = true
    var canPublish: Boolean = true

    init {
        count += 1
    }
}