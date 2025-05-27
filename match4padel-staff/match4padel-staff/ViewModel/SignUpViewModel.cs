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
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Username { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public string ConfirmPassword { get; set; }
        public string PhoneNumber { get; set; }
        public string Nif { get; set; }
        public DateTime BirthDate { get; set; }
        public string Level { get; set; }

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
            BirthDate = DateTime.Today;
            SignUpCommand = new AsyncRelayCommand(SignUp);
        }

        private async Task SignUp()
        {
            ClearErrors();

            User user = CreateUser();

            var result = await UserService.CreateUser(user);

            if (result is ValidationsResponse validation)
            {
                if (Password != ConfirmPassword)
                {
                    ConfirmPasswordError = "Las contraseñas no coinciden";
                }
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



        private User CreateUser()
        {
            User user = new User();
            user.ContactInfo = new ContactInfo();
            user.AccountInfo = new AccountInfo();
            user.AccountSecurity = new AccountSecurity();
            user.ContactInfo.FirstName = FirstName;
            user.ContactInfo.LastName = LastName;
            user.AccountInfo.Username = Username;
            user.ContactInfo.Email = Email;
            user.AccountSecurity.Password = Password;
            user.ContactInfo.PhoneNumber = PhoneNumber;
            user.ContactInfo.Nif = Nif;
            user.ContactInfo.BirthDate = BirthDate;
            user.Level = Level;
            return user;
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
