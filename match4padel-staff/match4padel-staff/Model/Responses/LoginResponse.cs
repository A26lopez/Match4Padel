using System.Text.Json.Serialization;

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
