using CommunityToolkit.Mvvm.Input;
using match4padel_staff.Model;
using match4padel_staff.Service;
using match4padel_staff.View;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Input;

namespace match4padel_staff.ViewModel
{
    public class MyReservationsViewModel : BaseViewModel
    {
        public ObservableCollection<Reservation> Reservations { get; set; }
        public IAsyncRelayCommand CancelReservationCommand { get; }

        private readonly ReservationService reservationService;

        public MyReservationsViewModel()
        {
            reservationService = new ReservationService();
            Reservations = new ObservableCollection<Reservation>();
            CancelReservationCommand = new AsyncRelayCommand<Reservation>(CancelReservation);
            LoadReservations();
        }

        private async Task LoadReservations()
        {
            var result = await reservationService.getReservationsByUserId(SessionService.Instance.UserId);
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

        private async Task CancelReservation(Reservation reservation)
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

            var result = await reservationService.cancelReservationById(reservation.Id);

            if (result is Reservation r)
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
