using System.Net.Http;

namespace match4padel_staff.Service
{
    public class HttpClientService
    {
        private static HttpClient _instance;

        public static string ApiUrl { get; } = "http://localhost:8080/match4padel/api";
        public static HttpClient Instance
        {
            get
            {
                if (_instance == null)
                {
                    _instance = new HttpClient();
                }
                return _instance;
            }
        }
    }

}
