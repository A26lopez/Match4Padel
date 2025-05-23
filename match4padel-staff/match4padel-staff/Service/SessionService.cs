namespace match4padel_staff.Service
{
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

        public void SetSession(string token, long userId)
        {
            Token = token;
            UserId = userId;
        }

        public void ClearSession()
        {
            Token = null;
            UserId = 0;
        }
    }

}
