using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

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
