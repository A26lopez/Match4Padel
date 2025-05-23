using PropertyChanged;
using System;
using System.Text.Json.Serialization;

namespace match4padel_staff.Model
{
    [AddINotifyPropertyChangedInterface]
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

        public string FormatedStatus
        {
            get
            {
                switch (Status)
                {
                    case "CONFIRMED":
                        return "Confirmado";
                        break;
                    case "COMPLETED":
                        return "Completado";
                        break;
                    case "CANCELLED":
                        return "Cancelado";
                        break;
                    default:
                        return Status;
                }
            }
        }
    }
}
