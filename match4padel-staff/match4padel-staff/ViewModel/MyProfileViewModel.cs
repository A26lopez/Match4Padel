using CommunityToolkit.Mvvm.Input;
using match4padel_staff.Model;
using match4padel_staff.Model.Responses;
using match4padel_staff.Service;
using match4padel_staff.View;
using PropertyChanged;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace match4padel_staff.ViewModel
{
    [AddINotifyPropertyChangedInterface]
    class MyProfileViewModel
    {
        public bool formsEnabled { get; set; }
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
        public string AddressError { get; set; }
        public string CityError { get; set; }
        public string CountryError { get; set; }
        public string PostalCodeError { get; set; }
        public string Error { get; set; }

        public AsyncRelayCommand SaveChangesCommand { get; set; }


        public MyProfileViewModel()
        {
            formsEnabled = true;
            LoadUser();
            SaveChangesCommand = new AsyncRelayCommand(SaveChanges);
        }

        private async void LoadUser()
        {
            var result = await UserService.GetUserById(SessionService.Instance.UserId);
            
            if (result is User user)
            {
                User = user;
                User.AccountSecurity = new AccountSecurity();
                
            }
        }

        private async Task SaveChanges()
        {
            ClearErrors();

            var result = await UserService.updateUser(SessionService.Instance.UserId, User);

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
                AddressError = validation.AddressError;
                CityError = validation.CityError;
                PostalCodeError = validation.PostalCodeError;
                CountryError = validation.CountryError;
                

            }
            else if (result is User)
            {
                var savedChangesWindow = new SavedChangesWindow();
                savedChangesWindow.Owner = Application.Current.MainWindow;
                savedChangesWindow.ShowDialog();
                formsEnabled = false;
                SessionService.Instance.Username = User.AccountInfo.Username;
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
            AddressError = "";
            CityError = "";
            CountryError = "";
            PostalCodeError = "";
            Error = "";
        }

    }
}
