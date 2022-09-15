import attachments.*
class Wall(private val ownerID: Int) : FillerContent<Post> {
    //TODO объект должен создаваться при регистрации
    var posts: MutableList<Post> = mutableListOf()

    override fun add(vararg content: Post): List<Post> {
        content.forEach {
            if (it.publish(ownerID)) posts.add(it)
            else throw PublishedBeforeException("Один или несколько постов уже были опубликованы")
        }
        return content.toList()
    }

    override fun add(content: Post): Post {
        if (content.publish(ownerID)) posts.add(content)
        else throw PublishedBeforeException("Этот пост уже был опубликован")
        return posts.last()
    }

    override fun update(content: Post): Boolean {
        posts.forEach {
            if (it.getID() == content.getID()) {
                posts[posts.indexOf(it)] = posts[posts.indexOf(it)].copy(content)
                return true
            }
        }
        return false
    }
}