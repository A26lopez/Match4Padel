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

        public static async Task<object> updateUser(long id, User user)
        {
            object accountSecurity;

            if (user.AccountSecurity.Password != null)
            {
                accountSecurity = new { password = user.AccountSecurity.Password };
            }
            else
            {
                accountSecurity = new { };
            }

            var data = new
            {
                account_info = new
                {
                    username = user.AccountInfo.Username
                },
                account_security = accountSecurity,
                contact_info = new
                {
                    first_name = user.ContactInfo.FirstName,
                    last_name = user.ContactInfo.LastName,
                    nif = user.ContactInfo.Nif,
                    email = user.ContactInfo.Email,
                    phone_number = user.ContactInfo.PhoneNumber,
                    birth_date = user.ContactInfo.BirthDate.ToString("yyyy-MM-dd"),
                    address = user.ContactInfo.Address,
                    city = user.ContactInfo.City,
                    postal_code = user.ContactInfo.PostalCode,
                    country = user.ContactInfo.Country
                },
                level = user.FormatedLevel
            };

            return await HttpClientService.SafePutAsync<User>($"/users/{id}", data);
        }
    }
}
