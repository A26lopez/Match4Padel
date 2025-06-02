using System;
using System.Text.Json.Serialization;

namespace match4padel_staff.Model
{
    public class ContactInfo
    {
        public long Id { get; set; }

        [JsonPropertyName("first_name")]
        public string FirstName { get; set; }

        [JsonPropertyName("last_name")]
        public string LastName { get; set; }

        public string Nif { get; set; }

        public string Email { get; set; }


        [JsonPropertyName("phone_number")]
        public string PhoneNumber { get; set; }

        [JsonPropertyName("birth_date")]
        public DateTime BirthDate { get; set; }

        public string Address { get; set; }

        public string City { get; set; }

        [JsonPropertyName("postal_code")]
        public string PostalCode { get; set; }

        public string Country { get; set; }
    }
}
