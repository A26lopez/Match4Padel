using PropertyChanged;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using System.Windows;
using System.Xml.Linq;

namespace match4padel_staff.ViewModel
{
    [AddINotifyPropertyChangedInterface]
    public class LoginViewModel
    {
        public string Username { get; set; }
        public string Password { get; set; }
        public string ErrorMessage { get; set; }

        public ICommand LoginCommand { get; }

        public LoginViewModel()
        {

        }
    }
}
