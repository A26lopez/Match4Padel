using PropertyChanged;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace match4padel_staff.Model.Responses
{
    [AddINotifyPropertyChangedInterface]
    public class ValidationsResponse
    {

        [JsonPropertyName("contactInfo.firstName")]
        public string FirstNameError { get; set; }

        [JsonPropertyName("contactInfo.lastName")]
        public string LastNameError { get; set; }

        [JsonPropertyName("accountInfo.username")]
        public string UsernameError { get; set; }

        [JsonPropertyName("contactInfo.email")]
        public string EmailError { get; set; }

        [JsonPropertyName("accountSecurity.password")]
        public string PasswordError { get; set; }

        [JsonPropertyName("contactInfo.phoneNumber")]
        public string PhoneNumberError { get; set; }
        [JsonPropertyName("contactInfo.nif")]
        public string NifError { get; set; }
        [JsonPropertyName("contactInfo.birthDate")]
        public string BirthDateError { get; set; }
        [JsonPropertyName("level")]
        public string LevelError { get; set; }

        public string Error { get; set; }
    }
}

