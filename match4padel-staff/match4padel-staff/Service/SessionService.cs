using PropertyChanged;

namespace match4padel_staff.Service
{
    [AddINotifyPropertyChangedInterface]
    public class SessionService
    {
        private static SessionService _instance;
        public static SessionService Instance
        {
            get
            {
                if (_instance == null)
                {
                    _instance = new SessionService();
                }
                return _instance;
            }
        }

        public string Token { get; private set; }
        public long UserId { get; private set; }
        public string Username { get; set; }

        public void SetSession(string token, long userId, string username)
        {
            Token = token;
            UserId = userId;
            Username = username;
        }

        public void ClearSession()
        {
            Token = null;
            UserId = 0;
        }
    }

}
