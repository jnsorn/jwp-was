package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port = initPort(args);
        ExecutorService executorService = Executors.newFixedThreadPool(ThreadPoolSizeCalculator.getThreadPoolSize());
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executorService.submit(new RequestHandler(connection));
            }
        }
        executorService.shutdown();
    }

    private static int initPort(String[] args) {
        int port;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }
        return port;
    }
}
