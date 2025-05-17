using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace match4padel_staff.Model
{
    public class LoginResponse
    {
        [JsonPropertyName("user_id")]
        public long UserId { get; set; }
        public string Username { get; set; }
        public string Token { get; set; }

    }
}
