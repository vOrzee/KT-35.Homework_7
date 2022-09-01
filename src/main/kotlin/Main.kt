fun main() {
    var wallService:MutableList<Post> = mutableListOf() // ArrayList
    wallService.add(Post(
        256,
        567,
        "Первая тестовая запись"
    ))
    for (post:Post in wallService){
        println(post.text)
        println(post.getID())
        println(post.getDate())
        println(post)
    }
}