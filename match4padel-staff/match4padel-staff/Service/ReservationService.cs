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
    class ReservationService
    {
        public async Task<object> getReservationsByUserId(long userId)
        {
            var response = await HttpClientService.Instance.GetAsync($"http://localhost:8080/match4padel/api/reservations/user/{userId}");
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
    }
}
