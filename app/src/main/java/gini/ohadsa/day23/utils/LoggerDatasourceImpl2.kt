package gini.ohadsa.day23.utils


import javax.inject.Inject


//InMemoryLogger (Good for Unit Tests)//example (not for production)
class LoggerDatasourceImpl2 constructor(private val list: MutableList<String>) : LoggerDatasource {
    @Inject
    constructor() : this(mutableListOf())

    override fun addLog(msg: String) {
        list.add("$msg Tests Logger")
    }

    override fun getAllLogs(callback: (List<String>) -> Unit) {
        callback(list)
    }

    override fun clearLogs() = list.clear()

}