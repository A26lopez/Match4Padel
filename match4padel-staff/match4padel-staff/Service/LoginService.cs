using match4padel_staff.Model;
using System;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows;

namespace match4padel_staff.Service
{
    public static class LoginService
    {
        public static async Task<object> LoginAsync(string username, string password)
        {
            var data = new { username, password };
            return await HttpClientService.SafePostAsync<LoginResponse>("/auth/login", data);
        }
    }
}
