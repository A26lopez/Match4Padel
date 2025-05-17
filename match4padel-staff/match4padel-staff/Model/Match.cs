using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace match4padel_staff.Model
{
    public class Match
    {
        public long Id { get; set; }

        public Reservation Reservation { get; set; }

        public User Owner { get; set; }

        [JsonPropertyName("player_1_name")]
        public User Player1 { get; set; }

        [JsonPropertyName("player_2_name")]
        public User Player2 { get; set; }

        [JsonPropertyName("player_3_name")]
        public User Player3 { get; set; }

        public string Status { get; set; }

        public string Level { get; set; }
    }
}

