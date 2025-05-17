using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

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

    }
}
