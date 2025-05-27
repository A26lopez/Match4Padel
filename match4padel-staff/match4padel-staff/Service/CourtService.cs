using match4padel_staff.Model;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows;

namespace match4padel_staff.Service
{
    public static class CourtService
    {
        public static async Task<object> GetAllCourts()
        {
            return await HttpClientService.SafeGetAsync<List<Court>>("/courts");
        }

        public static async Task<object> GetCourtsByDateAndTime(DateTime date, TimeSpan time)
        {
            string endpoint = $"/courts/free?date={date:yyyy-MM-dd}&starttime={time}";
            return await HttpClientService.SafeGetAsync<List<Court>>(endpoint);
        }
    }
}


