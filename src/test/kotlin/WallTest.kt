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
    fun testUpdateInText() {
        val wallService = Wall(734)
        val post1 = Post(734, "Первый пост")
        val post2 = Post(9532, "Второй пост")
        val post3 = Post(734, "Третий пост")
        val post4 = Post(734, "Четвёртый пост")
        wallService.add(post1, post2, post3, post4)
        post3.text = "Изменённый в третьем посту текст"
        wallService.update(post3)
        if (wallService.posts.filter { it.getID() == post3.getID() }.size!=1) assertTrue(false)
        assertEquals("Изменённый в третьем посту текст", wallService.posts.filter { it.getID() == post3.getID() }[0].text)
    }

    @Test
    fun testUpdateIn() {
        val wallService = Wall(734)
        val post1 = Post(734, "Первый пост")
        val post2 = Post(9532, "Второй пост")
        val post3 = Post(734, "Третий пост")
        wallService.add(post1, post2, post3)
        assertTrue(wallService.update(post3))
    }

    @Test
    fun testUpdateOut() {
        val wallService = Wall(734)
        val post1 = Post(734, "Первый пост")
        val post2 = Post(9532, "Второй пост")
        val post3 = Post(734, "Третий пост")
        val post4 = Post(734, "Четвёртый пост")
        wallService.add(post1)
        wallService.add(post2)
        wallService.add(post4)
        assertTrue(!wallService.update(post3))
    }
}