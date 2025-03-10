using match4padel_staff.ViewModel;
using PropertyChanged;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace match4padel_staff.ViewModel
{
    [AddINotifyPropertyChangedInterface]
    public class LoginViewModel : ViewModelBase
    {
        //Fields
        public string Username { get; set; }
        public SecureString Password { get; set; }
        public string ErrorMessage { get; set; }
        public bool IsViewVisible { get; set; } = true;


        //Commands
        public ICommand LoginCommand { get; }
        public ICommand RememberPassword { get; }


        //Constructor
        public LoginViewModel()
        {
            LoginCommand = new ViewModelCommand(ExecuteLoginCommand, CanExecuteLoginCommand);
            RememberPassword = new ViewModelCommand(p => ExecuteRememberPasswordCommand("", ""));
        }

        private void ExecuteRememberPasswordCommand(string username, string email)
        {
            throw new NotImplementedException();
        }

        private bool CanExecuteLoginCommand(object obj)
        {
            bool validData;
            if (string.IsNullOrEmpty(Username) ||
                Username.Length < 3 ||
                Password == null ||
                Password.Length < 3)
            {
                validData = false;
            }
            else
            {
                validData = true;
            }
            return validData;
        }

        private void ExecuteLoginCommand(object obj)
        {
            throw new NotImplementedException();
        }
    }


}
