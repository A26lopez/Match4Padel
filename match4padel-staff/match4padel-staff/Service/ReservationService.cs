using match4padel_staff.Model;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace match4padel_staff.Service
{
    class ReservationService
    {
        public async Task<object> getReservationsByUserId(long userId)
        {
            var response = await HttpClientService.Instance.GetAsync($"{HttpClientService.ApiUrl}/reservations/user/{userId}");
            var responseJson = await response.Content.ReadAsStringAsync();
            if (response.IsSuccessStatusCode)
            {
                return JsonSerializer.Deserialize<List<Reservation>>(responseJson, new JsonSerializerOptions
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

        public async Task<object> cancelReservationById(long id)
        {
            var response = await HttpClientService.Instance.PostAsync($"{HttpClientService.ApiUrl}/reservations/{id}/cancel", null);
            var responseJson = await response.Content.ReadAsStringAsync();
            if (response.IsSuccessStatusCode)
            {
                return JsonSerializer.Deserialize<Reservation>(responseJson, new JsonSerializerOptions
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

        public async Task<object> getFreeHoursByDate(DateTime date)
        {
            var response = await HttpClientService.Instance.GetAsync($"{HttpClientService.ApiUrl}/reservations/free/{date.ToString("yyyy-MM-dd")}");
            var responseJson = await response.Content.ReadAsStringAsync();
            if (response.IsSuccessStatusCode)
            {
                return JsonSerializer.Deserialize<List<TimeSpan>>(responseJson, new JsonSerializerOptions
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

        public async Task<object> CreateReservation(long userId, long courtId, DateTime date, TimeSpan startTime)
        {
            var data = new
            {
                user = new { id = userId },
                court = new { id = courtId },
                date = date.ToString("yyyy-MM-dd"),
                start_time = startTime.ToString(@"hh\:mm\:ss")
            };

            var json = JsonSerializer.Serialize(data);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            var response = await HttpClientService.Instance.PostAsync($"{HttpClientService.ApiUrl}/reservations", content);

            var responseJson = await response.Content.ReadAsStringAsync();

            if (response.IsSuccessStatusCode)
            {
                return JsonSerializer.Deserialize<Reservation>(responseJson, new JsonSerializerOptions
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
