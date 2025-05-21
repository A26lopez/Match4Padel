using match4padel_staff.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
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
            var response = await HttpClientService.Instance.GetAsync($"{HttpClientService.ApiUrl}/matches/user/{userId}");
            var responseJson = await response.Content.ReadAsStringAsync();
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

        public async Task<object> getOpenMatches()
        {
            var response = await HttpClientService.Instance.GetAsync($"{HttpClientService.ApiUrl}/matches/status/OPEN");
            var responseJson = await response.Content.ReadAsStringAsync();
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

        public async Task<object> cancelMatchById(long id)
        {
            var response = await HttpClientService.Instance.PostAsync($"{HttpClientService.ApiUrl}/matches/{id}/cancel", null);
            var responseJson = await response.Content.ReadAsStringAsync();
            if (response.IsSuccessStatusCode)
            {
                return JsonSerializer.Deserialize<Match>(responseJson, new JsonSerializerOptions
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
    
        public async Task<object> joinMatch(long matchId, long userId)
        {
            var response = await HttpClientService.Instance.PostAsync($"{HttpClientService.ApiUrl}/matches/{matchId}/add/{userId}", null);
            var responseJson = await response.Content.ReadAsStringAsync();
            if (response.IsSuccessStatusCode)
            {
                return JsonSerializer.Deserialize<Match>(responseJson, new JsonSerializerOptions
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

        public async Task<object> leaveMatch(long matchId, long userId)
        {
            var response = await HttpClientService.Instance.PostAsync($"{HttpClientService.ApiUrl}/matches/{matchId}/remove/{userId}", null);
            var responseJson = await response.Content.ReadAsStringAsync();
            if (response.IsSuccessStatusCode)
            {
                return JsonSerializer.Deserialize<Match>(responseJson, new JsonSerializerOptions
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

        public async Task<object> CreateMatch(long userId, long courtId, string level, DateTime date, TimeSpan startTime)
        {
            var data = new
            {
                reservation = new
                {
                    court = new { id = courtId },
                    date = date.ToString("yyyy-MM-dd"),
                    start_time = startTime.ToString(@"hh\:mm\:ss")
                },
                level = level,
                owner = new { id = userId }
            };

            var json = JsonSerializer.Serialize(data);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            var response = await HttpClientService.Instance.PostAsync($"{HttpClientService.ApiUrl}/matches", content);

            var responseJson = await response.Content.ReadAsStringAsync();
            
            if (response.IsSuccessStatusCode)
            {
                return JsonSerializer.Deserialize<Match>(responseJson, new JsonSerializerOptions
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
