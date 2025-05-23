using match4padel_staff.Model;
using System;
using System.Collections.Generic;
using System.Text.Json;
using System.Threading.Tasks;

namespace match4padel_staff.Service
{
    public class CourtService
    {
        public async Task<object> getAllCourts()
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

        public async Task<object> getCourtsByDateAndTime(DateTime date, TimeSpan time)
        {
            var response = await HttpClientService.Instance.GetAsync($"{HttpClientService.ApiUrl}/courts/free?date={date.ToString("yyyy-MM-dd")}&starttime={time}");
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


