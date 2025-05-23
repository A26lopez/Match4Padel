using PropertyChanged;
using System.Text.Json.Serialization;

namespace match4padel_staff.Model
{
    [AddINotifyPropertyChangedInterface]
    public class Court
    {
        public long Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        [JsonPropertyName("picture_url")]
        public string PictureUrl { get; set; }
        [JsonPropertyName("court_zone")]
        public string CourtZone { get; set; }
        [JsonPropertyName("court_type")]
        public string CourtType { get; set; }
        [JsonPropertyName("price_per_match")]
        public decimal PricePerMatch { get; set; }

        public string PricePerPlayer
        {
            get
            {
                return (PricePerMatch / 4).ToString("F1");
            }
        }

        [JsonIgnore]
        public string PictureFullPath
        {
            get
            {
                return $"/Resources/Images/Courts/{PictureUrl}.png";
            }
        }

        [JsonIgnore]
        public string Zone => TranslateCourtZone(CourtZone);

        [JsonIgnore]
        public string Type => TranslateCourtType(CourtType);

        [JsonIgnore]
        public string Price => PricePerMatch.ToString("F1");

        private string TranslateCourtType(string type)
        {
            switch (type)
            {
                case "ARTIFICIAL_GRASS":
                    return "Césped artificial";
                case "HARD_COURT":
                    return "Resina sintética";
                default:
                    return "Desconocido";
            }
        }

        private string TranslateCourtZone(string zone)
        {
            switch (zone)
            {
                case "INDOOR":
                    return "Interior";
                case "OUTDOOR":
                    return "Exterior";
                default:
                    return "Desconocido";
            }
        }
    }

}
