using CommunityToolkit.Mvvm.Input;
using match4padel_staff.Model;
using match4padel_staff.Service;
using match4padel_staff.View;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Threading.Tasks;
using System.Windows;

namespace match4padel_staff.ViewModel
{
    public class MyReservationsViewModel : BaseViewModel
    {
        public ObservableCollection<Reservation> Reservations { get; set; }
        public IAsyncRelayCommand CancelReservationCommand { get; }


        public MyReservationsViewModel()
        {
            Reservations = new ObservableCollection<Reservation>();
            CancelReservationCommand = new AsyncRelayCommand<Reservation>(CancelReservation);
            LoadReservations();
        }

        private async Task LoadReservations()
        {
            var result = await ReservationService.GetReservationsByUserId(SessionService.Instance.UserId);
            if (result is List<Reservation> reservationList)
            {
                foreach (var r in reservationList)
                {
                    if (!r.IsMatch)
                    {
                        Reservations.Add(r);
                    }

                }
            }
        }

        private static async Task CancelReservation(Reservation reservation)
        {
            if (reservation == null) return;

            var boxResult = MessageBox.Show(
                "¿Estás seguro de que deseas cancelar esta reserva?",
                "Confirmar",
                MessageBoxButton.YesNo,
                MessageBoxImage.Warning
            );

            if (boxResult != MessageBoxResult.Yes)
                return;

            var result = await ReservationService.CancelReservationById(reservation.Id);

            if (result is Reservation)
            {
                reservation.Status = "CANCELLED";
            }
            else if (result is ErrorResponse e)
            {
                MessageBox.Show(e.Error, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

    }
}
