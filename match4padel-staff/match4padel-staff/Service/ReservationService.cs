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
    }
}
