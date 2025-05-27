using match4padel_staff.Model;
using match4padel_staff.Model.Responses;
using System;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows;

namespace match4padel_staff.Service
{
    public static class HttpClientService
    {

        public static string ApiUrl { get; } = "http://localhost:8080/match4padel/api";
        private static HttpClient _instance;

        public static HttpClient Instance
        {
            get
            {
                if (_instance == null)
                {
                    _instance = new HttpClient();
                }
                return _instance;
            }
        }

        public static async Task<object> SafeGetAsync<T>(string endpoint)
        {
            try
            {
                var response = await Instance.GetAsync($"{ApiUrl}{endpoint}");
                var responseJson = await response.Content.ReadAsStringAsync();

                if (response.IsSuccessStatusCode)
                {
                    return JsonSerializer.Deserialize<T>(responseJson, new JsonSerializerOptions
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
            catch (HttpRequestException ex)
            {
                MessageBox.Show($"No se pudo conectar con el servidor:\n{ex.Message}", "Error de conexión", MessageBoxButton.OK, MessageBoxImage.Error);
            }
            catch (TaskCanceledException ex)
            {
                MessageBox.Show($"Tiempo de espera agotado:\n{ex.Message}", "Timeout", MessageBoxButton.OK, MessageBoxImage.Warning);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error inesperado:\n{ex.Message}", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }

            return null;
        }

        public static async Task<object> SafePostAsync<T>(string endpoint, object data)
        {
            try
            {
                var json = JsonSerializer.Serialize(data);
                var content = new StringContent(json, Encoding.UTF8, "application/json");
                var response = await Instance.PostAsync($"{ApiUrl}{endpoint}", content);
                var responseJson = await response.Content.ReadAsStringAsync();

                if (response.IsSuccessStatusCode)
                {
                    return JsonSerializer.Deserialize<T>(responseJson, new JsonSerializerOptions
                    {
                        PropertyNameCaseInsensitive = true
                    });
                }
                else if (responseJson.Contains("error"))
                {
                    return JsonSerializer.Deserialize<ErrorResponse>(responseJson, new JsonSerializerOptions
                    {
                        PropertyNameCaseInsensitive = true
                    });
                }
                else
                {
                    return JsonSerializer.Deserialize<ValidationsResponse>(responseJson, new JsonSerializerOptions
                    {
                        PropertyNameCaseInsensitive = true
                    });
                }
            }
            catch (HttpRequestException ex)
            {
                MessageBox.Show($"No se pudo conectar con el servidor:\n{ex.Message}", "Error de conexión", MessageBoxButton.OK, MessageBoxImage.Error);
            }
            catch (TaskCanceledException ex)
            {
                MessageBox.Show($"Tiempo de espera agotado:\n{ex.Message}", "Timeout", MessageBoxButton.OK, MessageBoxImage.Warning);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error inesperado:\n{ex.Message}", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }

            return null;
        }

    }

}
