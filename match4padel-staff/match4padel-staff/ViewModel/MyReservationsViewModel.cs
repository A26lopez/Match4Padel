using match4padel_staff.Model;
using match4padel_staff.Service;
using match4padel_staff.View;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace match4padel_staff.ViewModel
{
    public class MyReservationsViewModel : BaseViewModel
    {
        public ObservableCollection<Reservation> Reservations { get; set; }

        private readonly ReservationService reservationService;

        public MyReservationsViewModel()
        {
            reservationService = new ReservationService();
            Reservations = new ObservableCollection<Reservation>();
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
    }
}
