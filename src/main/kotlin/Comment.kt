import attachments.Attachment
import java.text.SimpleDateFormat
import java.util.*

data class Comment(
    val fromID: Int,
    var text: String,
    var donut:Any? = arrayOf(false, "Заглушка"),
    val replyToUser:Int? = null,
    val replyToComment:Int? = null,
    var attachments:Attachment? = null,
    var parentsStack:Array<Int>? = null,
    val thread:ThreadComment? = null
    ) {
    private val id: Long
    private var date: Int? = null

    companion object {
        private var count: Long = 0
    }
    fun getDate(): String =
        if (date != null) SimpleDateFormat("dd.MM.yyyy в HH:mm:ss").format(Date(((date?:0) * 1000).toLong()))
        else "Запись ещё не опубликована"
    fun getID() = id
    fun getDateUnixTime() = date

    init {
        count += 1
        id = count
        date = (System.currentTimeMillis() / 1000).toInt()
    }

}
data class ThreadComment(
    var count:Int=0,
    var items:Array<Any>?=null,
    var canPost:Boolean = true,
    var showReplyButton:Boolean = true,
    var groups_can_post:Boolean = true
)