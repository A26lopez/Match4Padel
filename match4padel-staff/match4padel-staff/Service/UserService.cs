using match4padel_staff.Model;
using match4padel_staff.Model.Responses;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace match4padel_staff.Service
{
    class UserService
    {
        public async Task<object> CreateUser(User user)
        {
            var data = new
            {
                account_info = new
                {
                    username = user.AccountInfo.Username
                },
                account_security = new
                {
                    password = user.AccountSecurity.Password
                },
                contact_info = new
                {
                    first_name = user.ContactInfo.FirstName,
                    last_name = user.ContactInfo.LastName,
                    nif = user.ContactInfo.Nif,
                    email = user.ContactInfo.Email,
                    phone_number = user.ContactInfo.PhoneNumber,
                    birth_date = user.ContactInfo.BirthDate.ToString("yyyy-MM-dd")
                },
                level = user.FormatedLevel
            };
            var json = JsonSerializer.Serialize(data);
            var content = new StringContent(json, Encoding.UTF8, "application/json");
            var response = await HttpClientService.Instance.PostAsync($"{HttpClientService.ApiUrl}/users", content);
            var responseJson = await response.Content.ReadAsStringAsync();
            
            if (response.IsSuccessStatusCode)
            {
                return JsonSerializer.Deserialize<User>(responseJson, new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                });
            }
            else
            {
                return JsonSerializer.Deserialize<ValidationsResponse>(responseJson, new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                });
            }
        }

        public async Task<object> getUserById(long userId)
        {
            var response = await HttpClientService.Instance.GetAsync($"{HttpClientService.ApiUrl}/users/{userId}");
            var responseJson = await response.Content.ReadAsStringAsync();
            if (response.IsSuccessStatusCode)
            {
                return JsonSerializer.Deserialize<User>(responseJson, new JsonSerializerOptions
                {

                    PropertyNameCaseInsensitive = true
                });
            }
            else
            {
                return JsonSerializer.Deserialize<ErrorResponse>(responseJson, new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                });
            }
        }
    }
}
