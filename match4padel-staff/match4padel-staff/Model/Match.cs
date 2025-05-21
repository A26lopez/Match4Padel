using match4padel_staff.Service;
using PropertyChanged;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace match4padel_staff.Model
{
    [AddINotifyPropertyChangedInterface]
    public class Match
    {
        public long Id { get; set; }

        public Reservation Reservation { get; set; }

        public User Owner { get; set; }

        [JsonPropertyName("player_1")]
        public User Player1 { get; set; }

        [JsonPropertyName("player_2")]
        public User Player2 { get; set; }

        [JsonPropertyName("player_3")]
        public User Player3 { get; set; }

        public string Status { get; set; }

        public string Level { get; set; }

        public bool IsUserJoined
        {
            get
            {
                var userId = SessionService.Instance.UserId;
                return Owner?.Id == userId ||
                       Player1?.Id == userId ||
                       Player2?.Id == userId ||
                       Player3?.Id == userId;
            }
        }

        public bool IsUserOwner
        {
            get
            {
                var userId = SessionService.Instance.UserId;
                return Owner.Id == userId;

            }
        }

        public bool IsUserPlayer
        {
            get
            {
                var userId = SessionService.Instance.UserId;
                return Player1?.Id == userId ||
                       Player2?.Id == userId ||
                       Player3?.Id == userId;
            }
        }

        public string FormatedStatus
        {
            get
            {
                switch (Status)
                {
                    case "OPEN":
                        return "Abierto";
                        break;
                    case "CLOSED":
                        return "Cerrado";
                        break;
                    default:
                        return Status;
                }
            }
        }

        public string FormatedLevel
        {
            get
            {
                switch (Level)
                {
                    case "BEGINNER":
                        return "Principiante";
                        break;
                    case "INTERMEDIATE":
                        return "Intermedio";
                        break;
                    case "ADVANCED":
                        return "Avanzado";
                        break;
                    case "EXPERT":
                        return "Experto";
                        break;
                    default:
                        return Level;

                }
            }
        }
    }


}

