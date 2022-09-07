object WallService {
    var posts = emptyArray<Post>() //массивы так массивы :)

    fun clear(){
        posts = emptyArray()
    }

    fun add(ownerID:Int, vararg content: Post): Array<Post> {
        content.forEach {
            if (it.publish(ownerID)) posts = posts.add(it)
            else throw PublishedBeforeException("Один или несколько постов уже были опубликованы")
        }
        return arrayOf(*content)
    }

    fun add(ownerID:Int, content: Post): Post {
        if (content.publish(ownerID)) posts = posts.add(content)
        else throw PublishedBeforeException("Этот пост уже был опубликован")
        return posts[posts.size - 1]
    }

    fun update(content: Post): Boolean {
        posts.forEach {
            if (it.getID() == content.getID()) {
                posts[posts.indexOf(it)] = posts[posts.indexOf(it)].copy(content)
                return true
            }
        }
        return false
    }
}
// extensions for Array
private fun Array<Post>.add(element: Post): Array<Post> {
    val list: MutableList<Post> = this.toMutableList()
    list.add(element)
    return list.toTypedArray()
}