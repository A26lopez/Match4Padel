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

    }
}
