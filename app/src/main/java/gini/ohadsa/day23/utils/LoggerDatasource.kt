package gini.ohadsa.day23.utils

interface LoggerDatasource {
    fun addLog(msg: String)
    fun getAllLogs(callback: (List<String>)->Unit )
    fun clearLogs()
}
