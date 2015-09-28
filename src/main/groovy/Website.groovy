import io.vertx.groovy.ext.web.Router
import io.vertx.groovy.ext.web.handler.StaticHandler
import io.vertx.groovy.ext.web.handler.LoggerHandler
import io.vertx.groovy.ext.web.handler.sockjs.SockJSHandler


def sockJSHandler = SockJSHandler.create(vertx)

def bridgeOptions = [
    outboundPermitteds : [
        [address: 'com.example:stat:server-info']
    ],
    inboundPermitteds: [
        [address: 'com.example:cmd:poke-server']
    ]
]

sockJSHandler.bridge(bridgeOptions)

def router = Router.router(vertx)
def assetHandler = StaticHandler
    .create()
    .setDirectoryListing(true)

router.route("/eventbus/*").handler(sockJSHandler)
router.get("/*").handler(assetHandler)

def httpPort = vertx.getOrCreateContext().config()['http.port']

vertx
    .createHttpServer()
    .requestHandler(router.&accept)
    .listen(httpPort)

println "Listening on ${httpPort}"