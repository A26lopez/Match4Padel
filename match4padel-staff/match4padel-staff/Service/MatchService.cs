using match4padel_staff.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows;

namespace match4padel_staff.Service
{
    public class MatchService
    {
        public async Task<object> getMatchesByUserId(long userId)
        {
            var response = await HttpClientService.Instance.GetAsync($"http://localhost:8080/match4padel/api/matches/user?userId={userId}");
            var responseJson = await response.Content.ReadAsStringAsync();
            MessageBox.Show("hola" + responseJson);
            if (response.IsSuccessStatusCode)
            {
                return JsonSerializer.Deserialize<List<Match>>(responseJson, new JsonSerializerOptions
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
