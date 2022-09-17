import attachments.Attachment
import java.text.SimpleDateFormat
import java.util.*

data class Comment(
    val fromID: Int,
    var text: String,
    var donut: Any? = arrayOf(false, "Заглушка"),
    val replyToUser: Int? = null,
    val replyToComment: Int? = null,
    var attachments: Attachment? = null,
    val parentsStack: Array<Int>? = null,
    val thread: ThreadComment? = null,
    var reportComments: Array<ReportComment> = emptyArray()
) {
    private val id: Long
    private var date: Int

    override fun equals(other: Any?): Boolean {
        return if (other is Comment) this.id == other.id
        else false
    }

    fun getDate(): String =
        SimpleDateFormat("dd.MM.yyyy в HH:mm:ss").format(Date((date * 1000).toLong()))

    fun getID() = id
    fun getDateUnixTime() = date

    init {
        Enumerator.countComment += 1
        id = Enumerator.countComment
        date = (System.currentTimeMillis() / 1000).toInt()
    }

    fun reportComment(reason: Byte = -1): ReportComment {
        this.reportComments += ReportComment(this.fromID, this.id, reason)
        return reportComments.last()
    }

    override fun hashCode(): Int {
        var result = fromID
        result = 31 * result + text.hashCode()
        result = 31 * result + (donut?.hashCode() ?: 0)
        result = 31 * result + (replyToUser ?: 0)
        result = 31 * result + (replyToComment ?: 0)
        result = 31 * result + (attachments?.hashCode() ?: 0)
        result = 31 * result + (parentsStack?.contentHashCode() ?: 0)
        result = 31 * result + (thread?.hashCode() ?: 0)
        result = 31 * result + reportComments.contentHashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + date
        return result
    }
}

data class ThreadComment(
    var count: Int = 0,
    var items: Array<Any>? = null,
    var canPost: Boolean = true,
    var showReplyButton: Boolean = true,
    var groups_can_post: Boolean = true
)

data class ReportComment(
    val ownerId: Int,
    val commentId: Long,
    val reason: Byte = 9
) {
    init {
        if (reason !in 0..8) {
            throw IndexOutOfBoundsReasonReportException("Не указана причина жалобы, либо причина несущественна")
        }
    }
}