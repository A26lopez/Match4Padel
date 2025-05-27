using match4padel_staff.Model;
using System.Collections.Generic;
using System.Threading.Tasks;


namespace match4padel_staff.Service
{
    public static class PaymentService
    {
        public static async Task<object> GetPaymentByReservationId(long reservationId)
        {
            return await HttpClientService.SafeGetAsync<List<Payment>>($"/payments/reservation/{reservationId}");
        }

        public static async Task<object> CompletePayment(long paymentId, string method)
        {
            string endpoint = $"/payments/{paymentId}/complete?method={method}";
            return await HttpClientService.SafePostAsync<LoginResponse>(endpoint, null);
        }
    }
}

