import io.vertx.core.AsyncResult
import io.vertx.core.buffer.Buffer
import io.vertx.core.json.JsonObject
import io.vertx.core.VertxOptions

def eb = vertx.eventBus()
def webOptions = [config: ["http.port": 9999]]

vertx.deployVerticle("ServerInfo.groovy")
vertx.deployVerticle("Website.groovy", webOptions)

