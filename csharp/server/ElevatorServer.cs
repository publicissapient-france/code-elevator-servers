using System;
using System.Net;
using System.Text;
using System.Threading;

namespace csharp
{
    class ElevatorServer
    {
        static void Main(string[] args)
        {
            ElevatorServer elevatorServer = new ElevatorServer("http://*:8080/");
            elevatorServer.Run();

            Console.WriteLine("A simple webserver. Press a key to quit.");
            Console.ReadKey();
            elevatorServer.Stop();
        }

        private readonly HttpListener _listener = new HttpListener();
        private readonly ElevatorEngine _elevatorEngine = new ElevatorEngine();

        public ElevatorServer(string root)
        {
            if (string.IsNullOrEmpty(root))
            {
                throw new ArgumentException("root");
            }

            _listener.Prefixes.Add(root);
            _listener.Start();
        }

        public void Run()
        {
            ThreadPool.QueueUserWorkItem((o) =>
            {
                Console.WriteLine("Webserver running...");
                try
                {
                    while (_listener.IsListening)
                    {
                        ThreadPool.QueueUserWorkItem((c) =>
                        {
                            var ctx = c as HttpListenerContext;
                            try

                            {
                                switch (ctx.Request.Url.Segments[1].Replace("/", ""))
                                {
                                    case "reset":
                                    {
                                        _elevatorEngine.Reset(ctx.Request.QueryString.Get("cause"));
                                        HttpResponseOk(ctx);
                                        break;
                                    }
                                    case "call":
                                    {
                                        Direction direction =
                                            (Direction) Enum.Parse(typeof(Direction), ctx.Request.QueryString.Get("to"));
                                        _elevatorEngine.Call(
                                            Int32.Parse(ctx.Request.QueryString.Get("atFloor")),
                                            direction
                                        );
                                        HttpResponseOk(ctx);
                                        break;
                                    }
                                    case "go":
                                    {
                                        _elevatorEngine.Go(
                                            Int32.Parse(ctx.Request.QueryString.Get("floorToGo"))
                                        );
                                        HttpResponseOk(ctx);
                                        break;
                                    }
                                    case "nextCommand":
                                    {
                                        HttpResponseOk(ctx, _elevatorEngine.NextCommand().ToString().ToUpper());
                                        break;
                                    }
                                    case "userHasEntered":
                                    {
                                        _elevatorEngine.UserHasEntered();
                                        HttpResponseOk(ctx);
                                        break;
                                    }
                                    case "userHasExited":
                                    {
                                        _elevatorEngine.UserHasExited();
                                        HttpResponseOk(ctx);
                                        break;
                                    }
                                    default:
                                    {
                                        HttpResponseKo(ctx);
                                        break;
                                    }
                                }
                            }
                            finally
                            {
                                ctx.Response.OutputStream.Close();
                            }
                        }, _listener.GetContext());
                    }
                }
                catch
                {
                }
            });
        }

        public void HttpResponseOk(HttpListenerContext ctx, string content = "")
        {
            HttpListenerResponse response = ctx.Response;
            response.StatusCode = (int) HttpStatusCode.OK;
            response.StatusDescription = "OK";

            if (!String.IsNullOrEmpty(content))
            {
                byte[] buf = Encoding.UTF8.GetBytes(content);
                response.ContentLength64 = buf.Length;
                response.OutputStream.Write(buf, 0, buf.Length);
            }
        }

        public void HttpResponseKo(HttpListenerContext ctx)
        {
            HttpListenerResponse response = ctx.Response;
            response.StatusCode = (int) HttpStatusCode.BadRequest;
            response.StatusDescription = "KO";
        }

        public void Stop()
        {
            _listener.Stop();
            _listener.Close();
        }
    }
}