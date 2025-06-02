using CommunityToolkit.Mvvm.Input;
using match4padel_staff.Model;
using match4padel_staff.Model.Responses;
using match4padel_staff.Service;
using match4padel_staff.View;
using PropertyChanged;
using System;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Input;

namespace match4padel_staff.ViewModel
{
    [AddINotifyPropertyChangedInterface]
    class SignUpViewModel : BaseViewModel
    {
        public event EventHandler RequestClose;
        public User User { get; set; }
        public string ConfirmPassword { get; set; }
        public string FirstNameError { get; set; }
        public string LastNameError { get; set; }
        public string UsernameError { get; set; }
        public string EmailError { get; set; }
        public string PasswordError { get; set; }
        public string ConfirmPasswordError { get; set; }
        public string PhoneNumberError { get; set; }
        public string NifError { get; set; }
        public string BirthDateError { get; set; }
        public string LevelError { get; set; }
        public string Error { get; set; }

        public AsyncRelayCommand SignUpCommand { get; set; }


        public SignUpViewModel()
        {
            User = new User();
            User.ContactInfo = new ContactInfo();
            User.AccountInfo = new AccountInfo();
            User.AccountSecurity = new AccountSecurity();
            User.ContactInfo.BirthDate = DateTime.Today;
            SignUpCommand = new AsyncRelayCommand(SignUp);
        }

        private async Task SignUp()
        {
            ClearErrors();


            var result = await UserService.CreateUser(User);

            if (User.AccountSecurity.Password != ConfirmPassword)
            {
                ConfirmPasswordError = "Las contraseñas no coinciden";
            }

            if (result is ValidationsResponse validation)
            {
                FirstNameError = validation.FirstNameError;
                LastNameError = validation.LastNameError;
                UsernameError = validation.UsernameError;
                EmailError = validation.EmailError;
                PasswordError = validation.PasswordError;
                PhoneNumberError = validation.PhoneNumberError;
                NifError = validation.NifError;
                BirthDateError = validation.BirthDateError;
                LevelError = validation.LevelError;
                
            }
            else if (result is User)
            {
                var userCreatedWindow = new UserCreatedWindow();
                userCreatedWindow.Owner = Application.Current.MainWindow;
                userCreatedWindow.ShowDialog();
                RequestClose?.Invoke(this, EventArgs.Empty);
            }
            else if (result is ErrorResponse e)
            {
                Error = e.Error;
            }

        }

        private void ClearErrors()
        {
            FirstNameError = "";
            LastNameError = "";
            UsernameError = "";
            EmailError = "";
            PasswordError = "";
            ConfirmPasswordError = "";
            PhoneNumberError = "";
            NifError = "";
            BirthDateError = "";
            LevelError = "";
            Error = "";
        }

       

    }
}
