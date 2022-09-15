import org.junit.Test
import attachments.*
import org.junit.Assert.*

class WallServiceTest {

    @Test
    fun testAdds() {
        WallService.clear()
        val ownerID = 734
        val post = Post(734, "Какая-то запись")
        val resultPost = WallService.add(ownerID, post)
        assertEquals(resultPost.getID(), WallService.posts[WallService.posts.lastIndex].getID())
    }

    @Test
    fun testAddsRepeat() {
        WallService.clear()
        val ownerID = 734
        val post = WallService.add(ownerID, Post(734, "Какая-то запись"))
        try {
            WallService.add(ownerID, post)
            assertTrue(false)
        } catch (e: PublishedBeforeException) {
            assertEquals("Этот пост уже был опубликован", e.message)
        }
    }

    @Test
    fun testAddsRepeatVararg() {
        WallService.clear()
        val ownerID = 734
        val post = WallService.add(ownerID, Post(734, "Какая-то запись"))
        val post2 = Post(734, "Какая-то ещё запись")
        try {
            WallService.add(ownerID, post, post2)
            assertTrue(false)
        } catch (e: PublishedBeforeException) {
            assertEquals("Один или несколько постов уже были опубликованы", e.message)
        }
    }

    @Test
    fun testUpdateInText() {
        WallService.clear()
        val ownerID = 734
        val post1 = Post(734, "Первый пост")
        val post2 = Post(9532, "Второй пост")
        val post3 = Post(734, "Третий пост")
        val post4 = Post(734, "Четвёртый пост")
        WallService.add(ownerID, post1, post2, post3, post4)
        post3.text = "Изменённый в третьем посту текст"
        WallService.update(post3)
        if (WallService.posts.filter { it.getID() == post3.getID() }.size != 1) assertTrue(false)
        assertEquals(
            "Изменённый в третьем посту текст",
            WallService.posts.filter { it.getID() == post3.getID() }[0].text
        )
    }

    @Test
    fun testUpdateIn() {
        WallService.clear()
        val ownerID = 734
        val post1 = Post(734, "Первый пост")
        val post2 = Post(9532, "Второй пост")
        val post3 = Post(734, "Третий пост")
        WallService.add(ownerID, post1, post2, post3)
        post3.text = "Изменённый в третьем посту текст"
        assertTrue(WallService.update(post3))
    }

    @Test
    fun testUpdateOut() {
        WallService.clear()
        val ownerID = 734
        val post1 = Post(734, "Первый пост")
        val post2 = Post(9532, "Второй пост")
        val post3 = Post(734, "Третий пост")
        val post4 = Post(734, "Четвёртый пост")
        WallService.add(ownerID, post1, post2, post4)
        assertTrue(!WallService.update(post3))
    }

    @Test
    fun testCopyPost() {
        WallService.clear()
        val ownerID = arrayOf(734, 856)
        val post1: Post = WallService.add(ownerID[0], Post(734, "Первый пост"))
        val post2: Post = WallService.add(ownerID[1], Post(734, "Второй пост"))
        val post3 = post1.copy(post2)
        assertTrue(
            post1.getID() == post3.getID()
                    && post1.getOwnerID() == post3.getOwnerID()
                    && post1.getDateUnixTime() == post3.getDateUnixTime()
                    && post2 == post3 //Equals() применяется только для параметров в конструкторе
        )
    }

    @Test
    fun testSetPosts() {
        WallService.clear()
        val ownerID = arrayOf(734, 856)
        val posts: Array<Post> = arrayOf(
            WallService.add(ownerID[0], Post(734, "Первый пост")),
            WallService.add(ownerID[1], Post(734, "Второй пост"))
        )
        WallService.clear()
        WallService.posts = posts
        assertEquals(posts.toString(), WallService.posts.toString())
    }

    @Test
    fun getAttachmentsIsEmpty() {
        val actualResult = WallService.getAttachments(
            Post(
                1,
                "my fourth post"
            )
        )
        assertTrue(actualResult == null)
    }

    @Test
    fun getAttachmentsIsContains() {
        val post = WallService.add(
            734,
            Post(
                1,
                "my fourth post",
                attachments = arrayOf(
                    AudioAttachment(
                        audio = Audio(
                            5,
                            65,
                            "",
                            "",
                            98,
                            "",
                            null,
                            null,
                            5,
                            98756,
                            false,
                            true
                        )
                    )
                )
            )
        )
        assertTrue(WallService.getAttachments(post) is Array<Attachment>)
    }

    @Test(expected = PostNotFoundException::class)
    fun addCommentWithUnrealPost() {
        val post3 = WallService.add(734, Post(734, "Третий пост"))
        WallService.clear()
        val ownerID = 734
        val post1 = Post(734, "Первый пост")
        val post2 = Post(9532, "Второй пост")
        val post4 = Post(734, "Четвёртый пост")
        val comment = Comment(864, "Самый обычный комментарий")
        WallService.add(ownerID, post1, post2, post4)
        post3.getID()?.let {
            WallService.createComment(it, comment)
        }
    }

    @Test
    fun addCommentWithRealPost() {
        WallService.clear()
        val ownerID = 734
        val post1 = Post(734, "Первый пост")
        val post2 = Post(9532, "Второй пост")
        val post3 = Post(734, "Третий пост")
        val comment = Comment(864, "Самый обычный комментарий")
        WallService.add(ownerID, post1, post2, post3)
        post3.getID()?.let {
            assertEquals(comment, WallService.createComment(it, comment))
        }
    }

    @Test(expected = IndexOutOfBoundsReasonReportException::class)
    fun addCommentWithUnrealReason() {
        val comment = Comment(864, "Самый обычный комментарий")
        val report = comment.reportComment()
        assertEquals(comment.reportComments.last(), report)
    }

    @Test
    fun addCommentWithRealReason() {
        val comment = Comment(864, "Самый обычный комментарий")
        val report = comment.reportComment(5)
        assertEquals(comment.reportComments.last(), report)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun addCommentWithUnrealComment() {
        val commentId: Long = 8743L
        val report = ReportComment(784, commentId, 4)
        WallService.comments.filter { it.getID() == commentId }[0].reportComments.plus(report)
    }

    @Test
    fun addCommentWithRealComment() {
        WallService.clear()
        val comment = Comment(864, "Самый обычный комментарий")
        val post1 = WallService.add(734, Post(734, "Первый пост"))
        post1.getID()?.let { WallService.createComment(it, comment) }
        val report = ReportComment(784, comment.getID(), 4)
        WallService.comments.forEach {
            if (it.getID() == comment.getID()) it.reportComments += report
        }
        assertEquals(WallService.comments.last().reportComments.last(), report)
    }

}