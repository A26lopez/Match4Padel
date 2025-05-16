using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace match4padel_staff.Model
{
    public class LoginResponse
    {
        public long UserId { get; set; }
        public string Username { get; set; }
        public string Token { get; set; }

    }
}
