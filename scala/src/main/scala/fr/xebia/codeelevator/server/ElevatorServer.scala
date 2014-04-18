package fr.xebia.codeelevator.server

import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}
import java.net.{URI, InetSocketAddress}
import java.lang.Thread
import scala.collection.immutable.Map
import scala.collection.immutable.HashMap
import java.util.Scanner
import java.lang.Integer._
import java.io.{Writer, OutputStreamWriter}
import fr.xebia.codeelevator.{Direction, ElevatorEngine}

class ElevatorHttpHandler extends HttpHandler {
  val elevator: ElevatorEngine = new ElevatorEngine

  override def handle(httpExchange: HttpExchange): Unit = {
    val requestURI: URI = httpExchange.getRequestURI
    val parameters: Map[String, String] = extractParameters(requestURI)
    requestURI.getPath match {
      case "/reset" =>
        elevator.reset(parameters.get("cause").get)
        httpExchange.sendResponseHeaders(200, 0)
      case "/call" =>
        elevator.call(parseInt(parameters.get("atFloor").get), Direction.withName(parameters.get("to").get))
        httpExchange.sendResponseHeaders(200, 0)
      case "/go" =>
        elevator.go(parseInt(parameters.get("floorToGo").get))
        httpExchange.sendResponseHeaders(200, 0)
      case "/nextCommand" =>
        val nextCommand: String = elevator.nextCommand.toString
        httpExchange.sendResponseHeaders(200, nextCommand.length)
        val out: Writer = new OutputStreamWriter(httpExchange.getResponseBody)
        out.write(nextCommand)
        out.close()
      case "/userHasEntered" =>
        elevator.userHasEntered()
        httpExchange.sendResponseHeaders(200, 0)
      case "/userHasExited" =>
        elevator.userHasExited()
        httpExchange.sendResponseHeaders(200, 0)
    }
    httpExchange.close()
  }

  private def extractParameters(uri: URI): Map[String, String] = {
    val query: String = uri.getQuery
    val parameterAndValues: Map[String, String] = new HashMap
    if (query == null) {
      parameterAndValues
    } else {
      addParameter(new Scanner(query).useDelimiter("&"), new HashMap)
    }
  }

  private def addParameter(scanner: Scanner, parameterAndValues: HashMap[String, String]): Map[String, String] = {
    if (!scanner.hasNext) {
      parameterAndValues
    } else {
      val parameterAndValue: Array[String] = scanner.next().split("=")
      addParameter(scanner, parameterAndValues + (parameterAndValue(0) -> parameterAndValue(1).replace('+', ' ')))
    }
  }
}

object ElevatorServer {
  def main(args: Array[String]) {
    val httpServer: HttpServer = HttpServer.create(new InetSocketAddress(8081), 0)
    httpServer.createContext("/", new ElevatorHttpHandler())
    httpServer.start()

    Runtime.getRuntime.addShutdownHook(new Thread(new Runnable() {
      override def run(): Unit = httpServer.stop(1)
    }))
  }
}
