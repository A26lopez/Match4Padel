using System;
using System.Text.Json.Serialization;

namespace match4padel_staff.Model
{
    public class AccountInfo
    {
        public long Id { get; set; }

        public string Username { get; set; }

        public string ProfilePictureUrl { get; set; }

        public DateTime CreatedAt { get; set; }

        public DateTime? LastLogin { get; set; }

        [JsonIgnore]
        public string PictureFullPath
        {
            get
            {
                return $"../Resources/Images/Courts/{ProfilePictureUrl}.png";
            }
        }

    }
}
