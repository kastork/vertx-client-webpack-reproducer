def eb = vertx.eventBus()
def vContext = vertx.getOrCreateContext()
println("ServerStatus: Vertx context ${vContext.config()}")

String sysTime()
{
  Date dd = new Date()
  dd.format("hh:mm:ss")
}

vertx.setPeriodic(
    1000, {id ->
  eb.publish(
      'com.example:stat:server-info',
      [
          systemTime: sysTime()
      ]
  )
}
)

println "* deployed: ServerInfo"
