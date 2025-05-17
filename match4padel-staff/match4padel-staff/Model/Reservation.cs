using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace match4padel_staff.Model
{
    public class Reservation
    {
        public long Id { get; set; }

        public User User { get; set; }

        public Court Court { get; set; }

        public DateTime Date { get; set; }
        [JsonPropertyName("match")]
        public bool IsMatch { get; set; } = false;
        [JsonPropertyName("start_time")]
        public TimeSpan StartTime { get; set; }

        public int Duration { get; set; } = 90;
        [JsonPropertyName("end_time")]
        public TimeSpan EndTime { get; set; }

        public string Status { get; set; }
        [JsonPropertyName("created_at")]
        public DateTime CreatedAt { get; set; }

        [JsonPropertyName("paid")]
        public bool IsPaid { get; set; } = false;
    }
}
