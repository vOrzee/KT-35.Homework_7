import org.junit.Assert.*
import org.junit.Test

class WallTest {
    @Test
    fun testAdds() {
        val wallService = Wall(734)
        val post = Post(734, "Какая-то запись")
        val resultPost = wallService.add(post)
        assertEquals(resultPost.getID(), wallService.posts[wallService.posts.lastIndex].getID())
    }

    @Test
    fun testAddsRepeat() {
        val wallService = Wall(734)
        val post = wallService.add(Post(734, "Какая-то запись"))
        try {
            wallService.add(post)
            assertTrue(false)
        } catch (e: PublishedBeforeException) {
            assertEquals("Этот пост уже был опубликован", e.message)
        }
    }

    @Test
    fun testAddsRepeatVararg() {
        val wallService = Wall(734)
        val post = wallService.add(Post(734, "Какая-то запись"))
        val post2 = Post(734, "Какая-то ещё запись")
        try {
            wallService.add(post, post2)
            assertTrue(false)
        } catch (e: PublishedBeforeException) {
            assertEquals("Один или несколько постов уже были опубликованы", e.message)
        }
    }

    @Test
    fun testUpdateInText() {
        val wallService = Wall(734)
        val post1 = Post(734, "Первый пост")
        val post2 = Post(9532, "Второй пост")
        val post3 = Post(734, "Третий пост")
        val post4 = Post(734, "Четвёртый пост")
        wallService.add(post1, post2, post3, post4)
        post3.text = "Изменённый в третьем посту текст"
        wallService.update(post3)
        if (wallService.posts.filter { it.getID() == post3.getID() }.size != 1) assertTrue(false)
        assertEquals(
            "Изменённый в третьем посту текст",
            wallService.posts.filter { it.getID() == post3.getID() }[0].text
        )
    }

    @Test
    fun testUpdateIn() {
        val wallService = Wall(734)
        val post1 = Post(734, "Первый пост")
        val post2 = Post(9532, "Второй пост")
        val post3 = Post(734, "Третий пост")
        wallService.add(post1, post2, post3)
        post3.text = "Изменённый в третьем посту текст"
        assertTrue(wallService.update(post3))
    }

    @Test
    fun testUpdateOut() {
        val wallService = Wall(734)
        val post1 = Post(734, "Первый пост")
        val post2 = Post(9532, "Второй пост")
        val post3 = Post(734, "Третий пост")
        val post4 = Post(734, "Четвёртый пост")
        wallService.add(post1, post2, post4)
        assertTrue(!wallService.update(post3))
    }

    @Test
    fun testCopyPost() {
        val wallService = Wall(734)
        val wallService2 = Wall(865)
        val post1: Post = wallService.add(Post(734, "Первый пост"))
        val post2: Post = wallService2.add(Post(734, "Второй пост"))
        val post3 = post1.copy(post2)
        assertTrue(
            post1.getID() == post3.getID()
                    && post1.getOwnerID() == post3.getOwnerID()
                    && post1.getDateUnixTime() == post3.getDateUnixTime()
                    && post2 == post3 //Equals() применяется только для параметров в конструкторе
        )
    }

    @Test
    fun dateIsNullable() {
        val post1 = Post(734, "Первый пост")
        val result = post1.getDate()
        val result2 = post1.getDateUnixTime()
        assertTrue("Запись ещё не опубликована" == result && result2 == null)
    }
}