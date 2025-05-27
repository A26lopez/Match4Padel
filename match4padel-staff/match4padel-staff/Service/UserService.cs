using match4padel_staff.Model;
using System.Threading.Tasks;

namespace match4padel_staff.Service
{
    public static class UserService
    {
        public static async Task<object> CreateUser(User user)
        {
            var data = new
            {
                account_info = new
                {
                    username = user.AccountInfo.Username
                },
                account_security = new
                {
                    password = user.AccountSecurity.Password
                },
                contact_info = new
                {
                    first_name = user.ContactInfo.FirstName,
                    last_name = user.ContactInfo.LastName,
                    nif = user.ContactInfo.Nif,
                    email = user.ContactInfo.Email,
                    phone_number = user.ContactInfo.PhoneNumber,
                    birth_date = user.ContactInfo.BirthDate.ToString("yyyy-MM-dd")
                },
                level = user.FormatedLevel
            };

            return await HttpClientService.SafePostAsync<User>("/users", data);
        }

        public static async Task<object> GetUserById(long userId)
        {
            return await HttpClientService.SafeGetAsync<User>($"/users/{userId}");
        }
    }
}
