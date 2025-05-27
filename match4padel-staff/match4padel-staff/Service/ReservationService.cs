using match4padel_staff.Model;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace match4padel_staff.Service
{
    public static class ReservationService
    {
        public static async Task<object> GetReservationsByUserId(long userId)
        {
            return await HttpClientService.SafeGetAsync<List<Reservation>>($"/reservations/user/{userId}");
        }

        public static async Task<object> CancelReservationById(long id)
        {
            return await HttpClientService.SafePostAsync<Reservation>($"/reservations/{id}/cancel", null);
        }

        public static async Task<object> GetFreeHoursByDate(DateTime date)
        {
            string dateStr = date.ToString("yyyy-MM-dd");
            return await HttpClientService.SafeGetAsync<List<TimeSpan>>($"/reservations/free/{dateStr}");
        }

        public static async Task<object> CreateReservation(long userId, long courtId, DateTime date, TimeSpan startTime)
        {
            var data = new
            {
                user = new { id = userId },
                court = new { id = courtId },
                date = date.ToString("yyyy-MM-dd"),
                start_time = startTime.ToString(@"hh\:mm\:ss")
            };

            return await HttpClientService.SafePostAsync<Reservation>("/reservations", data);
        }
    }
}

