interface FillerContent<T> {
    //Возможно стоит разбить на 2 интерфейса
    fun add(vararg content: T): List<T>
    fun add(content: T): T
    fun update(content: T): Boolean
}