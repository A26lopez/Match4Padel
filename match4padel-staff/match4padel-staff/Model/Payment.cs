using System;
using System.Text.Json.Serialization;

namespace match4padel_staff.Model
{
    public class Payment
    {
        public long Id { get; set; }

        public Reservation Reservation { get; set; }

        public User User { get; set; }

        public decimal Amount { get; set; }

        public string Method { get; set; }

        public string Status { get; set; }

        [JsonPropertyName("created_at")]
        public DateTime CreatedAt { get; set; }
    }
}
