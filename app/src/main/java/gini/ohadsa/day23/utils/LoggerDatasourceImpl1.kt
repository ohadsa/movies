package gini.ohadsa.day23.utils

import gini.ohadsa.day23.utils.LoggerDatasource
import javax.inject.Inject


//InMemoryLogger (Good for Unit Tests)//example (not for production)
class LoggerDatasourceImpl1 constructor(private val list: MutableList<String> ) : LoggerDatasource {

    @Inject constructor(): this(mutableListOf())

    override fun addLog(msg: String) {
    list.add("$msg Mock Logger")
    }

    override fun getAllLogs(callback: (List<String>) -> Unit) {
        callback(list)
    }

    override fun clearLogs() {
       list.clear()
    }
}