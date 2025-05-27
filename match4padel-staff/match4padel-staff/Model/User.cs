using System.Text.Json.Serialization;

namespace match4padel_staff.Model
{
    public class User
    {
        public long Id { get; set; }

        public string Level { get; set; }

        [JsonPropertyName("account_info")]
        public AccountInfo AccountInfo { get; set; }
        [JsonPropertyName("contact_info")]
        public ContactInfo ContactInfo { get; set; }
        [JsonPropertyName("account_security")]
        public AccountSecurity AccountSecurity { get; set; }
        public string FormatedLevel
        {
            get
            {
                switch (Level)
                {
                    case "Principiante":
                        return "BEGINNER";
                    case "Intermedio":
                        return "INTERMEDIATE";
                    case "Avanzado":
                        return "ADVANCED";
                    case "Experto":
                        return "EXPERT";
                    default:
                        return Level;

                }
            }

        }
    }
}
