using match4padel_staff.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using System.Windows;

namespace match4padel_staff.Service
{
    public class CourtService
    {
        public async Task<object> getAllCourtsAsync()
        {
            var response = await HttpClientService.Instance.GetAsync($"{HttpClientService.ApiUrl}/courts");
            var responseJson = await response.Content.ReadAsStringAsync();
            if (response.IsSuccessStatusCode)
            {
                
                return JsonSerializer.Deserialize<List<Court>>(responseJson, new JsonSerializerOptions
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


