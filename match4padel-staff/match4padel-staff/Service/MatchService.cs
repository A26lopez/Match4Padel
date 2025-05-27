using match4padel_staff.Model;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace match4padel_staff.Service
{
    public static class MatchService
    {
        public static async Task<object> GetMatchesByUserId(long userId)
        {
            return await HttpClientService.SafeGetAsync<List<Match>>($"/matches/user/{userId}");
        }

        public static async Task<object> GetOpenMatches()
        {
            return await HttpClientService.SafeGetAsync<List<Match>>("/matches/status/OPEN");
        }

        public static async Task<object> CancelMatchById(long id)
        {
            return await HttpClientService.SafePostAsync<Match>($"/matches/{id}/cancel", null);
        }

        public static async Task<object> JoinMatch(long matchId, long userId)
        {
            return await HttpClientService.SafePostAsync<Match>($"/matches/{matchId}/add/{userId}", null);
        }

        public static async Task<object> LeaveMatch(long matchId, long userId)
        {
            return await HttpClientService.SafePostAsync<Match>($"/matches/{matchId}/remove/{userId}", null);
        }

        public static async Task<object> CreateMatch(long userId, long courtId, string level, DateTime date, TimeSpan startTime)
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

            return await HttpClientService.SafePostAsync<Match>("/matches", data);
        }
    }
}
