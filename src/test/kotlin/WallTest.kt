import org.junit.Assert.*
import org.junit.Test

class WallTest {
    @Test
    fun testAdds() {
        val post: Post = Post(856, text = "Какая-то запись")
        assertEquals(post.getID(), Wall(856).add(post).getID())
    }

    @Test
    fun testUpdateInText() {
        var wallService: Wall = Wall(734)
        val post1:Post = Post(734, 856, "Первый пост")
        val post2:Post = Post(734, text = "Второй пост")
        val post3:Post = Post(734, text = "Третий пост")
        val post4:Post = Post(734, 635, "Четвёртый пост")
        wallService.add(post1,post2,post3,post4)
        post3.text = "Изменённый в третьем посту текст"
        wallService.update(post3)
        assertEquals("Изменённый в третьем посту текст", wallService.posts[2].text)
    }
    @Test
    fun testUpdateIn() {
        var wallService: Wall = Wall(734)
        val post1:Post = Post(734, 856, "Первый пост")
        val post2:Post = Post(734, text = "Второй пост")
        val post3:Post = Post(734, text = "Третий пост")
        wallService.add(post1,post2,post3)
        assertTrue(wallService.update(post3))
    }

    @Test
    fun testUpdateOut() {
        var wallService: Wall = Wall(734)
        val post1:Post = Post(734, 856, "Первый пост")
        val post2:Post = Post(734, text = "Второй пост")
        val post3:Post = Post(734, text = "Третий пост")
        val post4:Post = Post(734, 635, "Четвёртый пост")
        wallService.add(post1)
        wallService.add(post2)
        wallService.add(post4)
        assertTrue(!wallService.update(post3))
    }
}